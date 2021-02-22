package com.ramadan.islami.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Collection
import com.ramadan.islami.data.model.Quote
import com.ramadan.islami.data.model.Story
import com.ramadan.islami.ui.activity.TopicsList
import com.ramadan.islami.ui.activity.VideosList
import com.ramadan.islami.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_item.view.*
import com.ramadan.islami.ui.activity.Quote as QuoteActivity
import com.ramadan.islami.ui.activity.Story as ActivityStory


class RecycleViewAdapter(val isWrapped: Boolean) :
    RecyclerView.Adapter<RecycleViewAdapter.CustomView>() {
    private var suggestionList = mutableListOf<Story>()
    private var storiesList = mutableListOf<Story>()
    private var categoryList = mutableListOf<Quote>()
    private var quotesList = ArrayList<String>()
    private var collectionList = mutableListOf<Collection>()

    fun suggestionDataList(data: MutableList<Story>) {
        suggestionList = data
        notifyDataSetChanged()
    }

    fun setStoriesDataList(data: MutableList<Story>) {
        storiesList = data
        notifyDataSetChanged()
    }

    fun setCategoryDataList(data: MutableList<Quote>) {
        data.removeAt(1)
        categoryList = data
        notifyDataSetChanged()
    }

    fun setQuotesDataList(data: ArrayList<String>) {
        data.shuffle()
        quotesList = data
        notifyDataSetChanged()
    }

    fun setCollectionsDataList(data: MutableList<Collection>) {
        collectionList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return CustomView(view)
    }


    override fun onBindViewHolder(holder: CustomView, position: Int) {
        when {
            suggestionList.size > 0 -> holder.suggestionView(suggestionList[position])
            storiesList.size > 0 -> holder.storyView(storiesList[position])
            categoryList.size > 0 -> holder.quotesList(categoryList[position])
            quotesList.size > 0 -> holder.quoteView(quotesList[position])
            collectionList.size > 0 -> holder.collectionView(collectionList[position])
        }
    }

    override fun getItemCount(): Int {
        return when {
            suggestionList.size > 0 -> suggestionList.size
            storiesList.size > 0 -> storiesList.size
            categoryList.size > 0 -> categoryList.size
            quotesList.size > 0 -> quotesList.size
            collectionList.size > 0 -> collectionList.size
            else -> 1
        }
    }
    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val util = Utils(itemView.context)

        fun suggestionView(story: Story) {
            Picasso.get().load(story.imgUrl).error(R.drawable.failure_img)
                .placeholder(R.drawable.load_img).into(itemView.cardImg)
            itemView.cardName.text = story.title
            itemView.setOnClickListener {
                Intent(itemView.context, ActivityStory::class.java).apply {
                    putExtra("story", story)
                    itemView.context.startActivity(this)
                }
            }
        }

        fun storyView(story: Story) {
            Picasso.get().load(story.imgUrl).error(R.drawable.failure_img)
                .placeholder(R.drawable.load_img).into(itemView.cardImg)
            itemView.cardName.text = story.title.toUpperCase()
            itemView.setOnClickListener {
                Intent(itemView.context, ActivityStory::class.java).apply {
                    putExtra("story", story)
                    itemView.context.startActivity(this)
                }
            }
        }

        fun quotesList(quote: Quote) {
            Picasso.get().load(quote.image).error(R.drawable.error_img)
                .placeholder(R.drawable.failure_img).into(itemView.cardImg)
            itemView.cardName.text = quote.title
            itemView.setOnClickListener {
                Intent(itemView.context, QuoteActivity::class.java).apply {
                    putExtra("quotes", quote)
                    itemView.context.startActivity(this)
                }
            }
        }

        fun quoteView(quotes: String) {
            Picasso.get().load(quotes).error(R.drawable.error_img)
                .placeholder(R.drawable.failure_img).into(itemView.cardImg)
            if (isWrapped) itemView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            itemView.setOnClickListener { util.showImg(quotes, itemView.context) }
        }

        fun collectionView(collection: Collection) {
            Picasso.get().load(collection.image).error(R.drawable.error_img)
                .placeholder(R.drawable.failure_img).into(itemView.cardImg)
            if (isWrapped) itemView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            itemView.cardName.text = collection.title
            itemView.setOnClickListener {
                when (collection.id) {
                    "videos" -> {
                        Intent(itemView.context, VideosList::class.java).apply {
                            putExtra("id", collection.id)
                            putExtra("title", collection.title)
                            itemView.context.startActivity(this)
                        }
                    }
                    else -> {
                        Intent(itemView.context, TopicsList::class.java).apply {
                            putExtra("collectionId", collection.id)
                            putExtra("title", collection.title)
                            itemView.context.startActivity(this)
                        }
                    }
                }
            }
        }

    }
}