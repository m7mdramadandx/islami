package com.ramadan.islami.ui.activity

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
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
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.ramadan.islami.R
import com.ramadan.islami.utils.LocaleHelper
import com.ramadan.islami.utils.debug_tag
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

    companion object {
        var language: String = "ar"
        var isConnected: Boolean = true
        lateinit var firebaseAnalytics: FirebaseAnalytics
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
                Log.e(debug_tag, "LOADED")
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                constraintLayout.updatePadding(0, 0, 0, 0)
                Log.e(debug_tag, adError.message)
            }

            override fun onAdClicked() {
                Log.e(debug_tag, "CLICKED")
            }

            override fun onAdClosed() {
                constraintLayout.updatePadding(0, 0, 0, 0)
                Log.e(debug_tag, "CLOSED")
            }
        }
    }

    override fun onStop() {
        super.onStop()
        fixedBanner.removeAllViews()
        fixedBanner.destroy()
    }

    override fun onPause() {
        super.onPause()
        navController.addOnDestinationChangedListener(listener)
        language = if (localeHelper.getDefaultLanguage(this) == "en") "en" else "ar"
        isConnected = this.isNetworkConnected()
    }

    override fun onResume() {
        super.onResume()
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
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

}