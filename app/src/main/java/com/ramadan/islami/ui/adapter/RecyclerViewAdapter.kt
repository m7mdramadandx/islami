package com.ramadan.islami.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import com.ramadan.islami.utils.showImg
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_allah_name.view.*
import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.android.synthetic.main.item_family_tree.view.*
import java.util.*
import kotlin.collections.ArrayList
import com.ramadan.islami.ui.activity.AllahNames as AllahNamesActivity
import com.ramadan.islami.ui.activity.Quote as QuoteActivity
import com.ramadan.islami.ui.activity.StoryDetails as ActivityStory


class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.CustomView>() {
    private var isDashboard = true
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

    fun setQuotesDataList(data: ArrayList<String>, isDashboard: Boolean) {
        this.isDashboard = isDashboard
        data.removeAt(1)
        data.shuffle()
        quotesList = data
        notifyDataSetChanged()
    }

    fun setCollectionsDataList(data: MutableList<Collection>) {
        collectionList = data
        notifyDataSetChanged()
    }

    fun setFamilyTreeDataList(data: MutableList<Collection>, isDashboard: Boolean) {
        this.isDashboard = isDashboard
        familyTreeList = data
        notifyDataSetChanged()
    }

    fun setAllahNamesDataList(data: MutableList<AllahNames.Data>) {
        allahNames = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView {
        val view: View = when {
            allahNames.size > 1 -> {
                LayoutInflater.from(parent.context).inflate(R.layout.item_allah_name, parent, false)
            }
            familyTreeList.isNotEmpty() -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_family_tree, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
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
            allahNames.isNotEmpty() -> holder.allahNamesView(allahNames[position], position)
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
            else -> 0
        }
    }

    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ctx: Context = itemView.context
        private val util = Utils(ctx)

        fun suggestionView(collection: Collection) {
            Picasso.get().load(collection.image).error(R.drawable.failure_img)
                .placeholder(R.drawable.load_img).into(itemView.cardImage)
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
                .placeholder(R.drawable.load_img).into(itemView.cardImage)
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
            if (isDashboard) {
                itemView.cardItem.setCardBackgroundColor(Color.WHITE)
                itemView.cardImage.maxWidth =
                    ctx.resources.getDimension(R.dimen.familyTree).toInt()
                itemView.cardImage.maxHeight = 140
            }
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
                .placeholder(R.drawable.failure_img).into(itemView.cardImage)
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
                .placeholder(R.drawable.failure_img).into(itemView.cardImage)
            if (isDashboard) {
                itemView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            itemView.setOnClickListener { showImg(quotes, ctx) }
        }

        fun collectionView(collection: Collection) {
            Picasso.get().load(collection.image).error(R.drawable.error_img)
                .placeholder(R.drawable.failure_img).into(itemView.cardImage)
            itemView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            itemView.cardName.text = collection.title
            itemView.setOnClickListener {
                when (collection.id) {
                    "videos" -> {
                        Intent(ctx, VideoList::class.java).apply {
                            putExtra("id", collection.id)
                            putExtra("title", collection.title)
                            ctx.startActivity(this)
                        }
                    }
                    else -> {
                        Intent(ctx, TopicList::class.java).apply {
                            putExtra("collectionId", collection.id)
                            putExtra("title", collection.title)
                            ctx.startActivity(this)
                        }
                    }
                }
            }
        }

        fun familyTreeView(collection: Collection) {
            itemView.familyTreeName.text = collection.title.toUpperCase(Locale.ENGLISH)
            if (isDashboard) {
                itemView.familyTreeCard.setCardBackgroundColor(ctx.resources.getColor(R.color.colorBackground))
                itemView.familyTreeImage.maxWidth =
                    ctx.resources.getDimension(R.dimen.familyTree).toInt()
            }
            itemView.setOnClickListener {
                when (collection.id) {
                    "muhammadTree" -> ctx.startActivity(Intent(ctx, PrayerTimes::class.java))
                    "prophetsTree" -> ctx.startActivity(Intent(ctx, Qibla::class.java))
                    "bigTree" -> ctx.startActivity(Intent(ctx, Qibla::class.java))
                }
            }
        }

        fun allahNamesView(allahNames: AllahNames.Data?, position: Int) {
            if (position % 2 == 0) {
                itemView.allahNameCard.setCardBackgroundColor(ctx.resources.getColor(R.color.colorSecondary))
            } else {
                itemView.allahNameCard.setCardBackgroundColor(ctx.resources.getColor(R.color.colorPrimary))
            }
            itemView.allahNameNumber.text = allahNames?.number.toString() ?: "0"
            itemView.allahName.text = allahNames?.name ?: "l"
            itemView.allahNameMeaning.text = allahNames?.en?.meaning ?: "2"
        }

    }
}