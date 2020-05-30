@file:Suppress("DEPRECATION")

package com.ramadan.theReminder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ramadan.theReminder.ViewModel.ViewModel
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController.ClickListener
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.smarteist.imageslider.HadithImgAdapter
import kotlinx.android.synthetic.main.hadith_layout.*


class Hadith : AppCompatActivity() {
    private lateinit var sliderView: SliderView
    private lateinit var adapter: HadithImgAdapter
    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModel::class.java) }
    private lateinit var category: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hadith_layout)
        val bundle = intent?.extras
        category = bundle?.getString("category").toString()
        supportActionBar?.hide()
        sliderView = findViewById(R.id.imageSlider)
        adapter = HadithImgAdapter(this)
        sliderView.setSliderAdapter(adapter)
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINSCALINGTRANSFORMATION)
        sliderView.setOnIndicatorClickListener(ClickListener { position ->
            sliderView.currentPagePosition = position
        })
        if (category == "Death") {
            categoryName.text = "Judgement Day hadiths"
        } else {
            categoryName.text = "$category hadiths"
        }
        observeDate()
    }


    private fun observeDate() {
        viewModel.fetchHadith(category).observe(this, Observer {
            adapter.setDataList(it)
        })
//        viewModel.insert()
//        viewModel.insert1()
//        viewModel.insert2()
//        viewModel.insert3()
//        viewModel.insert4()
//        viewModel.insert5()
//        viewModel.insert6()
//        viewModel.insert7()
//        viewModel.insert8()
//        viewModel.insert9()
//        viewModel.insert10()
    }
}