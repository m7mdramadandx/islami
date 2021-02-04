package com.ramadan.islami.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.VideoAdapter
import kotlinx.android.synthetic.main.recycle_view.*
import com.ramadan.islami.data.model.Video as VideoModel

class Video : AppCompatActivity() {
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var video: VideoModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycle_view)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        video = intent.getSerializableExtra("videos") as VideoModel
        supportActionBar?.title = video.title
        recyclerView = findViewById(R.id.recycler_view)
        videoAdapter = VideoAdapter(lifecycle)
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = videoAdapter
        observeData()
        progress.visibility = View.GONE
    }

    private fun observeData() {
        videoAdapter.setVideoList(video.videosID)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}