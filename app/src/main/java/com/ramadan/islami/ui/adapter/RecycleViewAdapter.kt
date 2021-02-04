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
import com.ramadan.islami.ui.activity.InformationList
import com.ramadan.islami.ui.activity.VideosList
import com.ramadan.islami.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_item.view.*
import com.ramadan.islami.ui.activity.Quote as QuoteActivity
import com.ramadan.islami.ui.activity.Story as ActivityStory


class RecycleViewAdapter(val isWrapped: Boolean) :
    RecyclerView.Adapter<RecycleViewAdapter.CustomView>() {
    private var storiesList = mutableListOf<Story>()
    private var categoryList = mutableListOf<Quote>()
    private var quotesList = ArrayList<String>()
    private var collectionList = mutableListOf<Collection>()

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

    override fun getItemCount(): Int {
        return when {
            storiesList.size > 0 -> storiesList.size
            categoryList.size > 0 -> categoryList.size
            quotesList.size > 0 -> quotesList.size
            collectionList.size > 0 -> collectionList.size
            else -> 1
        }
    }

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        when {
            storiesList.size > 0 -> holder.storyView(storiesList[position])
            categoryList.size > 0 -> holder.quotesList(categoryList[position])
            quotesList.size > 0 -> holder.quoteView(quotesList[position])
            collectionList.size > 0 -> holder.collectionView(collectionList[position])
        }
    }

    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val util = Utils(itemView.context)

        fun storyView(story: Story) {
            Picasso.get().load(story.imgUrl).error(R.drawable.failure_img)
                .placeholder(R.drawable.load_img).into(itemView.cardImg)
            itemView.cardName.text = story.title
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ActivityStory::class.java)
                intent.putExtra("story", story)
                itemView.context.startActivity(intent)
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
            if (isWrapped) {
                itemView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            itemView.setOnClickListener { util.showImg(quotes, itemView.context) }
        }

        fun collectionView(collection: Collection) {
            Picasso.get().load(collection.image).error(R.drawable.error_img)
                .placeholder(R.drawable.failure_img).into(itemView.cardImg)
            if (isWrapped) {
                itemView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            }
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
                    "information" -> {
                        Intent(itemView.context, InformationList::class.java).apply {
                            putExtra("id", collection.id)
                            putExtra("title", collection.title)
                            itemView.context.startActivity(this)
                        }
                    }
                }
            }
        }

    }
}