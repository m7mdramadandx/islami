package com.ramadan.islami.ui.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.navigation.NavigationView
import com.ramadan.islami.R
import com.ramadan.islami.utils.LocaleHelper
import kotlinx.android.synthetic.main.main_content.*

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val localeHelper = LocaleHelper()
    private lateinit var listener: NavController.OnDestinationChangedListener
    private lateinit var navController: NavController
    private lateinit var fixedBanner: AdView
    private val appURL = ""

    companion object {
        var language: String = "ar"
        const val PERMISSION_ACCESS_FINE_LOCATION: Int = 101

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (localeHelper.getDefaultLanguage(this) == "en") language = "en"
        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        toolbar.setTitleTextColor(resources.R.color.colorPrimary)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)

        navView.setupWithNavController(navController)
//        navView.setNavigationItemSelectedListener(this)
        navView.itemIconTintList = null
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        fixedBanner = findViewById(R.id.fixedBanner)
        fixedBanner.loadAd(AdRequest.Builder().build())

        val colorDrawable = ColorDrawable(R.drawable.asset)
        val colorBackground = ColorDrawable(resources.getColor(R.color.colorBackground))
        listener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                when (destination.id) {
                    R.id.nav_share -> {
                        Intent(Intent.ACTION_SEND).also {
                            it.type = "text/plain"
                            it.putExtra(Intent.EXTRA_TEXT,
                                "I suggest this app for you : ${appURL}")
                            startActivity(it)
                        }
                    }
//                    R.id.nav_quran ->
                }
            }
    }

    override fun onPause() {
        super.onPause()
        navController.addOnDestinationChangedListener(listener)
        fixedBanner.loadAd(AdRequest.Builder().build())
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
        fixedBanner.loadAd(AdRequest.Builder().build())
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(localeHelper.setLocale(base!!,
            localeHelper.getDefaultLanguage(base)))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeHelper.setLocale(this, localeHelper.getDefaultLanguage(this))
    }


    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                AlertDialog.Builder(this)
                    .setTitle("Location Permission")
                    .setMessage("This app require access the location.")
                    .setPositiveButton("Ask me") { _, _ ->
                        requestLocationPermission()
                    }
                    .setNegativeButton("No") { _, _ ->
                        notifyDetailFragment(false)
                    }
                    .show()
            } else {
                requestLocationPermission()
            }
        } else {
            notifyDetailFragment(true)
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ACCESS_FINE_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            PERMISSION_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    notifyDetailFragment(true)
                } else {
                    notifyDetailFragment(false)
                }
            }
        }
    }

    private fun notifyDetailFragment(permissionGranted: Boolean) {
        if (permissionGranted) {
            val activeFragment = nav_host_fragment.childFragmentManager.primaryNavigationFragment
//            if (activeFragment is HomeFragment) {
//                activeFragment.onPermissionSuccess()
//            }
        } else {
            finish()
        }
    }

}