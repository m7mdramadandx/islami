package com.ramadan.islami.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ramadan.islami.R
import com.ramadan.islami.data.api.ApiHelper
import com.ramadan.islami.data.api.RetrofitBuilder
import com.ramadan.islami.ui.viewModel.ViewModelFactory
import com.ramadan.islami.ui.viewModel.WebServiceViewModel
import com.ramadan.islami.utils.ResponseStatus
import com.ramadan.islami.utils.debug_tag
import com.ramadan.islami.utils.showMessage
import kotlinx.android.synthetic.main.activity_date_conversion.*
import net.alhazmy13.hijridatepicker.date.gregorian.GregorianDatePickerDialog
import java.util.*


class DateConversion : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder("http://api.aladhan.com/").apiService()))
        ).get(WebServiceViewModel::class.java)
    }
    private var mRewardedAd: RewardedAd? = null
    private lateinit var hijriToday: UmmalquraCalendar
    private lateinit var gregorianToday: Calendar

    override fun onResume() {
        super.onResume()
        MainActivity.firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, title.toString())
        }
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_conversion)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        gregorianToday = Calendar.getInstance()
        hijriToday = UmmalquraCalendar()
        calendarView.setSelectedDate(hijriToday)
//        hijri.setOnClickListener {
//            try {
//                val dpd = HijriDatePickerDialog.newInstance(
//                    { _, year, monthOfYear, dayOfMonth ->
//                        setHijriDate(year, monthOfYear, dayOfMonth)
//                    },
//                    hijriToday[UmmalquraCalendar.YEAR],
//                    hijriToday[UmmalquraCalendar.MONTH],
//                    hijriToday[UmmalquraCalendar.DAY_OF_MONTH]
//                )
//                dpd.vibrate(true)
//                dpd.accentColor = resources.getColor(R.color.colorPrimary)
//                dpd.setOkText(R.string.ok)
//                dpd.setCancelText(R.string.cancel)
//                dpd.show(supportFragmentManager, "HijriDatePickerDialog")
//
//            } catch (e: Exception) {
//                it.snackBar(e.localizedMessage!! + "\n" + "Sorry, try it again later")
//            }
//        }
        gregorian.setOnClickListener {
            val dpd = GregorianDatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    setGregorianDate(year, monthOfYear, dayOfMonth)
                },
                gregorianToday[Calendar.YEAR],
                gregorianToday[Calendar.MONTH],
                gregorianToday[Calendar.DAY_OF_MONTH],
            )
            dpd.vibrate(true)
            dpd.accentColor = resources.getColor(R.color.colorPrimary)
            dpd.setOkText(R.string.ok)
            dpd.setCancelText(R.string.cancel)
            dpd.show(supportFragmentManager, "GregorianDatePickerDialog")

        }
    }

    private fun fetchHijri(gregorianDate: String) {
        val adRequest = AdRequest.Builder().build()
        viewModel.hijriCalender(gregorianDate).observe(this@DateConversion, {
            when (it.status) {
                ResponseStatus.LOADING -> conversionIcon.rotation = 180F
                ResponseStatus.SUCCESS -> {
                    conversionIcon.rotation = 90F
                    RewardedAd.load(this,
                        getString(R.string.date_conversion_ad),
                        adRequest,
                        object : RewardedAdLoadCallback() {
                            override fun onAdFailedToLoad(adError: LoadAdError) {
                                Log.d(debug_tag, adError.message)
                                mRewardedAd = null
                            }

                            override fun onAdLoaded(rewardedAd: RewardedAd) {
                                mRewardedAd = rewardedAd
                                mRewardedAd?.show(this@DateConversion) { _ ->
                                    hijri.text = it.data!!.data.hijri.date
                                }
                            }
                        })
                }
                ResponseStatus.ERROR -> {
                    conversionIcon.rotation = 90F
                    showMessage(this@DateConversion, getString(R.string.noInternet))
                }
            }
        })
    }


    private fun fetchGregorian(hijriDate: String) {
        val adRequest = AdRequest.Builder().build()
        viewModel.gregorianCalender(hijriDate).observe(this@DateConversion,
            {
                when (it.status) {
                    ResponseStatus.LOADING -> conversionIcon.rotation = 180F
                    ResponseStatus.SUCCESS -> {
                        conversionIcon.rotation = 90F
                        RewardedAd.load(this,
                            getString(R.string.date_conversion_ad),
                            adRequest,
                            object : RewardedAdLoadCallback() {
                                override fun onAdFailedToLoad(adError: LoadAdError) {
                                    Log.d(debug_tag, adError.message)
                                    mRewardedAd = null
                                }

                                override fun onAdLoaded(rewardedAd: RewardedAd) {
                                    mRewardedAd = rewardedAd
                                    mRewardedAd?.show(this@DateConversion) { item ->
                                        gregorian.text = it.data!!.data.gregorian.date
                                    }
                                }
                            })
                    }
                    ResponseStatus.ERROR -> {
                        conversionIcon.rotation = 90F
                        showMessage(this@DateConversion, getString(R.string.noInternet))
                    }
                }
            })
    }

    private fun setHijriDate(year: Int, month: Int, day: Int) {
        val hijriDate = StringBuilder().append(day).append("-")
            .append(month + 1).append("-").append(year)
        hijri.text = hijriDate
        fetchGregorian(hijriDate.toString())
    }

    private fun setGregorianDate(year: Int, month: Int, day: Int) {
        val gregorianDate = StringBuilder().append(day).append("-")
            .append(month + 1).append("-").append(year)
        gregorian.text = gregorianDate
        fetchHijri(gregorianDate.toString())
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}