@file:Suppress("DEPRECATION")

package com.ramadan.islamicAwareness.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.ramadan.islamicAwareness.R


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        supportActionBar?.hide()
        Handler().postDelayed({
            startActivity(Intent(this, Dashboard::class.java))
            finish()
        }, 1500)
    }
}