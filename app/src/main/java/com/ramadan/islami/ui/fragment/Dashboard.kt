package com.ramadan.islami.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.ramadan.islami.R
import com.ramadan.islami.data.api.ApiHelper
import com.ramadan.islami.data.api.RetrofitBuilder
import com.ramadan.islami.data.listener.DataListener
import com.ramadan.islami.ui.activity.MainActivity.Companion.language
import com.ramadan.islami.ui.adapter.RecyclerViewAdapter
import com.ramadan.islami.ui.adapter.SliderAdapter
import com.ramadan.islami.ui.viewModel.ApiViewModel
import com.ramadan.islami.ui.viewModel.DataViewModel
import com.ramadan.islami.ui.viewModel.ViewModelFactory
import com.ramadan.islami.utils.Utils
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class Dashboard : Fragment(), DataListener {

    private val dataViewModel by lazy { ViewModelProvider(this).get(DataViewModel::class.java) }
    private val apiViewModel by lazy {
        ViewModelProvider(this,
            ViewModelFactory(ApiHelper(RetrofitBuilder("http://api.aladhan.com/").hijriCalender()))
        ).get(ApiViewModel::class.java)
    }
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        suggestionAdapter = RecyclerViewAdapter()
        dailyAdapter = RecyclerViewAdapter()
        familyTreeAdapter = RecyclerViewAdapter()
        utils = Utils(context)
        hijriToday = UmmalquraCalendar(Locale.getDefault())
//        hijriToday.set(
//            hijriToday[UmmalquraCalendar.YEAR],
//            hijriToday[UmmalquraCalendar.MONTH],
//            hijriToday[UmmalquraCalendar.DAY_OF_MONTH],
//            hijriToday[UmmalquraCalendar.HOUR_OF_DAY],
//            hijriToday[UmmalquraCalendar.MINUTE],
//        )
        observeData()
    }

    override fun onResume() {
        super.onResume()
        observeData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        mAdView = root.findViewById(R.id.adView)
        dataViewModel.dataListener = this
        val hijriDate = root.findViewById<TextView>(R.id.hijriDate)
        hijriDate.text = hijriToday.time.toString()

        suggestionRCV = root.findViewById(R.id.suggestionRecyclerView)
        suggestionRCV.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        suggestionRCV.adapter = suggestionAdapter

        dailyRCV = root.findViewById(R.id.dailyRecyclerView)
        dailyRCV.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        dailyRCV.adapter = dailyAdapter

        familyTreeRCV = root.findViewById(R.id.familyTreeRecyclerView)
        familyTreeRCV.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        familyTreeRCV.adapter = familyTreeAdapter

        storiesSlider = root.findViewById(R.id.storiesSlider)
        storiesSlider.setSliderAdapter(storiesAdapter)
        storiesSlider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_RTL
        storiesSlider.startAutoCycle()
        storiesSlider.isAutoCycle = true
        storiesSlider.setIndicatorAnimation(IndicatorAnimations.THIN_WORM)
        storiesSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)

        quotesSlider = root.findViewById(R.id.quotesSlider)
        quotesSlider.setSliderAdapter(quotesAdapter)
        quotesSlider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_RTL
        quotesSlider.startAutoCycle()
        quotesSlider.isAutoCycle = true
        quotesSlider.setIndicatorAnimation(IndicatorAnimations.THIN_WORM)
        quotesSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)

//        storiesCard.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
//        quotesCard.setOnClickListener { startActivity(Intent(this, QuoteDashboard::class.java)) }
//        familyTreeCard.setOnClickListener { startActivity(Intent(this, FamilyTree::class.java)) }
//        topics.setOnClickListener { startActivity(Intent(this, Collection::class.java)) }

        return root
    }

    private fun observeData() {
//        apiViewModel.hijriCalender(dateOfDay()).observe(this, {
//            when (it.status) {
//                ResStatus.LOADING -> hijriDate.text = "تاريخ اليوم"
//                ResStatus.SUCCESS -> {
//                    (it.data!!.data.hijri.weekday.ar + getString(R.string.mn) + it.data.data.hijri.day + it.data.data.hijri.month.ar + "\t" + it.data.data.hijri.year)
//                        .also { date -> hijriDate.text = date }
//                }
//                ResStatus.ERROR -> Log.e(debug_tag, it.message.toString())
//            }
//        })

        suggestionAdapter.setSuggestionDataList(utils.suggestionMutableList)
        dataViewModel.fetchStories(language)
            .observe(this, { storiesAdapter.setStoriesDataList(it) })
        dailyAdapter.setDailyDataList(utils.dailyMutableList)
        dataViewModel.fetchQuotes(language)
            .observe(this, { quotesAdapter.setCategoryDataList(it) })
        familyTreeAdapter.setFamilyTreeDataList(utils.familyTreeMutableList, true)
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { mAdView.loadAd(AdRequest.Builder().build()) }
        }
    }

//    private fun initMenuFragment() {
//        val menuParams = MenuParams(
//            actionBarSize = resources.getDimension(R.dimen.tool_bar_height).toInt(),
//            menuObjects = getMenuObjects(),
//            isClosableOutside = true,
//            gravity = MenuGravity.START,
//        )
//        contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams).apply {
//            menuItemClickListener = { view, position ->
//                when (position) {
//                    0 -> startActivity(Intent(view.context, StoryDashboard::class.java))
//                    1 -> startActivity(Intent(view.context, QuoteDashboard::class.java))
//                    2 -> startActivity(Intent(view.context, FamilyTree::class.java))
//                    3 -> startActivity(Intent(view.context, Collection::class.java))
//                    4 -> {
//                        alertDialog("Theme setting",
//                            getString(R.string.light_theme),
//                            getString(R.string.night_theme),
//                            false)
//                    }
//                    5 -> {
//                        alertDialog("Language setting",
//                            getString(R.string.arabic),
//                            getString(R.string.english),
//                            true)
//                    }
//                    6 -> startActivity(Intent(view.context, StoryDashboard::class.java))
//                    7 -> {
//                        Intent(Intent.ACTION_SEND).also {
//                            it.type = "text/plain"
//                            it.putExtra(Intent.EXTRA_TEXT,
//                                "I suggest this app for you : ${appURL}")
//                            startActivity(it)
//                        }
//                    }
//                    8 -> startActivity(Intent(view.context, SendFeedback::class.java))
//                    9 -> startActivity(Intent(view.context, HadithOfDay::class.java))
//                }
//            }
//        }
//    }
//
//    private fun getMenuObjects() = mutableListOf<MenuObject>().apply {
//        val primaryColor = Color.rgb(22, 36, 71)
//        val primaryColorDark = Color.rgb(23, 34, 59)
//        MenuObject(getString(R.string.stories)).apply {
//            setResourceValue(R.drawable.story)
//            setBgColorValue(primaryColor)
//            add(this)
//        }
//        MenuObject(getString(R.string.quotes)).apply {
//            setResourceValue(R.drawable.quote)
//            setBgColorValue(primaryColorDark)
//            add(this)
//        }
//        MenuObject(getString(R.string.prophets_tree)).apply {
//            setResourceValue(R.drawable.family_tree)
//            setBgColorValue(primaryColor)
//            add(this)
//        }
//        MenuObject(getString(R.string.topics)).apply {
//            setResourceValue(R.drawable.topics)
//            setBgColorValue(primaryColorDark)
//            add(this)
//        }
//        MenuObject(getString(R.string.themes)).apply {
//            setResourceValue(R.drawable.theme)
//            setBgColorValue(primaryColor)
//            add(this)
//        }
//        MenuObject(getString(R.string.language)).apply {
//            setResourceValue(R.drawable.language)
//            setBgColorValue(primaryColorDark)
//            add(this)
//        }
//        MenuObject(getString(R.string.rate_app)).apply {
//            setResourceValue(R.drawable.rating)
//            setBgColorValue(primaryColor)
//            add(this)
//        }
//        MenuObject(getString(R.string.share)).apply {
//            setResourceValue(R.drawable.share)
//            setBgColorValue(primaryColorDark)
//            add(this)
//        }
//        MenuObject(getString(R.string.send_feedback)).apply {
//            setResourceValue(R.drawable.feedback)
//            setBgColorValue(primaryColor)
//            add(this)
//        }
//        MenuObject(getString(R.string.about_app)).apply {
//            setResourceValue(R.drawable.info)
//            setBgColorValue(primaryColorDark)
//            add(this)
//        }
//    }

    override fun onStarted() {}

    override fun onSuccess() {
        progress.visibility = View.GONE
        progress1.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        progress.visibility = View.GONE
        progress1.visibility = View.GONE
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}