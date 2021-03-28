package com.ramadan.islami.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ramadan.islami.Azan
import com.ramadan.islami.R
import com.ramadan.islami.data.api.ApiHelper
import com.ramadan.islami.data.api.RetrofitBuilder
import com.ramadan.islami.data.listener.FirebaseListener
import com.ramadan.islami.ui.activity.MainActivity
import com.ramadan.islami.ui.activity.MainActivity.Companion.language
import com.ramadan.islami.ui.adapter.RecyclerViewAdapter
import com.ramadan.islami.ui.adapter.SliderAdapter
import com.ramadan.islami.ui.viewModel.FirebaseViewModel
import com.ramadan.islami.ui.viewModel.ViewModelFactory
import com.ramadan.islami.ui.viewModel.WebServiceViewModel
import com.ramadan.islami.utils.Utils
import com.ramadan.islami.utils.changeNavigation
import com.ramadan.islami.utils.showMessage
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class Dashboard : Fragment(), FirebaseListener {

    private val dataViewModel by lazy { ViewModelProvider(this).get(FirebaseViewModel::class.java) }
    private val apiViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder("http://api.aladhan.com/").apiService()))
        ).get(WebServiceViewModel::class.java)
    }
    private lateinit var hijriDate: TextView
    private lateinit var nextPrayerTime: TextView
    private lateinit var mAdView: AdView
    private lateinit var suggestionRCV: RecyclerView
    private lateinit var dailyRCV: RecyclerView
    private lateinit var familyTreeRCV: RecyclerView
    private lateinit var storiesSlider: SliderView
    private lateinit var quotesSlider: SliderView
    private lateinit var suggestionAdapter: RecyclerViewAdapter
    private lateinit var dailyAdapter: RecyclerViewAdapter
    private lateinit var familyTreeAdapter: RecyclerViewAdapter
    private val storiesAdapter = SliderAdapter()
    private val quotesAdapter = SliderAdapter()
    private lateinit var utils: Utils
    private lateinit var hijriToday: UmmalquraCalendar
    private lateinit var progress0: ProgressBar
    private lateinit var progress1: ProgressBar
    private lateinit var timer: Chronometer

    override fun onAttach(context: Context) {
        super.onAttach(context)
        suggestionAdapter = RecyclerViewAdapter()
        dailyAdapter = RecyclerViewAdapter()
        familyTreeAdapter = RecyclerViewAdapter()
        utils = Utils(context)
        hijriToday = UmmalquraCalendar(Locale.getDefault())
        hijriToday.set(
            hijriToday[UmmalquraCalendar.YEAR],
            hijriToday[UmmalquraCalendar.MONTH],
            hijriToday[UmmalquraCalendar.DAY_OF_MONTH],
            hijriToday[UmmalquraCalendar.HOUR_OF_DAY],
            hijriToday[UmmalquraCalendar.MINUTE],
        )
        observeData()
    }

    override fun onResume() {
        super.onResume()
        observeData()
        loadAds()
        MainActivity.firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "Dashboard")
        }
    }

    override fun onPause() {
        super.onPause()
        mAdView.removeAllViews()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        mAdView = root.findViewById(R.id.adView)
        dataViewModel.firebaseListener = this
        hijriDate = root.findViewById(R.id.hijriDate)
        timer = root.findViewById(R.id.timer)
        suggestionRCV = root.findViewById(R.id.suggestionRecyclerView)
        dailyRCV = root.findViewById(R.id.dailyRecyclerView)
        familyTreeRCV = root.findViewById(R.id.familyTreeRecyclerView)
        storiesSlider = root.findViewById(R.id.storiesSlider)
        quotesSlider = root.findViewById(R.id.quotesSlider)
        progress0 = root.findViewById(R.id.progress0)
        progress1 = root.findViewById(R.id.progress1)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ("${utils.weekday[hijriToday[UmmalquraCalendar.DAY_OF_WEEK]]} " +
                "${hijriToday[UmmalquraCalendar.DAY_OF_MONTH]} " +
                "${utils.month[hijriToday[UmmalquraCalendar.MONTH]]} " +
                "${hijriToday[UmmalquraCalendar.YEAR]} ").also { hijriDate.text = it }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            timer.isCountDown = true
        }
//        timer.text = "00:00:00"
        timer.base = Azan().getAlarmDate(view.context)?.timeInMillis!!
        timer.start()
        timer.format = "HH:mm:ss"
        timer.setOnChronometerTickListener { chronometer ->
//            val time: Long = SystemClock.elapsedRealtime() - chronometer.base
            val time: Long = Azan().getAlarmDate(view.context)?.timeInMillis!!
            val h = (time / 3600000).toInt()
            val m = (time - h * 3600000).toInt() / 60000
            val s = (time - h * 3600000 - m * 60000).toInt() / 1000
            val t =
                (if (h < 10) "0$h" else h).toString() + ":" + (if (m < 10) "0$m" else m) + ":" + if (s < 10) "0$s" else s
            chronometer.text = t
        }

        suggestionRCV.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        suggestionRCV.adapter = suggestionAdapter

        dailyRCV.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        dailyRCV.adapter = dailyAdapter

        familyTreeRCV.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        familyTreeRCV.adapter = familyTreeAdapter

        storiesSlider.setSliderAdapter(storiesAdapter)
        storiesSlider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_RTL
        storiesSlider.startAutoCycle()
        storiesSlider.isAutoCycle = true
        storiesSlider.setIndicatorAnimation(IndicatorAnimations.THIN_WORM)
        storiesSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)

        quotesSlider.setSliderAdapter(quotesAdapter)
        quotesSlider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_RTL
        quotesSlider.startAutoCycle()
        quotesSlider.isAutoCycle = true
        quotesSlider.setIndicatorAnimation(IndicatorAnimations.THIN_WORM)
        quotesSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        view.findViewById<RelativeLayout>(R.id.storiesCard).setOnClickListener {
            it.changeNavigation(DashboardDirections.actionDashboardToStories())
        }
        view.findViewById<RelativeLayout>(R.id.dailyCard).setOnClickListener {
            it.changeNavigation(DashboardDirections.actionDashboardToDaily())
        }
        view.findViewById<RelativeLayout>(R.id.quotesCard).setOnClickListener {
            it.changeNavigation(DashboardDirections.actionDashboardToQuotes())
        }
        view.findViewById<CardView>(R.id.topics).setOnClickListener {
            it.changeNavigation(DashboardDirections.actionDashboardToTopics())
        }
        view.findViewById<RelativeLayout>(R.id.prophets_tree).setOnClickListener {
            it.changeNavigation(DashboardDirections.actionDashboardToFamilyTree())
        }
    }

    private fun observeData() {
        suggestionAdapter.setSuggestionDataList(utils.suggestionMutableList)
        dataViewModel.fetchStories(language)
            .observe(this, { storiesAdapter.setStoriesDataList(it) })
        dailyAdapter.setDailyDataList(utils.dailyMutableList, true)
        dataViewModel.fetchQuotes(language)
            .observe(this, { quotesAdapter.setCategoryDataList(it) })
        familyTreeAdapter.setFamilyTreeDataList(utils.familyTreeMutableList, true)
    }

    private fun loadAds() {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { mAdView.loadAd(AdRequest.Builder().build()) }
        }
    }

    override fun onStarted() {}

    override fun onSuccess() {
        progress0.visibility = View.GONE
        progress1.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        progress0.visibility = View.GONE
        progress1.visibility = View.GONE
        showMessage(requireContext(), message)
    }
}