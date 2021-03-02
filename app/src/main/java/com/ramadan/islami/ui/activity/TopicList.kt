package com.ramadan.islami.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ramadan.islami.R
import com.ramadan.islami.ui.activity.MainActivity.Companion.language
import com.ramadan.islami.ui.adapter.TopicAdapter
import com.ramadan.islami.ui.viewModel.DataViewModel
import com.ramadan.islami.ui.viewModel.Listener
import com.ramadan.islami.utils.LocaleHelper
import kotlinx.android.synthetic.main.recycler_view.*


class TopicList : AppCompatActivity(), Listener {
    private val viewModel by lazy { ViewModelProvider(this).get(DataViewModel::class.java) }
    private var isEnglish: Boolean = true
    private val localeHelper = LocaleHelper()
    private lateinit var topicAdapter: TopicAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onStart() {
        super.onStart()
        observeData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view)
        isEnglish = localeHelper.getDefaultLanguage(this) == "en"
        recyclerView = findViewById(R.id.global_recycler_view)
        topicAdapter = TopicAdapter()
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = topicAdapter
        viewModel.listener = this
    }

    private fun observeData() {
        val collectionId = intent.getStringExtra("collectionId").toString()
        viewModel.fetchTopics(language, collectionId)
            .observe(this, { topicAdapter.setTopicDataList(it, collectionId) })
    }


    override fun onStarted() {}

    override fun onSuccess() {
        progress.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        progress.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}