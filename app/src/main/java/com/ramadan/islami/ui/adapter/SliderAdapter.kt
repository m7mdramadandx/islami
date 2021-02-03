package com.ramadan.islami.ui.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Category
import com.ramadan.islami.ui.activity.Quote
import com.ramadan.islami.ui.activity.Story
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.slider_img.view.*


class SliderAdapter(val context: Context) :
    SliderViewAdapter<SliderAdapter.CustomView>() {
    private var storyList = mutableListOf<com.ramadan.islami.data.model.Story>()
    private var categoryList = mutableListOf<Category>()

    fun setStoriesDataList(data: MutableList<com.ramadan.islami.data.model.Story>) {
        data.shuffle()
        storyList = data
        notifyDataSetChanged()
    }

    fun setCategoryDataList(data: MutableList<Category>) {
        data.removeAt(1)
        data.shuffle()
        categoryList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): CustomView {
        val inflate: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.slider_img, parent, false)
        return CustomView(inflate)
    }

    override fun getCount(): Int {
        return when {
            storyList.size > 0 -> 6
            categoryList.size > 0 -> categoryList.size
            else -> 1
        }
    }

    override fun onBindViewHolder(viewHolder: CustomView, position: Int) {
        when {
            storyList.size > 0 -> return viewHolder.storyView(storyList[position])
            categoryList.size > 0 -> return viewHolder.categoryView(categoryList[position])
        }
    }

    class CustomView(itemView: View) : ViewHolder(itemView) {

        fun storyView(story: com.ramadan.islami.data.model.Story) {
            Picasso.get().load(story.imgUrl).error(R.drawable.error_img)
                .placeholder(R.drawable.failure_img).into(itemView.sliderImg)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, Story::class.java)
                val bundle = Bundle()
                bundle.putString("storyTitle", story.displayName)
                bundle.putStringArrayList("text", story.text)
                intent.putExtras(bundle)
                itemView.context.startActivity(intent)
            }
        }

        fun categoryView(category: Category) {
            Picasso.get().load(category.imgUrl).error(R.drawable.error_img)
                .placeholder(R.drawable.failure_img).into(itemView.sliderImg)
            itemView.setOnClickListener {
                Intent(itemView.context, Quote::class.java).apply {
                    putExtra("title", category.displayName)
                    putExtra("category", category.name)
                    itemView.context.startActivity(this)
                }
            }
        }
    }
}