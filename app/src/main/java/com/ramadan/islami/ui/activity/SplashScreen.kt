@file:Suppress("DEPRECATION")

package com.ramadan.islami.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
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
import kotlinx.android.synthetic.main.activity_splash_screen.*


class SplashScreen : AppCompatActivity() {
    private val localeHelper = LocaleHelper()
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        appTheme()
        supportActionBar?.hide()
        window!!.navigationBarColor = Color.TRANSPARENT
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        FirebaseMessaging.getInstance().subscribeToTopic("allUsers")
        val animation = AnimationUtils.loadAnimation(this, R.anim.pop_enter)
        animation.duration = 800
        islamiImg.animation = animation
        Handler().postDelayed({
            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)

        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,
            getString(R.string.interstitial),
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