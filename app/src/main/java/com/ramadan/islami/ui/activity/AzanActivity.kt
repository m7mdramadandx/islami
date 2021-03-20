package com.ramadan.islami.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import com.ramadan.islami.Azan
import com.ramadan.islami.R
import com.ramadan.islami.data.api.ApiHelper
import com.ramadan.islami.data.api.RetrofitBuilder
import com.ramadan.islami.ui.viewModel.ViewModelFactory
import com.ramadan.islami.ui.viewModel.WebServiceViewModel
import com.ramadan.islami.utils.LocaleHelper
import com.ramadan.islami.utils.ResponseStatus
import kotlinx.android.synthetic.main.activity_azan.*
import kotlinx.android.synthetic.main.fragment_schedule_prayer.*
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
            if (it) {
                NotificationManagerCompat.from(this).cancel(1001)
                prayName.text = intent.getStringExtra("prayName")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Handler().postDelayed({
            Azan().setAlarm(this)
        }, 70000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_azan)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        findViewById<ImageView>(R.id.mute).setOnClickListener {
            Azan.mediaPlayer.pause()
            it.isActivated = false
        }
    }


    private fun observeDate(lat: Double, lon: Double) {
        val localeHelper = LocaleHelper()
        viewModel.fetchPrayers(lat, lon).observe(this, {
            if (it.status == ResponseStatus.SUCCESS) {
                progress.visibility = View.GONE
                localeHelper.setPrayerTimes(this, it.data!!.data[selectedDate - 1].timings)
            }
        })
    }


}