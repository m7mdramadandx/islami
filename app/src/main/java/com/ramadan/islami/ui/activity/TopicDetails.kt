package com.ramadan.islami.ui.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Azkar
import com.ramadan.islami.data.model.Topic
import com.ramadan.islami.ui.activity.MainActivity.Companion.language
import com.ramadan.islami.ui.adapter.TopicAdapter
import com.ramadan.islami.ui.viewModel.FirebaseViewModel
import com.ramadan.islami.ui.viewModel.LocalViewModel
import kotlinx.android.synthetic.main.activity_topic.*
import kotlinx.android.synthetic.main.content_nested_view.*
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
    private var intentKey: String = ""
    private lateinit var azkar: Azkar.AzkarItem

    override fun onStart() {
        super.onStart()
        intent.getStringExtra("intentKey")?.let {
            when (it) {
                "morningAzkar" -> fetchMorningAzkar()
                "eveningAzkar" -> fetchEveningAzkar()
                "topic" -> fetchTopic()
            }
        }
    }

    private fun fetchTopic() {
        if (intent.hasExtra("topic")) topic = intent.getSerializableExtra("topic") as Topic
        else fetchNotification()
        observeData()
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
    }

    private fun observeData() {
        topicAdapter
            .setTopicContentDataList(topic!!.content as MutableMap<String, String>, topic!!.brief)
        title = topic!!.title
    }

    private fun fetchNotification() {
        val collectionID = intent.getStringExtra("collectionID").toString()
        val documentID = intent.getStringExtra("documentID").toString()
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                topic = viewModel.fetchTopic(language, collectionID, documentID)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}