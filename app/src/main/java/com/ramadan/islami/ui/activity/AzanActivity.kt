package com.ramadan.islami.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ramadan.islami.Azan
import com.ramadan.islami.R
import com.ramadan.islami.data.api.ApiHelper
import com.ramadan.islami.data.api.RetrofitBuilder
import com.ramadan.islami.ui.viewModel.ViewModelFactory
import com.ramadan.islami.ui.viewModel.WebServiceViewModel
import com.ramadan.islami.utils.LocaleHelper
import com.ramadan.islami.utils.ResponseStatus
import com.ramadan.islami.utils.debug_tag
import com.ramadan.islami.utils.turnScreenOnAndKeyguardOff
import kotlinx.android.synthetic.main.activity_azan.*
import java.util.*

class AzanActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder("http://api.aladhan.com/").apiService()))
        ).get(WebServiceViewModel::class.java)
    }
    private var selectedDate: Int = 0
    private lateinit var gregorianToday: Calendar
    private var isPlaying = true

    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()
        gregorianToday = Calendar.getInstance()
        selectedDate = gregorianToday[Calendar.DAY_OF_MONTH]
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener {
            observeDate(it.latitude, it.longitude)
        }
        intent.hasExtra("prayName").let {
            if (it) (intent.getStringExtra("prayName")).also { prayName.text = it }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(debug_tag, "DESTROY")
        Azan().setAlarm(this)
    }

    override fun onStop() {
        super.onStop()
        Log.e(debug_tag, "STOP")
        Azan().setAlarm(this)
    }

    override fun onPause() {
        super.onPause()
        Log.e(debug_tag, "PAUSE")
        Azan().setAlarm(this)
    }

    override fun onResume() {
        super.onResume()
        MainActivity.firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, title.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_azan)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        findViewById<ImageView>(R.id.mute).setOnClickListener { Azan.mediaPlayer.pause() }
        turnScreenOnAndKeyguardOff()
    }


    private fun observeDate(lat: Double, lon: Double) {
        val localeHelper = LocaleHelper()
        viewModel.fetchPrayers(lat, lon).observe(this, {
            if (it.status == ResponseStatus.SUCCESS) localeHelper.setPrayerTimes(this,
                it.data!!.data[selectedDate - 1].timings)
        })
    }
}