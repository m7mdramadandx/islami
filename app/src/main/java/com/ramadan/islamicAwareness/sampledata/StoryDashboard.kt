@file:Suppress("DEPRECATION")

package com.ramadan.islamicAwareness.sampledata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ramadan.islamicAwareness.Adapter.StoryAdapter
import com.ramadan.islamicAwareness.R
import com.ramadan.islamicAwareness.ViewModel.ViewModel

class StoryDashboard : Fragment() {
    private lateinit var prophetsAdapter: StoryAdapter
    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeDate()
    }

    private fun observeDate() {
        viewModel.fetchStory().observe(this, Observer {
            prophetsAdapter.setDataList(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.recycle_view, container, false)
        val progress: ProgressBar = view.findViewById(R.id.progress_circular)
        prophetsAdapter = StoryAdapter(this)
        val recyclerView: RecyclerView = view.findViewById(R.id.dashboardRecycleView)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.adapter = prophetsAdapter
        progress.visibility = View.GONE
        return view
    }
}