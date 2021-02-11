package com.ramadan.islami.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.InfoAdapter
import com.ramadan.islami.ui.viewModel.Listener
import com.ramadan.islami.ui.viewModel.ViewModel
import com.ramadan.islami.utils.LocaleHelper
import kotlinx.android.synthetic.main.recycle_view.*


class InformationList : AppCompatActivity(), Listener {
    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModel::class.java) }
    private var isEnglish: Boolean = true
    private val localeHelper = LocaleHelper()
    private lateinit var infoAdapter: InfoAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        val intent_o = getIntent()
    }

    override fun onStart() {
        super.onStart()
        observeData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycle_view)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = intent.getStringExtra("title")
        isEnglish = localeHelper.getDefaultLanguage(this) == "en"
        recyclerView = findViewById(R.id.recycler_view)
        infoAdapter = InfoAdapter()
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = infoAdapter
        viewModel.listener = this
    }

    private fun observeData() {
        viewModel.fetchInformation(isEnglish).observe(this, { infoAdapter.setInfoDataList(it) })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStarted() {}

    override fun onSuccess() {
        progress.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        progress.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}