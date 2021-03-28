package com.ramadan.islami.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ramadan.islami.R
import com.ramadan.islami.data.listener.FirebaseListener
import com.ramadan.islami.ui.activity.MainActivity.Companion.language
import com.ramadan.islami.ui.adapter.TopicAdapter
import com.ramadan.islami.ui.viewModel.FirebaseViewModel
import kotlinx.android.synthetic.main.recycler_view.*


class TopicList : AppCompatActivity(), FirebaseListener {
    private val viewModel by lazy { ViewModelProvider(this).get(FirebaseViewModel::class.java) }
    private lateinit var topicAdapter: TopicAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onStart() {
        super.onStart()
        observeData()
    }
    override fun onResume() {
        super.onResume()
        MainActivity.firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, title.toString())
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerView = findViewById(R.id.global_recycler_view)
        topicAdapter = TopicAdapter()
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = topicAdapter
        viewModel.firebaseListener = this
    }

    private fun observeData() {
        val collectionId = intent.getStringExtra("collectionId").toString()
        viewModel.fetchTopics(language, collectionId)
            .observe(this, { topicAdapter.setTopicDataList(it, collectionId) })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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