package com.ramadan.islami.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
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

class Topics : Fragment(), FirebaseListener {
    private val viewModel by lazy { ViewModelProvider(this).get(FirebaseViewModel::class.java) }
    private lateinit var collectionAdapter: RecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progress: ProgressBar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        collectionAdapter = RecyclerViewAdapter()
        observeData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.recycler_view, container, false)
        recyclerView = root.findViewById(R.id.global_recycler_view)
        progress = root.findViewById(R.id.progress)
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = collectionAdapter
        viewModel.firebaseListener = this
        return root
    }

    private fun observeData() {
        viewModel.fetchCollection(language)
            .observe(this, { collectionAdapter.setCollectionsDataList(it) })
    }

    override fun onStarted() {}

    override fun onSuccess() {
        progress.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        progress.visibility = View.GONE
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}