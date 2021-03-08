package com.ramadan.islami.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ramadan.islami.R
import com.ramadan.islami.data.api.ApiHelper
import com.ramadan.islami.data.api.RetrofitBuilder
import com.ramadan.islami.ui.viewModel.ViewModelFactory
import com.ramadan.islami.ui.viewModel.WebServiceViewModel
import com.ramadan.islami.utils.ResponseStatus
import com.ramadan.islami.utils.debug_tag
import kotlinx.android.synthetic.main.activity_hadith_of_day.*


class HadithOfDay : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this,
            ViewModelFactory(ApiHelper(RetrofitBuilder("https://api.sunnah.com/").apiService()))
        ).get(WebServiceViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hadith_of_day)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
        hadithBody.customSelectionActionModeCallback
        setupObservers()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupObservers() {
        viewModel.hadithOfDay().observe(this, {
            when (it.status) {
                ResponseStatus.SUCCESS -> {
                    progress.visibility = View.GONE
                    hadithBody.visibility = View.VISIBLE
                    hadithTitle.text = it.data!!.hadith[1].chapterTitle
                    hadithBody.text = it.data.hadith[1].body.removeSurrounding("<p>", "</p>")
                }
                ResponseStatus.ERROR -> {
                    progress.visibility = View.GONE
                    Log.e(debug_tag, it.message.toString())
                }
                ResponseStatus.LOADING -> {
                    Log.e(debug_tag, "LOADING")
                }
            }
        })
    }
}