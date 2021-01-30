package com.ramadan.islami.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.SliderAdapter
import com.ramadan.islami.ui.viewModel.ViewModel
import com.ramadan.islami.utils.LocaleHelper
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.coroutines.*


@Suppress("DEPRECATION")
class Quote : AppCompatActivity() {
    private lateinit var sliderView: SliderView
    private lateinit var sliderView1: SliderView
    private lateinit var adapter: SliderAdapter
    private lateinit var adapter1: SliderAdapter
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
        adapter = SliderAdapter(this)
        sliderView = findViewById(R.id.versesSlider)
        sliderView.setSliderAdapter(adapter)
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINSCALINGTRANSFORMATION)
        sliderView.setOnIndicatorClickListener { position ->
            sliderView.currentPagePosition = position
        }

        adapter1 = SliderAdapter(this)
        sliderView1 = findViewById(R.id.hadithsSlider)
        sliderView1.setSliderAdapter(adapter1)
        sliderView1.setIndicatorAnimation(IndicatorAnimations.THIN_WORM)
        sliderView1.setSliderTransformAnimation(SliderAnimations.CUBEINSCALINGTRANSFORMATION)
        sliderView1.setOnIndicatorClickListener { position ->
            sliderView1.currentPagePosition = position
        }

        val version = Build.VERSION.SDK_INT
        if (version > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyPermission()) {
                requestForSpecificPermission()
            }
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
                Toast.makeText(this, "download image by long press", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "you will not able to download images", Toast.LENGTH_LONG)
                    .show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun observeDate() {
        GlobalScope.launch {
            viewModel.fetchQuote(isEnglish, category!!).also {
                delay(500)
                withContext(Dispatchers.Main) {
                    adapter.setDataList(it.verses)
                    adapter1.setDataList(it.hadiths)
                }
            }
        }
    }


}