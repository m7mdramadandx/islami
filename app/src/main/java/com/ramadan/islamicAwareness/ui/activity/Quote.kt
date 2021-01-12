package com.ramadan.islamicAwareness.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.ramadan.islamicAwareness.R
import com.ramadan.islamicAwareness.ui.adapter.QuoteImgAdapter
import com.ramadan.islamicAwareness.ui.viewModel.ViewModel
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView


@Suppress("DEPRECATION")
class Quote : AppCompatActivity() {
    private lateinit var sliderView: SliderView
    private lateinit var sliderView1: SliderView
    private lateinit var adapter: QuoteImgAdapter
    private lateinit var adapter1: QuoteImgAdapter
    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModel::class.java) }
    private var category: String? = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quote_layout)
        category = intent?.getStringExtra("category")
        supportActionBar!!.title = if (category == "Death") {
            "Judgement Day quotes"
        } else {
            "$category quotes"
        }
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        adapter = QuoteImgAdapter(this)
        sliderView = findViewById(R.id.imageSlider)
        sliderView.setSliderAdapter(adapter)
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINSCALINGTRANSFORMATION)
        sliderView.setOnIndicatorClickListener { position ->
            sliderView.currentPagePosition = position
        }

        adapter1 = QuoteImgAdapter(this)
        sliderView1 = findViewById(R.id.imageSlider1)
        sliderView1.setSliderAdapter(adapter1)
        sliderView1.setIndicatorAnimation(IndicatorAnimations.THIN_WORM)
        sliderView1.setSliderTransformAnimation(SliderAnimations.CUBEINSCALINGTRANSFORMATION)
        sliderView1.setOnIndicatorClickListener { position ->
            sliderView1.currentPagePosition = position
        }
        observeDate()
    }

    private fun observeDate() {
        viewModel.fetchQuote(category!!).observe(this, {
            adapter.setDataList(it)
            adapter1.setDataList(it)
        })
    }
}