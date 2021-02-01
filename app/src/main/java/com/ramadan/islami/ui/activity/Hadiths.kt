package com.ramadan.islami.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.RecycleViewAdapter
import com.ramadan.islami.ui.viewModel.Listener
import com.ramadan.islami.ui.viewModel.ViewModel
import com.ramadan.islami.utils.LocaleHelper
import kotlinx.android.synthetic.main.recycle_view.*
import kotlinx.coroutines.*

class Hadiths : AppCompatActivity(), Listener {
    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModel::class.java) }
    private lateinit var recycleViewAdapter: RecycleViewAdapter
    private var isEnglish: Boolean = true
    private val localeHelper = LocaleHelper()
    private var recyclerView: RecyclerView? = null

    override fun onStart() {
        super.onStart()
        observeDate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycle_view)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        isEnglish = localeHelper.getDefaultLanguage(this) == "en"
        viewModel.listener = this
        recycleViewAdapter = RecycleViewAdapter(this, false)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = recycleViewAdapter

    }

    private fun observeDate() {
        GlobalScope.launch {
            viewModel.fetchHadiths(isEnglish).also {
                delay(500)
                withContext(Dispatchers.Main) {
                    recycleViewAdapter.setQuotesDataList(it.hadiths)
                    progress.visibility = View.GONE
                }
            }
        }
    }

    override fun onStarted() {
    }

    override fun onSuccess() {
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}