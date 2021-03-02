package com.ramadan.islami.ui.activity

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val localeHelper = LocaleHelper()
    private lateinit var listener: NavController.OnDestinationChangedListener
    private lateinit var navController: NavController
    private lateinit var fixedBanner: AdView
    private val appURL = ""

    companion object {
        var language: String = "ar"
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
                }
            }
    }

    override fun onPause() {
        super.onPause()
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
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

}