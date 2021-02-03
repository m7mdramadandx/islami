package com.ramadan.islami.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.VideoAdapter
import com.ramadan.islami.ui.viewModel.ViewModel
import com.ramadan.islami.utils.LocaleHelper
import kotlinx.android.synthetic.main.recycle_view.*

class Videos : AppCompatActivity() {
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModel::class.java) }
    private var isEnglish: Boolean = true
    private val localeHelper = LocaleHelper()

    override fun onStart() {
        super.onStart()
        observeDate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycle_view)
        progress.visibility = View.GONE
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        isEnglish = localeHelper.getDefaultLanguage(this) == "en"
        recyclerView = findViewById(R.id.recycler_view)
        videoAdapter = VideoAdapter(this.lifecycle)
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = videoAdapter
    }

    private fun observeDate() {
        viewModel.fetchVideos(isEnglish).observe(this, { videoAdapter.setDataList(it) })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}