package com.ramadan.islami.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import com.ramadan.islami.R
import com.ramadan.islami.data.listener.FirebaseListener
import com.ramadan.islami.ui.activity.MainActivity
import com.ramadan.islami.ui.activity.MainActivity.Companion.language
import com.ramadan.islami.ui.adapter.RecyclerViewAdapter
import com.ramadan.islami.ui.adapter.SliderAdapter
import com.ramadan.islami.ui.viewModel.FirebaseViewModel
import com.ramadan.islami.utils.Utils
import com.ramadan.islami.utils.changeNavigation
import com.ramadan.islami.utils.showToast
import com.ramadan.islami.utils.topicsImg
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class Dashboard : Fragment(), FirebaseListener {
    private val dataViewModel by lazy { ViewModelProvider(this).get(FirebaseViewModel::class.java) }
    private lateinit var hijriDate: TextView
    private lateinit var mAdView: AdView
    private lateinit var suggestionRCV: RecyclerView
    private lateinit var dailyRCV: RecyclerView
    private lateinit var familyTreeRCV: RecyclerView
    private lateinit var storiesSlider: SliderView
    private lateinit var quotesSlider: SliderView
    private lateinit var suggestionAdapter: RecyclerViewAdapter
    private lateinit var dailyAdapter: RecyclerViewAdapter
    private lateinit var topicsImage: ImageView
    private lateinit var familyTreeAdapter: RecyclerViewAdapter
    private val storiesAdapter = SliderAdapter()
    private val quotesAdapter = SliderAdapter()
    private lateinit var utils: Utils
    private lateinit var hijriToday: UmmalquraCalendar
    private lateinit var progress0: ProgressBar
    private lateinit var progress1: ProgressBar

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
        suggestionRCV = root.findViewById(R.id.suggestionRecyclerView)
        dailyRCV = root.findViewById(R.id.dailyRecyclerView)
        familyTreeRCV = root.findViewById(R.id.familyTreeRecyclerView)
        storiesSlider = root.findViewById(R.id.storiesSlider)
        quotesSlider = root.findViewById(R.id.quotesSlider)
        topicsImage = root.findViewById(R.id.topicsImg)
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


//        localeHelper.getPrayerTimes(view.context).let {
//            val format = SimpleDateFormat("hh:mm:ss")
//            ticktock.setOnTickListener {
//                val date = Date()
//                date.setTime(System.currentTimeMillis());
//                azan.getAlarmDate(view.context)?.let { date.time = it.timeInMillis }
//                format.format(date)
//            }
//            azan.getAlarmDate(view.context)?.let {
//                val start = Calendar.getInstance()
//                ticktock.start(start, it)
//            }
//
//        }
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

        Picasso.get().load(topicsImg).placeholder(R.drawable.load_img)
            .error(R.drawable.failure_img).into(topicsImage)
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
        showToast(requireContext(), message)
    }
}