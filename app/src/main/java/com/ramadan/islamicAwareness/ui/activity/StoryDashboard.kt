@file:Suppress("DEPRECATION")

package com.ramadan.islamicAwareness.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ramadan.islamicAwareness.R
import com.ramadan.islamicAwareness.ui.adapter.StoryAdapter
import com.ramadan.islamicAwareness.ui.viewModel.ViewModel

class StoryDashboard : AppCompatActivity() {
    private lateinit var prophetsAdapter: StoryAdapter
    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModel::class.java) }

    override fun onResume() {
        super.onResume()
        observeDate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycle_view)
        prophetsAdapter = StoryAdapter(this, false)
        val recyclerView: RecyclerView = findViewById(R.id.dashboardRecycleView)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.adapter = prophetsAdapter

    }

    private fun observeDate() {
        viewModel.fetchAllStories(true).observe(this, { prophetsAdapter.setDataList(it) })
    }

}