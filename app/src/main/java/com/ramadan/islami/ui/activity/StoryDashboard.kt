@file:Suppress("DEPRECATION")

package com.ramadan.islami.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.StoryAdapter
import com.ramadan.islami.ui.viewModel.ViewModel
import com.ramadan.islami.utils.LocaleHelper

class StoryDashboard : AppCompatActivity() {
    private lateinit var prophetsAdapter: StoryAdapter
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
        isEnglish = localeHelper.getDefaultLanguage(this) == "en"
        prophetsAdapter = StoryAdapter(this, false)
        val recyclerView: RecyclerView = findViewById(R.id.dashboardRecycleView)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.adapter = prophetsAdapter

    }

    private fun observeDate() {
        viewModel.fetchAllStories(isEnglish).observe(this, { prophetsAdapter.setDataList(it) })
    }

}