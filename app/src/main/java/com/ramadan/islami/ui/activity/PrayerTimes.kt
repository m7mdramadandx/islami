package com.ramadan.islami.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
import com.ramadan.islami.ui.adapter.TableAdapter
import com.ramadan.islami.ui.viewModel.ViewModelFactory
import com.ramadan.islami.ui.viewModel.WebServiceViewModel
import com.ramadan.islami.utils.LocaleHelper
import com.ramadan.islami.utils.ResponseStatus
import com.ramadan.islami.utils.dateOfDay
import com.ramadan.islami.utils.debug_tag
import com.vivekkaushik.datepicker.DatePickerTimeline
import com.vivekkaushik.datepicker.OnDateSelectedListener
import kotlinx.android.synthetic.main.fragment_schedule_prayer.*
import java.util.*


class PrayerTimes : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this,
            ViewModelFactory(ApiHelper(RetrofitBuilder("http://api.aladhan.com/").apiService()))
        ).get(WebServiceViewModel::class.java)
    }
    private val ACCESS_FINE_LOCATION_REQ_CODE = 35
    private lateinit var tableAdapter: TableAdapter
    private lateinit var recyclerView: RecyclerView
    private val localeHelper = LocaleHelper()
    private lateinit var gregorianToday: Calendar
    private lateinit var prayer: Prayer
    private var selectedDate = Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_schedule_prayer)
        recyclerView = findViewById(R.id.rv_schedule_prayer)
        tableAdapter = TableAdapter()
        recyclerView.adapter = tableAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        gregorianToday = Calendar.getInstance()
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyPermission()) {
                requestForSpecificPermission()
            } else {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    observeDate(it.latitude, it.longitude)
                }
                fusedLocationClient.lastLocation.addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
        val datePickerTimeline = findViewById<DatePickerTimeline>(R.id.dtp_schedule_prayer)
        datePickerTimeline.setInitialDate(
            gregorianToday[Calendar.YEAR],
            gregorianToday[Calendar.MONTH],
            gregorianToday[Calendar.DAY_OF_MONTH],
        )
        datePickerTimeline.setOnDateSelectedListener(object : OnDateSelectedListener {
            override fun onDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int) {
                Log.e(debug_tag, "$dayOfWeek -- $day")
//                selectedDate = day
                tableAdapter.setSchedulePrayer(prayer.data[day])
                scheduleDay.text = gregorianToday[Calendar.DAY_OF_WEEK].toString()
                scheduleDate.text = "$year - $month $day"
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

        monthView.setOnClickListener {
            Intent(this, MonthPrayerTimes::class.java).also {
                it.putExtra("prayer", prayer)
                startActivity(it)
            }
        }
    }


    private fun checkIfAlreadyPermission(): Boolean {
        val result =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION_REQ_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            ACCESS_FINE_LOCATION_REQ_CODE -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "guides", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, getString(R.string.couldnotDownload), Toast.LENGTH_LONG)
                    .show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun observeDate(lat: Double, lon: Double) {
        viewModel.fetchPrayers(lat, lon).observe(this, {
            when (it.status) {
                ResponseStatus.LOADING -> progress.visibility = View.VISIBLE
                ResponseStatus.SUCCESS -> {
                    progress.visibility = View.GONE
                    val month = it.data!!.data.first().date.gregorian.month.en
                    title = "${getString(R.string.prayerTimesTitle)} $month"
                    prayer = it.data
                    scheduleDate.text = dateOfDay()
                    it.data.data.forEach {
                        if (it.date.gregorian.day == dateOfDay()) {
                            scheduleDay.text = it.date.hijri.weekday.ar
                        }
                    }
//                    localeHelper.setPrayerTimes(this, it.data.data as MutableList<String>)
                }
                ResponseStatus.ERROR -> {
                    progress.visibility = View.GONE
                    Log.e(debug_tag, it.message.toString())
                }
            }
        })
    }
}