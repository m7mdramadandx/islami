package com.ramadan.islami.ui.adapter

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.utils.LocaleHelper
import kotlinx.android.synthetic.main.story_item.view.*

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.CustomView>() {
    private var text = ArrayList<String>()
    private var title: String? = null

    fun setStoriesDataList(data: ArrayList<String>, storyTitle: String) {
        text = data
        title = storyTitle
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryAdapter.CustomView {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.story_item, parent, false)
        return CustomView(view)
    }

    override fun onBindViewHolder(holder: StoryAdapter.CustomView, position: Int) {
        return holder.expandView(text[position], position)
    }

    override fun getItemCount(): Int = text.size


    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val localeHelper = LocaleHelper()
        private val mContext = itemView.context
        private val marks = localeHelper.getMark(mContext)
        private val secondaryColor = Color.rgb(23, 34, 59)
        private val accentColor = Color.rgb(175, 135, 50)


        fun expandView(text: String, position: Int) {
            if (position % 2 == 0) itemView.expansionCard.setCardBackgroundColor(secondaryColor)
            itemView.expansionText.text = text
            itemView.storyTitle.text = "${mContext.getString(R.string.part)} ${(position + 1)}"
            marks.forEach {
                if (it == "$$title ${position + 1}")
                    itemView.expansionCard.setCardBackgroundColor(accentColor)
            }
            itemView.expansionLayout.addListener { expansionLayout, isExpanded ->
                if (!isExpanded) {
                    marks.forEach {
                        if (it != "$$title ${position + 1}") {
                            val dialogBuilder = AlertDialog.Builder(mContext)
                            val view =
                                LayoutInflater.from(mContext).inflate(R.layout.story_marker, null)
                            dialogBuilder.setView(view)
                            val alertDialog = dialogBuilder.create()
                            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            alertDialog.show()
                            view.findViewById<TextView>(R.id.yes).setOnClickListener {
                                localeHelper.setMark(mContext, "$$title ${position + 1}")
                                itemView.expansionCard.setCardBackgroundColor(accentColor)
                                alertDialog.dismiss()
                            }
                            view.findViewById<TextView>(R.id.not_yet)
                                .setOnClickListener { alertDialog.dismiss() }
                        }
                    }
                }
            }
        }
    }
}