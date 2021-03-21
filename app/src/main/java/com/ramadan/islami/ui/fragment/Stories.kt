package com.ramadan.islami.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ramadan.islami.R
import com.ramadan.islami.data.listener.FirebaseListener
import com.ramadan.islami.ui.activity.MainActivity.Companion.language
import com.ramadan.islami.ui.adapter.RecyclerViewAdapter
import com.ramadan.islami.ui.viewModel.FirebaseViewModel
import com.ramadan.islami.utils.showMessage

class Stories : Fragment(), FirebaseListener {
    private val viewModel by lazy { ViewModelProvider(this).get(FirebaseViewModel::class.java) }
    private lateinit var storyAdapter: RecyclerViewAdapter
    private lateinit var progress: ProgressBar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        observeDate()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.recycler_view, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.global_recycler_view)
        progress = root.findViewById(R.id.progress)
        viewModel.firebaseListener = this
        storyAdapter = RecyclerViewAdapter()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = storyAdapter
        return root
    }

    private fun observeDate() {
        viewModel.fetchStories(language).observe(this, { storyAdapter.setStoriesDataList(it) })
    }

    override fun onStarted() {
    }

    override fun onSuccess() {
        progress.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        progress.visibility = View.GONE
        showMessage(requireContext(), message)
    }
}