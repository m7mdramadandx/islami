package com.ramadan.islami.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.QuoteImgAdapter
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
    private lateinit var adapter: QuoteImgAdapter
    private lateinit var adapter1: QuoteImgAdapter
    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModel::class.java) }
    private var category: String? = ""
    private var isEnglish: Boolean = true
    private val localeHelper = LocaleHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quote_layout)
        isEnglish = localeHelper.getDefaultLanguage(this) == "en"
        category = intent?.getStringExtra("category")
        supportActionBar!!.title = intent?.getStringExtra("title")
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        adapter = QuoteImgAdapter(this)
        sliderView = findViewById(R.id.versesSlider)
        sliderView.setSliderAdapter(adapter)
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINSCALINGTRANSFORMATION)
        sliderView.setOnIndicatorClickListener { position ->
            sliderView.currentPagePosition = position
        }

        adapter1 = QuoteImgAdapter(this)
        sliderView1 = findViewById(R.id.hadithsSlider)
        sliderView1.setSliderAdapter(adapter1)
        sliderView1.setIndicatorAnimation(IndicatorAnimations.THIN_WORM)
        sliderView1.setSliderTransformAnimation(SliderAnimations.CUBEINSCALINGTRANSFORMATION)
        sliderView1.setOnIndicatorClickListener { position ->
            sliderView1.currentPagePosition = position
        }
    }

    override fun onStart() {
        super.onStart()
        observeDate()
    }

    private fun observeDate() {
        GlobalScope.launch {

            viewModel.fetchQuote(isEnglish, category!!).also {
                delay(3000)
                withContext(Dispatchers.Main) {
                    adapter.setDataList(it.verses)
                    adapter1.setDataList(it.hadiths)
                }
            }
        }
    }


}