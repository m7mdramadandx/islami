@file:Suppress("DEPRECATION")

package com.ramadan.islami.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.QuoteAdapter
import com.ramadan.islami.ui.viewModel.ViewModel
import com.ramadan.islami.utils.LocaleHelper

class QuoteDashboard : AppCompatActivity() {
    private lateinit var quoteAdapter: QuoteAdapter
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
        quoteAdapter = QuoteAdapter(this, false)
        val recyclerView: RecyclerView = findViewById(R.id.dashboardRecycleView)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.adapter = quoteAdapter

    }


    private fun observeDate() {
        viewModel.fetchCategory(isEnglish).observe(this, { quoteAdapter.setDataList(it) })
    }

}