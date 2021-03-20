@file:Suppress("DEPRECATION")

package com.ramadan.islami.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.messaging.FirebaseMessaging
import com.ramadan.islami.R
import com.ramadan.islami.utils.LocaleHelper
import com.ramadan.islami.utils.debug_tag


class SplashScreen : AppCompatActivity() {
    private val localeHelper = LocaleHelper()
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        appTheme()
        supportActionBar?.hide()
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        FirebaseMessaging.getInstance().subscribeToTopic("allUsers")
//        BadgeUtils().clearBadge(this)
        Handler().postDelayed({
            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this,
//            "ca-app-pub-3940256099942544/1033173712",
            getString(R.string.interstitialAd),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(debug_tag, adError.message)
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    mInterstitialAd.show(this@SplashScreen)
                }
            })
    }

    private fun appTheme() {
        when (localeHelper.getDefaultTheme(this)) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "night" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}