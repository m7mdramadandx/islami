package com.ramadan.islami.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.api.ApiHelper
import com.ramadan.islami.data.api.RetrofitBuilder
import com.ramadan.islami.data.model.AllahNames
import com.ramadan.islami.ui.adapter.RecyclerViewAdapter
import com.ramadan.islami.ui.viewModel.ViewModelFactory
import com.ramadan.islami.ui.viewModel.WebServiceViewModel
import com.ramadan.islami.utils.ResponseStatus
import com.ramadan.islami.utils.debug_tag
import com.ramadan.islami.utils.showMessage
import kotlinx.android.synthetic.main.recycler_view.*


class AllahNames : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this,
            ViewModelFactory(ApiHelper(RetrofitBuilder("http://api.aladhan.com/").apiService()))
        ).get(WebServiceViewModel::class.java)
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onStart() {
        super.onStart()
        observeDate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerViewAdapter = RecyclerViewAdapter()
        recyclerView = findViewById(R.id.global_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerViewAdapter
    }

    private fun observeDate() {
        viewModel.allahNames().observe(this, {
            when (it.status) {
                ResponseStatus.LOADING -> progress.visibility = View.VISIBLE
                ResponseStatus.SUCCESS -> {
                    progress.visibility = View.GONE
                    recyclerViewAdapter.setAllahNamesDataList(it.data!!.data as MutableList<AllahNames.Data>)
                }
                ResponseStatus.ERROR -> {
                    progress.visibility = View.GONE
                    showMessage(this, it.message.toString())
                    Log.e(debug_tag, it.message.toString())
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}