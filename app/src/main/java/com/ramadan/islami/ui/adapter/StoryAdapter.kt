package com.ramadan.islami.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.utils.LocaleHelper
import kotlinx.android.synthetic.main.expand_item.view.*
import kotlin.random.Random

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.CustomView>() {
    private var text = ArrayList<String>()

    fun setStoriesDataList(data: ArrayList<String>) {
        text = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryAdapter.CustomView {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.expand_item, parent, false)
        return CustomView(view)
    }

    override fun onBindViewHolder(holder: StoryAdapter.CustomView, position: Int) {
        return holder.expandView(text[position], position)
    }

    override fun getItemCount(): Int = text.size


    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val localeHelper = LocaleHelper()
        private val mContext = itemView.context
        fun expandView(text: String, position: Int) {
            if (position % 2 != 0)
                itemView.expandedCard.setCardBackgroundColor(Color.rgb(23, 34, 59))
            itemView.storyText.text = text
            itemView.storyTitle.text =
                "${itemView.context.getString(R.string.part)}${(position + 1)}"
            itemView.expansionLayout.addListener { expansionLayout, isExpanded ->
                if (!isExpanded) {
                    localeHelper.setMark(mContext, Random.nextDouble(0.0, 100.0))
                    Toast.makeText(mContext, isExpanded.toString(), Toast.LENGTH_LONG).show()
                }
            }
//            itemView.expansionHeader.addListener { expansionLayout, isExpanded ->
//                print(isExpanded.toString())
//                Toast.makeText(itemView.context, isExpanded.toString() + "-", Toast.LENGTH_LONG)
//                    .show()
//            }
        }

    }
}