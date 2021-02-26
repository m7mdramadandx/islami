package com.ramadan.islami.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ramadan.islami.R
import com.ramadan.islami.data.api.ApiHelper
import com.ramadan.islami.data.api.RetrofitBuilder
import com.ramadan.islami.ui.viewModel.ApiViewModel
import com.ramadan.islami.ui.viewModel.ViewModelFactory
import com.ramadan.islami.utils.ResStatus
import com.ramadan.islami.utils.debug_tag
import kotlinx.android.synthetic.main.hadith_of_day.*

class HadithOfDay : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this,
            ViewModelFactory(ApiHelper(RetrofitBuilder("https://api.sunnah.com/").hijriCalender()))
        ).get(ApiViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hadith_of_day)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupObservers()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupObservers() {
        viewModel.hadithOfDay().observe(this, {
            when (it.status) {
                ResStatus.SUCCESS -> {
                    progress.visibility = View.GONE
                    hadithBody.visibility = View.VISIBLE
                    hadithTitle.text = it.data!!.hadith[1].chapterTitle
                    hadithBody.text = it.data.hadith[1].body.removeSurrounding("<p>", "</p>")
                }
                ResStatus.ERROR -> {
                    progress.visibility = View.GONE
                    Log.e(debug_tag, it.message.toString())
                }
                ResStatus.LOADING -> {
                    Log.e(debug_tag, "LOADING")
                }
            }
        })
    }
}