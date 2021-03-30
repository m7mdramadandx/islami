package com.ramadan.islami.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.updatePadding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Topic
import com.ramadan.islami.ui.activity.MainActivity.Companion.language
import com.ramadan.islami.ui.adapter.TopicAdapter
import com.ramadan.islami.ui.viewModel.FirebaseViewModel
import com.ramadan.islami.ui.viewModel.LocalViewModel
import com.ramadan.islami.utils.LocaleHelper
import com.ramadan.islami.utils.debug_tag
import kotlinx.android.synthetic.main.activity_topic.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TopicDetails : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(FirebaseViewModel::class.java) }
    private val localViewModel by lazy { ViewModelProvider(this).get(LocalViewModel::class.java) }
    private var topic: Topic? = null
    private lateinit var topicAdapter: TopicAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var adView: AdView
    private lateinit var localeHelper: LocaleHelper

    override fun onStart() {
        super.onStart()
        intent.getStringExtra("intentKey")?.let {
            when (it) {
                "morningAzkar" -> fetchMorningAzkar()
                "eveningAzkar" -> fetchEveningAzkar()
                "azkar" -> fetchDayAzkar()
                "topic" -> fetchTopic()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        MainActivity.firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, title.toString())
        }
        loadAds()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = topic?.title
        setSupportActionBar(toolbar)
        topicAdapter = TopicAdapter()
        recyclerView = findViewById(R.id.contentRecyclerView)
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = topicAdapter
        @Suppress("DEPRECATION")
        toolbar_layout.setContentScrimColor(resources.getColor(R.color.colorPrimary))
        toolbar_layout.setCollapsedTitleTextColor(Color.WHITE)
        toolbar_layout.setBackgroundResource(R.drawable.asset)
        toolbar_layout.setExpandedTitleColor(Color.WHITE)
        intent.getStringExtra("collectionId")?.let {
            good.setOnCheckedChangeListener { _, isChecked ->
                viewModel.rateTopic(language, it, topic!!.id, isChecked)
            }
            bad.setOnCheckedChangeListener { _, isChecked ->
                viewModel.rateTopic(language, it, topic!!.id, !isChecked)
            }
        }
        localeHelper = LocaleHelper()
        adView = findViewById(R.id.adView)
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                contentView.updatePadding(0, 0, 0, 160)
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                contentView.updatePadding(0, 0, 0, 0)
                Log.e(debug_tag, adError.message)
            }

            override fun onAdLeftApplication() {
                contentView.updatePadding(0, 0, 0, 0)
            }

            override fun onAdClosed() {
                contentView.updatePadding(0, 0, 0, 0)
            }
        }
    }


    private fun fetchTopic() {
        if (intent.hasExtra("topic")) {
            topic = intent.getSerializableExtra("topic") as Topic
            observeData()
        } else fetchNotification()
    }

    private fun fetchNotification() {
        val collectionID = intent.getStringExtra("collectionID").toString()
        val documentID = intent.getStringExtra("documentID").toString()
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                topic = viewModel.fetchTopic(language, collectionID, documentID)
                observeData()
            }
        }
    }

    private fun observeData() {
        topic?.let {
            title = it.title
            topicAdapter
                .setTopicContent(it.content as MutableMap<String, String>)
        }
    }

    private fun fetchEveningAzkar() {
        title = getString(R.string.eveningAzkar)
        localViewModel.getAzkar(this)?.let { azkar ->
            topicAdapter.setAzkarDataList(azkar.filter {
                it.category == "أذكار المساء"
            }.toMutableList())
        }
    }

    private fun fetchMorningAzkar() {
        title = getString(R.string.morningAzkar)
        localViewModel.getAzkar(this)?.let { azkar ->
            topicAdapter.setAzkarDataList(azkar.filter {
                it.category == "أذكار الصباح"
            }.toMutableList())
        }
    }

    private fun fetchDayAzkar() {
        title = getString(R.string.azkar)
        localViewModel.getAzkar(this)?.let { azkar ->
            azkar.shuffle()
            topicAdapter.setAzkarDataList(azkar.take(15).toMutableList())
        }
    }


    private fun loadAds() {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { adView.loadAd(AdRequest.Builder().build()) }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}