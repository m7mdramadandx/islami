package com.ramadan.islami.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ramadan.islami.R

class Qibla : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.date_conversion)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}