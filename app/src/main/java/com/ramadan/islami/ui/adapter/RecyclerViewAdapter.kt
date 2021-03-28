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
import com.ramadan.islami.ui.fragment.DashboardDirections
import com.ramadan.islami.ui.fragment.FamilyTreeDirections
import com.ramadan.islami.utils.Utils
import com.ramadan.islami.utils.changeNavigation
import com.ramadan.islami.utils.showImg
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_allah_name.view.*
import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.android.synthetic.main.item_family_tree.view.*
import java.util.*
import kotlin.collections.ArrayList
import com.ramadan.islami.ui.activity.AllahNames as AllahNamesActivity
import com.ramadan.islami.ui.activity.Quote as QuoteActivity


class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.CustomView>() {
    private var isDashboard = true
    private var suggestionList = mutableListOf<Collection>()
    private var storiesList = mutableListOf<Story>()
    private var dailyList = mutableListOf<Collection>()
    private var categoryList = mutableListOf<Quote>()
    private var quotesList = ArrayList<String>()
    private var collectionList = mutableListOf<Collection>()
    private var familyTreeList = mutableListOf<Collection>()
    private var allahNames = mutableListOf<AllahNames.AllahNamesItem>()

    fun setSuggestionDataList(data: MutableList<Collection>) {
        data.shuffle()
        suggestionList = data
        notifyDataSetChanged()
    }

    fun setStoriesDataList(data: MutableList<Story>) {
        storiesList = data
        notifyDataSetChanged()
    }

    fun setDailyDataList(data: MutableList<Collection>, isDashboard: Boolean) {
        this.isDashboard = isDashboard
        dailyList = data
        notifyDataSetChanged()
    }

    fun setCategoryDataList(data: MutableList<Quote>) {
        data.removeAt(1)
        categoryList = data
        notifyDataSetChanged()
    }

    fun setQuotesDataList(data: ArrayList<String>, isDashboard: Boolean) {
        this.isDashboard = isDashboard
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

    fun setAllahNamesDataList(data: MutableList<AllahNames.AllahNamesItem>) {
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
            itemView.apply {
                Picasso.get().load(collection.image).error(R.drawable.failure_img)
                    .placeholder(R.drawable.load_img).into(cardImage)
                layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                cardImage.minimumWidth = 350
                cardName.text = collection.title.toUpperCase(Locale.ENGLISH)
                cardName.textSize = 16.toFloat()
                setOnClickListener {
                    when (collection.id) {
                        "hadithOfDay" -> Intent(ctx, QuoteOfDay::class.java).apply {
                            putExtra("intentKey", "hadith")
                            ctx.startActivity(this)
                        }
                        "quran" -> it.changeNavigation(DashboardDirections.actionNavDashboardToNavQuran())
                        "hadiths" -> ctx.startActivity(Intent(ctx, Hadiths::class.java))
                        "muhammadStory" -> Intent(ctx, StoryDetails::class.java).apply {
                            putExtra("documentID", "muhammad")
                            ctx.startActivity(this)
                        }
                    }
                }
            }
        }

        fun storyView(story: Story) {
            Picasso.get().load(story.image).error(R.drawable.failure_img)
                .placeholder(R.drawable.load_img).into(itemView.cardImage)
            itemView.cardName.text = story.title.toUpperCase(Locale.ENGLISH)
            itemView.setOnClickListener {
                Intent(ctx, StoryDetails::class.java).apply {
                    putExtra("story", story)
                    ctx.startActivity(this)
                }
            }
        }

        fun dailyView(collection: Collection) {
            Picasso.get().load(collection.image).error(R.drawable.ic_error_img)
                .placeholder(R.drawable.failure_img).into(itemView.cardImage)
            itemView.cardName.text = collection.title.toUpperCase(Locale.ENGLISH)
            if (isDashboard) {
                itemView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                itemView.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                itemView.cardImage.maxWidth = ctx.resources.getDimension(R.dimen.familyTree).toInt()
                itemView.cardImage.maxHeight =
                    ctx.resources.getDimension(R.dimen.familyTree).toInt()
            }
            itemView.setOnClickListener {
                when (collection.id) {
                    "eveningAzkar" -> {
                        Intent(ctx, TopicDetails::class.java).apply {
                            putExtra("intentKey", "eveningAzkar")
                            ctx.startActivity(this)
                        }
                    }
                    "morningAzkar" -> {
                        Intent(ctx, TopicDetails::class.java).apply {
                            putExtra("intentKey", "morningAzkar")
                            ctx.startActivity(this)
                        }
                    }
                    "verseOfDay" -> {
                        Intent(ctx, QuoteOfDay::class.java).apply {
                            putExtra("intentKey", "verse")
                            ctx.startActivity(this)
                        }
                    }
                    "azkarOfDay" -> {
                        Intent(ctx, TopicDetails::class.java).apply {
                            putExtra("intentKey", "azkar")
                            ctx.startActivity(this)
                        }
                    }
                    "hadithOfDay" -> {
                        Intent(ctx, QuoteOfDay::class.java).apply {
                            putExtra("intentKey", "hadith")
                            ctx.startActivity(this)
                        }
                    }
                    "prayerTimes" -> ctx.startActivity(Intent(ctx, PrayerTimes::class.java))
                    "qibla" -> ctx.startActivity(Intent(ctx, Qibla::class.java))
                    "dateConversion" -> ctx.startActivity(Intent(ctx, DateConversion::class.java))
                    "allahNames" -> ctx.startActivity(Intent(ctx, AllahNamesActivity::class.java))
                }
            }
        }

        fun quotesList(quote: Quote) {
            Picasso.get().load(quote.image).error(R.drawable.ic_error_img)
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
            Picasso.get().load(quotes).error(R.drawable.ic_error_img)
                .placeholder(R.drawable.failure_img).into(itemView.cardImage)
            if (!isDashboard) {
                itemView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            itemView.setOnClickListener { showImg(quotes, ctx) }
        }

        fun collectionView(collection: Collection) {
            Picasso.get().load(collection.image).error(R.drawable.ic_error_img)
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
                if (isDashboard) {
                    when (collection.id) {
                        "muhammadTree" -> it.changeNavigation(DashboardDirections
                            .actionDashboardToFamilyTreeDetails("muhammad"))
                        "prophetsTree" -> it.changeNavigation(DashboardDirections
                            .actionDashboardToFamilyTreeDetails("prophets"))
                        "bigTree" -> it.changeNavigation(DashboardDirections
                            .actionDashboardToFamilyTreeDetails("big"))
                    }
                } else {
                    when (collection.id) {
                        "muhammadTree" -> it.changeNavigation(FamilyTreeDirections
                            .actionFamilyTreeToFamilyTreeDetails("muhammad"))
                        "prophetsTree" -> it.changeNavigation(FamilyTreeDirections
                            .actionFamilyTreeToFamilyTreeDetails("prophets"))
                        "bigTree" -> it.changeNavigation(FamilyTreeDirections
                            .actionFamilyTreeToFamilyTreeDetails("big"))
                    }
                }
            }
        }

        fun allahNamesView(allahNames: AllahNames.AllahNamesItem?, position: Int) {
            if (position % 2 == 0) {
                itemView.allahNameCard.setCardBackgroundColor(ctx.resources.getColor(R.color.colorSecondary))
            } else {
                itemView.allahNameCard.setCardBackgroundColor(ctx.resources.getColor(R.color.colorPrimary))
            }
            itemView.allahNameNumber.text = (position + 1).toString()
            itemView.allahName.text = allahNames?.name ?: "Name"
            itemView.allahNameMeaning.text = allahNames?.english ?: "name"
        }

    }
}