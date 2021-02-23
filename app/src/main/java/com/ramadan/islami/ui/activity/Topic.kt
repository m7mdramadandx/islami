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
import com.ramadan.islami.data.model.Topic
import com.ramadan.islami.ui.adapter.TopicAdapter
import com.ramadan.islami.ui.viewModel.DataViewModel
import com.ramadan.islami.utils.LocaleHelper
import kotlinx.android.synthetic.main.content_nestest_view.*
import kotlinx.android.synthetic.main.topic.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Topic : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(DataViewModel::class.java) }
    private var topic: Topic? = null
    private lateinit var topicAdapter: TopicAdapter
    private lateinit var recyclerView: RecyclerView
    private var isEnglish: Boolean = true
    private val localeHelper = LocaleHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.topic)
        isEnglish = localeHelper.getDefaultLanguage(this) == "en"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (intent.hasExtra("topic")) topic = intent.getSerializableExtra("topic") as Topic
        else fetchNotification()
        val collectionId: String = intent.getStringExtra("collectionId").toString()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = topic?.title
        setSupportActionBar(toolbar)
        recyclerView = findViewById(R.id.contentRecyclerView)
        topicAdapter = TopicAdapter()
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = topicAdapter
        good.setOnCheckedChangeListener { _, isChecked ->
            viewModel.rateTopic(isEnglish, collectionId, topic!!.id, isChecked)
        }
        bad.setOnCheckedChangeListener { _, isChecked ->
            viewModel.rateTopic(isEnglish, collectionId, topic!!.id, !isChecked)
        }
        toolbar_layout.setContentScrimColor(resources.getColor(R.color.colorPrimary))
        toolbar_layout.setCollapsedTitleTextColor(Color.WHITE)
        toolbar_layout.setBackgroundResource(R.drawable.asset)
        toolbar_layout.setExpandedTitleColor(Color.WHITE)
        toolbar_layout.setExpandedTitleColor(resources.getColor(R.color.colorPrimary))
        observeData()
    }

    private fun observeData() {
        topicAdapter
            .setTopicContentDataList(topic!!.content as MutableMap<String, String>, topic!!.brief)
        supportActionBar?.title = topic!!.title
    }

    private fun fetchNotification() {
        val collectionID = intent.getStringExtra("collectionID").toString()
        val documentID = intent.getStringExtra("documentID").toString()
        GlobalScope.launch(Dispatchers.IO) {
            topic = viewModel.fetchTopic(isEnglish, collectionID, documentID)
            withContext(Dispatchers.Main) { observeData() }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}