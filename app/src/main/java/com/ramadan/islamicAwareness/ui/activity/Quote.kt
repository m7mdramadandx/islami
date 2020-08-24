package com.ramadan.islamicAwareness.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ramadan.islamicAwareness.R
import com.ramadan.islamicAwareness.ui.adapter.QuoteImgAdapter
import com.ramadan.islamicAwareness.ui.viewModel.ViewModel
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.squareup.picasso.Picasso


@Suppress("DEPRECATION")
class Quote : AppCompatActivity() {
    private lateinit var sliderView: SliderView
    private lateinit var adapter: QuoteImgAdapter
    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModel::class.java) }
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quote_layout)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sliderView = findViewById(R.id.imageSlider)
        adapter = QuoteImgAdapter(this)
        sliderView.setSliderAdapter(adapter)
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINSCALINGTRANSFORMATION)
        sliderView.setOnIndicatorClickListener { position ->
            sliderView.currentPagePosition = position
        }
        observeDate()
    }

    override fun onResume() {
        super.onResume()
        category = intent.getStringExtra("category")!!
        println(category)
        title = if (category == "Death") {
            "Judgement Day quotes"
        } else {
            "$category quotes"
        }
    }

    fun x(imgUrl: String, context: Context) {
        val alert: AlertDialog.Builder = AlertDialog.Builder(context)
        val factory = LayoutInflater.from(context)
        val view: View = factory.inflate(R.layout.alert_dialog, null)
        val imageView: ImageView = view.findViewById(R.id.quoteImg)
        alert.setView(view)
        Picasso.get()
            .load(imgUrl).error(R.drawable.error_img).placeholder(
                R.drawable.load_img
            ).into(imageView)
        alert.show()
    }

    private fun observeDate() {
        viewModel.fetchQuote(category!!).observe(this, Observer {
            adapter.setDataList(it)
        })
    }
}