package com.ramadan.islami.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.RecyclerViewAdapter
import com.ramadan.islami.utils.Utils

class Daily : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progress: ProgressBar
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter


    override fun onAttach(context: Context) {
        super.onAttach(context)
        recyclerViewAdapter = RecyclerViewAdapter()
        observeData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.recycler_view, container, false)
        progress = root.findViewById(R.id.progress)
        progress.visibility = View.GONE
        recyclerView = root.findViewById(R.id.global_recycler_view)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = recyclerViewAdapter
        return root
    }

    private fun observeData() {
        recyclerViewAdapter.setDailyDataList(Utils(requireContext()).dailyMutableList)
    }


}