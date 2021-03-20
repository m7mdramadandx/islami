package com.ramadan.islami.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ramadan.islami.R
import com.ramadan.islami.ui.activity.Quote
import com.ramadan.islami.ui.activity.StoryDetails
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_slider_img.view.*
import com.ramadan.islami.data.model.Quote as QuoteModel
import com.ramadan.islami.data.model.Story as StoryModel


class SliderAdapter : SliderViewAdapter<SliderAdapter.CustomView>() {
    private var storyList = mutableListOf<StoryModel>()
    private var categoryList = mutableListOf<QuoteModel>()

    fun setStoriesDataList(data: MutableList<StoryModel>) {
        data.shuffle()
        storyList = data
        notifyDataSetChanged()
    }

    fun setCategoryDataList(data: MutableList<QuoteModel>) {
        data.removeAt(1)
        data.shuffle()
        categoryList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): CustomView {
        val inflate: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_slider_img, parent, false)
        return CustomView(inflate)
    }


    override fun onBindViewHolder(holder: CustomView, position: Int) {
        when {
            storyList.size > 0 -> return holder.storyView(storyList[position])
            categoryList.size > 0 -> return holder.quotesView(categoryList[position])
        }
    }

    override fun getCount(): Int {
        return when {
            storyList.size > 0 -> 6
            categoryList.size > 0 -> categoryList.size
            else -> 1
        }
    }

    class CustomView(itemView: View) : ViewHolder(itemView) {

        fun storyView(story: StoryModel) {
            Picasso.get().load(story.image).error(R.drawable.ic_error_img)
                .placeholder(R.drawable.failure_img).into(itemView.sliderImg)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, StoryDetails::class.java)
                intent.putExtra("story", story)
                itemView.context.startActivity(intent)
            }
        }

        fun quotesView(quote: QuoteModel) {
            Picasso.get().load(quote.image).error(R.drawable.ic_error_img)
                .placeholder(R.drawable.failure_img).into(itemView.sliderImg)
            itemView.setOnClickListener {
                Intent(itemView.context, Quote::class.java).apply {
                    putExtra("quotes", quote)
                    itemView.context.startActivity(this)
                }
            }
        }
    }
}