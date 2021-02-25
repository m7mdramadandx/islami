package com.ramadan.islami.ui.activity

import android.content.Context
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.ramadan.islami.R
import com.ramadan.islami.utils.LocaleHelper

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val localeHelper = LocaleHelper()
    private lateinit var listener: NavController.OnDestinationChangedListener
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        toolbar.setTitleTextColor(resources.R.color.colorPrimary)
        setSupportActionBar(toolbar)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)

        navView.setupWithNavController(navController)
nav.
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val colorDrawable = ColorDrawable(R.drawable.asset)
        val colorBackground = ColorDrawable(resources.getColor(R.color.colorBackground))
//        listener =
//            NavController.OnDestinationChangedListener { controller, destination, arguments ->
//                when (destination.id) {
//                    R.id.nav_stories -> supportActionBar?.setBackgroundDrawable(colorDrawable)
//                    R.id.nav_daily -> supportActionBar?.setBackgroundDrawable(colorDrawable)
//                    R.id.nav_quotes -> supportActionBar?.setBackgroundDrawable(colorDrawable)
//                    R.id.nav_topics -> supportActionBar?.setBackgroundDrawable(colorDrawable)
//                    R.id.nav_family_tree -> supportActionBar?.setBackgroundDrawable(colorDrawable)
//                    else -> supportActionBar?.setBackgroundDrawable(colorBackground)
//                }
//            }
    }

    override fun onPause() {
        super.onPause()
//        navController.addOnDestinationChangedListener(listener)
    }

    override fun onResume() {
        super.onResume()
//        navController.addOnDestinationChangedListener(listener)
    }


//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

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