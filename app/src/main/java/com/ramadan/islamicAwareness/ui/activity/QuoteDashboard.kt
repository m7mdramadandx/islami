@file:Suppress("DEPRECATION")

package com.ramadan.islamicAwareness.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ramadan.islamicAwareness.R
import com.ramadan.islamicAwareness.ui.adapter.QuoteAdapter
import com.ramadan.islamicAwareness.ui.viewModel.ViewModel

class QuoteDashboard : Fragment() {
    private lateinit var quoteAdapter: QuoteAdapter
    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModel::class.java) }

    override fun onResume() {
        super.onResume()
        observeDate()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.recycle_view, container, false)
        quoteAdapter = QuoteAdapter(this)
        observeDate()
        val recyclerView: RecyclerView = view.findViewById(R.id.dashboardRecycleView)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.adapter = quoteAdapter
        return view
    }

    private fun observeDate() {
        viewModel.fetchCategory().observe(viewLifecycleOwner, { quoteAdapter.setDataList(it) })
    }

}