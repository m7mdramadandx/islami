package com.ramadan.islami.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ramadan.islami.R
import kotlinx.android.synthetic.main.topics.*

class Topics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.topics)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        videosCard.setOnClickListener { startActivity(Intent(this, Videos::class.java)) }

    }


}