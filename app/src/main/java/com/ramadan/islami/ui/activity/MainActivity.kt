package com.ramadan.islami.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.ramadan.islami.R
import com.ramadan.islami.utils.LocaleHelper
import com.ramadan.islami.utils.isNetworkConnected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var listener: NavController.OnDestinationChangedListener
    private lateinit var navController: NavController
    private lateinit var fixedBanner: AdView
    private val appURL = ""
    private val localeHelper = LocaleHelper()
    lateinit var constraintLayout: ConstraintLayout
    private val APP_UPDATE_REQUEST_CODE = 1991
    private val mAppUpdateManager: AppUpdateManager? = null
    private val appUpdateManager: AppUpdateManager by lazy { AppUpdateManagerFactory.create(this) }
    private val appUpdatedListener: InstallStateUpdatedListener by lazy {
        object : InstallStateUpdatedListener {
            override fun onStateUpdate(installState: InstallState) {
                when {
                    installState.installStatus() == InstallStatus.DOWNLOADED -> popupSnackbarForCompleteUpdate()
                    installState.installStatus() == InstallStatus.INSTALLED ->
                        appUpdateManager.unregisterListener(this)
                    else -> Log.d("UpdatedListener", installState.installStatus().toString())
                }
            }
        }
    }

    companion object {
        var language: String = "ar"
        var isConnected: Boolean = true
        var firebaseAnalytics: FirebaseAnalytics? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        language = if (localeHelper.getDefaultLanguage(this) == "en") "en" else "ar"
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        constraintLayout = findViewById(R.id.mainConstraint)
        navView.setupWithNavController(navController)
        navView.itemIconTintList = null
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        firebaseAnalytics = Firebase.analytics
        fixedBanner = findViewById(R.id.fixedBanner)
        listener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                when (destination.id) {
                    R.id.nav_family_tree_details -> {

                    }
                    //                        Intent(Intent.ACTION_SEND).also {
//                            it.type = "text/plain"
//                            it.putExtra(
//                                Intent.EXTRA_TEXT,
//                                "I suggest this app for you : ${appURL}"
//                            )
//                            startActivity(it)
//                        }

                }
            }
        isConnected = this.isNetworkConnected()
        fixedBanner.adListener = object : AdListener() {
            override fun onAdLoaded() {
                constraintLayout.updatePadding(0, 0, 0, 160)
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                constraintLayout.updatePadding(0, 0, 0, 0)
            }

            override fun onAdClosed() {
                constraintLayout.updatePadding(0, 0, 0, 0)
            }
        }
        checkForAppUpdate()

    }

    override fun onStop() {
        super.onStop()
        fixedBanner.removeAllViews()
        fixedBanner.destroy()
        mAppUpdateManager?.unregisterListener(appUpdatedListener)
    }

    override fun onPause() {
        super.onPause()
        navController.addOnDestinationChangedListener(listener)
        language = if (localeHelper.getDefaultLanguage(this) == "en") "en" else "ar"
        isConnected = this.isNetworkConnected()
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) popupSnackbarForCompleteUpdate()
                try {
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                        appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.IMMEDIATE,
                            this,
                            APP_UPDATE_REQUEST_CODE)
                    }
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "MainActivity")
        }
        navController.addOnDestinationChangedListener(listener)
        language = if (localeHelper.getDefaultLanguage(this) == "en") "en" else "ar"
        isConnected = this.isNetworkConnected()
        loadAds()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base?.let {
            localeHelper.getDefaultLanguage(it)?.let { localeHelper.setLocale(base, it) }
        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeHelper.getDefaultLanguage(this)?.let { localeHelper.setLocale(this, it) }
    }

    fun loadAds() {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { fixedBanner.loadAd(AdRequest.Builder().build()) }
        }
    }

    private fun checkForAppUpdate() {
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                try {
                    val installType = when {
                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE) -> AppUpdateType.FLEXIBLE
                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) -> AppUpdateType.IMMEDIATE
                        else -> null
                    }
                    if (installType == AppUpdateType.FLEXIBLE) {
                        appUpdateManager.registerListener(appUpdatedListener)
                    }
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        installType!!,
                        this,
                        APP_UPDATE_REQUEST_CODE)
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == APP_UPDATE_REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                Toast.makeText(this,
                    "App Update failed, please try again on the next app launch.",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun popupSnackbarForCompleteUpdate() {
        val snackbar = Snackbar.make(
            findViewById(R.id.mainConstraint),
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("RESTART") { appUpdateManager.completeUpdate() }
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        snackbar.show()
    }

}