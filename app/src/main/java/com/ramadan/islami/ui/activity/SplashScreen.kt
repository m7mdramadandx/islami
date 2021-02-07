@file:Suppress("DEPRECATION")

package com.ramadan.islami.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.ramadan.islami.R
import com.ramadan.islami.utils.LocaleHelper


class SplashScreen : AppCompatActivity() {
    private val localeHelper = LocaleHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        appTheme()
        supportActionBar?.hide()
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        FirebaseMessaging.getInstance().subscribeToTopic("allUsers")

        Handler().postDelayed({
//            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this)
            startActivity(Intent(this, Dashboard::class.java))
            finish()
        }, 1500)
    }

    private fun appTheme() {
        when (localeHelper.getDefaultTheme(this)) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "night" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}