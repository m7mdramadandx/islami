package com.ramadan.islami.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.RecyclerViewAdapter
import com.ramadan.islami.ui.viewModel.DataViewModel
import com.ramadan.islami.utils.LocaleHelper
import com.ramadan.islami.data.model.Quote as QuoteModel


@Suppress("DEPRECATION")
class Quote : AppCompatActivity() {
    private var versesRecyclerView: RecyclerView? = null
    private var hadithsRecyclerView: RecyclerView? = null
    private lateinit var versesAdapter: RecyclerViewAdapter
    private lateinit var hadithsAdapter: RecyclerViewAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(DataViewModel::class.java) }
    private var isEnglish: Boolean = true
    private val localeHelper = LocaleHelper()
    private lateinit var quote: QuoteModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quotes)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        isEnglish = localeHelper.getDefaultLanguage(this) == "en"
        quote = intent?.getSerializableExtra("quotes") as QuoteModel
        supportActionBar!!.title = quote.title
//        viewModel.listener = this
        versesAdapter = RecyclerViewAdapter()
        hadithsAdapter = RecyclerViewAdapter()
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
        observeDate()
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun observeDate() {
        versesAdapter.setQuotesDataList(quote.verses)
        hadithsAdapter.setQuotesDataList(quote.hadiths)
    }

//    override fun onStarted() {}
//
//    override fun onSuccess() {
//        Toast.makeText(this, getString(R.string.could_download), Toast.LENGTH_LONG).show()
//    }
//
//    override fun onFailure(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
//    }
}