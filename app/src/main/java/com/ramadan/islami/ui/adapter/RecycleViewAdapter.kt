package com.ramadan.islami.ui.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Category
import com.ramadan.islami.data.model.Information
import com.ramadan.islami.data.model.Story
import com.ramadan.islami.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_item.view.*
import com.ramadan.islami.ui.activity.Quote as QuoteActivity
import com.ramadan.islami.ui.activity.Story as ActivityStory


class RecycleViewAdapter(val isDashboard: Boolean, val isQuotes: Boolean) :
    RecyclerView.Adapter<RecycleViewAdapter.CustomView>() {
    private var storiesList = mutableListOf<Story>()
    private var categoryList = mutableListOf<Category>()
    private var infoList = mutableListOf<Information>()
    private var quotesList = ArrayList<String>()

    fun setStoriesDataList(data: MutableList<Story>) {
        storiesList = data
        notifyDataSetChanged()
    }

    fun setInfoDataList(data: MutableList<Information>) {
        infoList = data
        notifyDataSetChanged()
    }

    fun setCategoryDataList(data: MutableList<Category>) {
        data.removeAt(1)
        categoryList = data
        notifyDataSetChanged()
    }

    fun setQuotesDataList(data: ArrayList<String>) {
        data.shuffle()
        quotesList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return CustomView(view)
    }

    override fun getItemCount(): Int {
        return when {
            storiesList.size > 0 -> storiesList.size
            categoryList.size > 0 -> categoryList.size
            quotesList.size > 0 -> quotesList.size
            else -> 1
        }
    }

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        when {
            storiesList.size > 0 -> holder.storyView(storiesList[position])
            categoryList.size > 0 -> holder.categoryView(categoryList[position])
            quotesList.size > 0 -> holder.quoteView(quotesList[position])
        }
    }

    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val util = Utils(itemView.context)
        fun storyView(story: Story) {
            Picasso.get().load(story.imgUrl).error(R.drawable.failure_img)
                .placeholder(R.drawable.load_img).into(itemView.cardImg)
            if (isDashboard) itemView.cardImg.maxHeight =
                itemView.resources.getDimension(R.dimen.max_card_height).toInt()
            itemView.cardName.text = story.displayName
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ActivityStory::class.java)
                val bundle = Bundle()
                bundle.putString("storyTitle", story.displayName)
                bundle.putStringArrayList("text", story.text)
                intent.putExtras(bundle)
                itemView.context.startActivity(intent)
            }
        }

        fun categoryView(category: Category) {
            Picasso.get().load(category.imgUrl).error(R.drawable.error_img)
                .placeholder(R.drawable.failure_img).into(itemView.cardImg)
            if (isDashboard) itemView.cardImg.maxHeight =
                itemView.resources.getDimension(R.dimen.max_card_height_1).toInt()
            itemView.cardName.text = category.displayName
            itemView.setOnClickListener {
                Intent(itemView.context, QuoteActivity::class.java).apply {
                    putExtra("title", category.displayName)
                    putExtra("category", category.name)
                    itemView.context.startActivity(this)
                }
            }
        }

        fun quoteView(quotes: String) {
            Picasso.get().load(quotes).error(R.drawable.error_img)
                .placeholder(R.drawable.failure_img).into(itemView.cardImg)
            if (isQuotes) {
                itemView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            itemView.setOnClickListener { util.showImg(quotes, itemView.context) }
        }

    }
}