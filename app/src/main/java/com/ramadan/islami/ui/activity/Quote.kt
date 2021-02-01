package com.ramadan.islami.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.RecycleViewAdapter
import com.ramadan.islami.ui.viewModel.ViewModel
import com.ramadan.islami.utils.LocaleHelper
import kotlinx.android.synthetic.main.quote_layout.*
import kotlinx.coroutines.*


@Suppress("DEPRECATION")
class Quote : AppCompatActivity() {
    private var versesRecyclerView: RecyclerView? = null
    private var hadithsRecyclerView: RecyclerView? = null
    private lateinit var versesAdapter: RecycleViewAdapter
    private lateinit var hadithsAdapter: RecycleViewAdapter
    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModel::class.java) }
    private var category: String? = ""
    private var isEnglish: Boolean = true
    private val localeHelper = LocaleHelper()

    override fun onStart() {
        super.onStart()
        observeDate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quote_layout)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        isEnglish = localeHelper.getDefaultLanguage(this) == "en"
        category = intent?.getStringExtra("category")
        supportActionBar!!.title = intent?.getStringExtra("title")
        versesAdapter = RecycleViewAdapter(isDashboard = false, isQuotes = true)
        hadithsAdapter = RecycleViewAdapter(isDashboard = false, isQuotes = true)
        versesRecyclerView = findViewById(R.id.versesRecyclerView)
        hadithsRecyclerView = findViewById(R.id.hadithsRecyclerView)
        versesRecyclerView?.layoutManager =
            StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        hadithsRecyclerView?.layoutManager =
            StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        versesRecyclerView?.adapter = versesAdapter
        hadithsRecyclerView?.adapter = hadithsAdapter
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyPermission()) requestForSpecificPermission()
        }
    }

    private fun checkIfAlreadyPermission(): Boolean {
        val result =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE),
            101)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            101 -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.could_download), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, getString(R.string.couldnot_download), Toast.LENGTH_LONG)
                    .show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun observeDate() {
        GlobalScope.launch {
            viewModel.fetchQuote(isEnglish, category!!).also {
                delay(300)
                withContext(Dispatchers.Main) {
                    versesAdapter.setQuotesDataList(it.verses)
                    hadithsAdapter.setQuotesDataList(it.hadiths)
                    if (it.verses.isNotEmpty())
                        progress.visibility = View.GONE
                }
            }
        }
    }


}