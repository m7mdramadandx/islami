package com.ramadan.islami.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationServices
import com.ramadan.islami.R
import com.ramadan.islami.data.api.ApiHelper
import com.ramadan.islami.data.api.RetrofitBuilder
import com.ramadan.islami.data.model.Prayer
import com.ramadan.islami.ui.adapter.PrayTimeAdapter
import com.ramadan.islami.ui.viewModel.ViewModelFactory
import com.ramadan.islami.ui.viewModel.WebServiceViewModel
import com.ramadan.islami.utils.*
import com.vivekkaushik.datepicker.DatePickerTimeline
import com.vivekkaushik.datepicker.OnDateSelectedListener
import kotlinx.android.synthetic.main.fragment_schedule_prayer.*
import java.util.*

class PrayerTimes : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder("http://api.aladhan.com/").apiService()))
        ).get(WebServiceViewModel::class.java)
    }
    private lateinit var prayTimeAdapter: PrayTimeAdapter
    private lateinit var recyclerView: RecyclerView
    private val localeHelper = LocaleHelper()
    private lateinit var gregorianToday: Calendar
    private var prayer: Prayer? = null
    private var selectedDate: Int = 0
    private lateinit var utils: Utils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_schedule_prayer)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerView = findViewById(R.id.rv_schedule_prayer)
        prayTimeAdapter = PrayTimeAdapter()
        utils = Utils(this)
        recyclerView.adapter = prayTimeAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        gregorianToday = Calendar.getInstance()
        selectedDate = gregorianToday[Calendar.DAY_OF_MONTH]
        scheduleDate.text = dateOfDay()
        scheduleDay.text = utils.weekday[gregorianToday[Calendar.DAY_OF_WEEK]]

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyPermission()) requestForSpecificPermission() else fetchDate()
        }

        findViewById<DatePickerTimeline>(R.id.dtp_schedule_prayer).apply {
            setInitialDate(
                gregorianToday[Calendar.YEAR],
                gregorianToday[Calendar.MONTH],
                gregorianToday[Calendar.DAY_OF_MONTH],
            )
            setActiveDate(gregorianToday)
            setOnDateSelectedListener(object : OnDateSelectedListener {
                override fun onDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int) {
                    prayer?.let {
                        selectedDate = day
                        prayTimeAdapter.setSchedulePrayer(prayer!!.data[day - 1])
                    } ?: snackBar(getString(R.string.noInternet))
                    scheduleDay.text = utils.weekday[dayOfWeek]
                    scheduleDate.text = "$day-${month + 1}-$year"
                }

                override fun onDisabledDateSelected(
                    year: Int,
                    month: Int,
                    day: Int,
                    dayOfWeek: Int,
                    isDisabled: Boolean,
                ) {
                    Log.e(debug_tag, "$dayOfWeek #### $day")
                }
            })
        }
    }


    private fun checkIfAlreadyPermission(): Boolean {
        val result =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestForSpecificPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION_REQ_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) = when (requestCode) {
        ACCESS_FINE_LOCATION_REQ_CODE -> {
            if (PackageManager.PERMISSION_GRANTED == grantResults[0]) fetchDate()
            else showMessage(this, getString(R.string.couldnotDownload))
        }
        else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    @SuppressLint("MissingPermission")
    private fun fetchDate() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener {
            observeDate(it.latitude, it.longitude)
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses: List<Address> =
                geocoder.getFromLocation(it.latitude, it.longitude, 1)
            scheduleLocation.text = addresses[0].adminArea
        }
        fusedLocationClient.lastLocation.addOnFailureListener {
            showMessage(this, it.localizedMessage!!)
        }
    }


    private fun observeDate(lat: Double, lon: Double) {
        viewModel.fetchPrayers(lat, lon).observe(this, { it ->
            when (it.status) {
                ResponseStatus.LOADING -> progress.visibility = View.VISIBLE
                ResponseStatus.SUCCESS -> {
                    progress.visibility = View.GONE
                    prayer = it.data!!
                    prayTimeAdapter.setSchedulePrayer(prayer!!.data[selectedDate])
                    localeHelper.setPrayerTimes(this, prayer!!.data[selectedDate].timings)
                }
                ResponseStatus.ERROR -> {
                    localeHelper.getPrayerTimes(this@PrayerTimes)?.let { mutableSet ->
                        prayTimeAdapter.setOfflinePrayer(mutableSet)
                    } ?: showMessage(this, getString(R.string.noInternet))
                    progress.visibility = View.GONE
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun monthView(view: View) {
        prayer?.let {
            Intent(this, MonthPrayerTimes::class.java).apply {
                putExtra("prayer", prayer)
                startActivity(this)
            }
        } ?: view.snackBar(getString(R.string.noInternet))
    }
}
