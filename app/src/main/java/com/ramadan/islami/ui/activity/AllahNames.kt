package com.ramadan.islami.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.RecyclerViewAdapter
import com.ramadan.islami.ui.viewModel.LocalViewModel


class AllahNames : AppCompatActivity() {

    private val localViewModel by lazy { ViewModelProvider(this).get(LocalViewModel::class.java) }

    private lateinit var recyclerView: RecyclerView
    private lateinit var allahNamesAdapter: RecyclerViewAdapter

    override fun onStart() {
        super.onStart()
        observeDate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        allahNamesAdapter = RecyclerViewAdapter()
        recyclerView = findViewById(R.id.global_recycler_view)
        findViewById<ProgressBar>(R.id.progress).visibility = View.GONE
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = allahNamesAdapter
    }

    private fun observeDate() {
        localViewModel.getAllahNames(this)?.let { allahNames ->
            allahNamesAdapter.setAllahNamesDataList(allahNames.toMutableList())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}