package com.ramadan.islami.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Video
import com.ramadan.islami.ui.adapter.VideoAdapter
import com.ramadan.islami.ui.viewModel.FirebaseViewModel
import kotlinx.android.synthetic.main.recycler_view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoDetails : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(FirebaseViewModel::class.java) }
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var recyclerView: RecyclerView
    private var video: Video? = null

    override fun onStart() {
        super.onStart()
        intent.getStringExtra("intentKey")?.let {
            if (it == "videoObj") {
                video = intent.getSerializableExtra("videoObj") as Video
                observeData()
            } else if (it == "video") fetchNotification()
        }
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
        videoAdapter = VideoAdapter(lifecycle)
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = videoAdapter
        progress.visibility = View.GONE
    }

    private fun fetchNotification() {
        val collectionID = intent.getStringExtra("collectionID").toString()
        val documentID = intent.getStringExtra("documentID").toString()
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                video = viewModel.fetchVideo(MainActivity.language, collectionID, documentID)
                observeData()
            }
        }
    }

    private fun observeData() {
        video?.let {
            title = it.title
            videoAdapter.setVideoList(it.videosID)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}