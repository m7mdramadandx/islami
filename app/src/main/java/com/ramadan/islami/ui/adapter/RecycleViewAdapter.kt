package com.ramadan.islami.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.model.AllahNames
import com.ramadan.islami.data.model.Collection
import com.ramadan.islami.data.model.Quote
import com.ramadan.islami.data.model.Story
import com.ramadan.islami.ui.activity.*
import com.ramadan.islami.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.allah_names.view.*
import kotlinx.android.synthetic.main.card_item.view.*
import java.util.*
import kotlin.collections.ArrayList
import com.ramadan.islami.ui.activity.AllahNames as AllahNamesActivity
import com.ramadan.islami.ui.activity.Quote as QuoteActivity
import com.ramadan.islami.ui.activity.Story as ActivityStory


class RecycleViewAdapter : RecyclerView.Adapter<RecycleViewAdapter.CustomView>() {
    private var suggestionList = mutableListOf<Collection>()
    private var storiesList = mutableListOf<Story>()
    private var dailyList = mutableListOf<Collection>()
    private var categoryList = mutableListOf<Quote>()
    private var quotesList = ArrayList<String>()
    private var collectionList = mutableListOf<Collection>()
    private var familyTreeList = mutableListOf<Collection>()
    private var allahNames = mutableListOf<AllahNames.Data>()

    fun setSuggestionDataList(data: MutableList<Collection>) {
        suggestionList = data
        notifyDataSetChanged()
    }

    fun setStoriesDataList(data: MutableList<Story>) {
        storiesList = data
        notifyDataSetChanged()
    }

    fun setDailyDataList(data: MutableList<Collection>) {
        dailyList = data
        notifyDataSetChanged()
    }

    fun setCategoryDataList(data: MutableList<Quote>) {
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

    fun setFamilyTreeDataList(data: MutableList<Collection>) {
        familyTreeList = data
        notifyDataSetChanged()
    }

    fun setAllahNamesDataList(data: MutableList<AllahNames.Data>) {
        allahNames = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView {
        val view: View = when {
            allahNames.isNotEmpty() -> {
                LayoutInflater.from(parent.context).inflate(R.layout.allah_names, parent, false)
            }
            familyTreeList.isNotEmpty() -> LayoutInflater.from(parent.context)
                .inflate(R.layout.family_tree_card, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        }
        return CustomView(view)
    }


    override fun onBindViewHolder(holder: CustomView, position: Int) {
        when {
            suggestionList.isNotEmpty() -> holder.suggestionView(suggestionList[position])
            dailyList.isNotEmpty() -> holder.dailyView(dailyList[position])
            storiesList.isNotEmpty() -> holder.storyView(storiesList[position])
            categoryList.isNotEmpty() -> holder.quotesList(categoryList[position])
            quotesList.isNotEmpty() -> holder.quoteView(quotesList[position])
            collectionList.isNotEmpty() -> holder.collectionView(collectionList[position])
            familyTreeList.isNotEmpty() -> holder.familyTreeView(familyTreeList[position])
            allahNames.isNotEmpty() -> holder.allahNamesView(allahNames[position])
        }
    }

    override fun getItemCount(): Int {
        return when {
            suggestionList.isNotEmpty() -> suggestionList.size
            dailyList.isNotEmpty() -> dailyList.size
            storiesList.isNotEmpty() -> storiesList.size
            categoryList.isNotEmpty() -> categoryList.size
            quotesList.isNotEmpty() -> quotesList.size
            collectionList.isNotEmpty() -> collectionList.size
            familyTreeList.isNotEmpty() -> familyTreeList.size
            allahNames.isNotEmpty() -> allahNames.size
            else -> 1
        }
    }

    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ctx: Context = itemView.context
        private val util = Utils(ctx)

        fun suggestionView(collection: Collection) {
            Picasso.get().load(collection.image).error(R.drawable.failure_img)
                .placeholder(R.drawable.load_img).into(itemView.cardImg)
            itemView.cardName.text = collection.title.toUpperCase(Locale.ENGLISH)
            itemView.setOnClickListener {
                when (collection.id) {
                    "hadithOfDay" -> ctx.startActivity(Intent(ctx, HadithOfDay::class.java))
                    "hadiths" -> ctx.startActivity(Intent(ctx, Hadiths::class.java))
                    "muhammadStory" -> Intent(ctx, ActivityStory::class.java).apply {
                        putExtra("storyID", "muhammad")
                        ctx.startActivity(this)
                    }
                }
            }
        }

        fun storyView(story: Story) {
            Picasso.get().load(story.image).error(R.drawable.failure_img)
                .placeholder(R.drawable.load_img).into(itemView.cardImg)
            itemView.cardName.text = story.title.toUpperCase(Locale.ENGLISH)
            itemView.setOnClickListener {
                Intent(ctx, ActivityStory::class.java).apply {
                    putExtra("story", story)
                    ctx.startActivity(this)
                }
            }
        }

        fun dailyView(collection: Collection) {
            itemView.cardName.text = collection.title.toUpperCase(Locale.ENGLISH)
            itemView.cardImg.maxHeight = 140
            itemView.setOnClickListener {
                when (collection.id) {
                    "prayerTimes" -> ctx.startActivity(Intent(ctx, PrayerTimes::class.java))
                    "qibla" -> ctx.startActivity(Intent(ctx, Qibla::class.java))
                    "dateConversion" -> ctx.startActivity(Intent(ctx, DateConversion::class.java))
                    "allahNames" -> ctx.startActivity(Intent(ctx, AllahNamesActivity::class.java))
                }
            }
        }

        fun quotesList(quote: Quote) {
            Picasso.get().load(quote.image).error(R.drawable.error_img)
                .placeholder(R.drawable.failure_img).into(itemView.cardImg)
            itemView.cardName.text = quote.title
            itemView.setOnClickListener {
                Intent(ctx, QuoteActivity::class.java).apply {
                    putExtra("quotes", quote)
                    ctx.startActivity(this)
                }
            }
        }

        fun quoteView(quotes: String) {
            Picasso.get().load(quotes).error(R.drawable.error_img)
                .placeholder(R.drawable.failure_img).into(itemView.cardImg)
            itemView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            itemView.setOnClickListener { util.showImg(quotes, ctx) }
        }

        fun collectionView(collection: Collection) {
            Picasso.get().load(collection.image).error(R.drawable.error_img)
                .placeholder(R.drawable.failure_img).into(itemView.cardImg)
            itemView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            itemView.cardName.text = collection.title
            itemView.setOnClickListener {
                when (collection.id) {
                    "videos" -> {
                        Intent(ctx, VideosList::class.java).apply {
                            putExtra("id", collection.id)
                            putExtra("title", collection.title)
                            ctx.startActivity(this)
                        }
                    }
                    else -> {
                        Intent(ctx, TopicsList::class.java).apply {
                            putExtra("collectionId", collection.id)
                            putExtra("title", collection.title)
                            ctx.startActivity(this)
                        }
                    }
                }
            }
        }

        fun familyTreeView(collection: Collection) {
            itemView.cardName.text = collection.title.toUpperCase(Locale.ENGLISH)
            itemView.setOnClickListener {
                when (collection.id) {
                    "muhammadTree" -> ctx.startActivity(Intent(ctx, PrayerTimes::class.java))
                    "prophetsTree" -> ctx.startActivity(Intent(ctx, Qibla::class.java))
                    "bigTree" -> ctx.startActivity(Intent(ctx, Qibla::class.java))
                }
            }
        }

        fun allahNamesView(allahNames: AllahNames.Data) {
            itemView.number.text = allahNames.number.toString()
            itemView.name.text = allahNames.name
            itemView.meaning.text = allahNames.en.meaning
        }

    }
}