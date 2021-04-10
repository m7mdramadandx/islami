package com.ramadan.islami.ui.adapter

import androidx.appcompat.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.utils.LocaleHelper
import kotlinx.android.synthetic.main.alert_dialog.view.*
import kotlinx.android.synthetic.main.item_story.view.*

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.CustomView>() {
    private var text = ArrayList<String>()
    private var title: String? = null

    fun setStoriesDataList(storyTitle: String, data: ArrayList<String>) {
        text = data
        title = storyTitle
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryAdapter.CustomView {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_story, parent, false)
        return CustomView(view)
    }

    override fun onBindViewHolder(holder: StoryAdapter.CustomView, position: Int) {
        return holder.expandView(text[position])
    }

    override fun getItemCount(): Int = text.size


    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val localeHelper = LocaleHelper()
        private val ctx = itemView.context
        private var marks = localeHelper.getStoryMark(ctx)
        private var execOnce: Boolean? = true
        fun expandView(text: String) {
            val keyStore = "$title ${layoutPosition + 1}"
            @Suppress("DEPRECATION")
            when {
                marks.contains(keyStore) -> {
                    itemView.expansionCard.setCardBackgroundColor(ctx.resources.getColor(R.color.silver_grey))
                    itemView.storyTitle.setTextColor(ctx.resources.getColor(R.color.grey_silver))
                }
                layoutPosition % 2 == 0 -> {
                    itemView.expansionCard.setCardBackgroundColor(ctx.resources.getColor(R.color.colorSecondary))
                }
                else -> itemView.expansionCard.setCardBackgroundColor(ctx.resources.getColor(R.color.colorPrimary))
            }
            itemView.expansionText.text = text.replace(":", "\n")
            "${ctx.getString(R.string.part)} ${layoutPosition + 1}".also {
                itemView.storyTitle.text = it
            }
            itemView.expansionLayout.addListener { _, isExpanded ->
                if (!isExpanded && !marks.contains("$title ${layoutPosition + 1}")) {
                    execOnce?.let { alert("$title ${layoutPosition + 1}") }
                    execOnce = null
                    return@addListener
                } else {
                    return@addListener
                }
            }
        }

        private fun alert(keyStore: String) {
            val dialogBuilder = AlertDialog.Builder(ctx)
            val view = LayoutInflater.from(ctx).inflate(R.layout.alert_dialog_read_marker, null)
            dialogBuilder.setView(view)
            val alertDialog = dialogBuilder.create()
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.window!!.attributes.windowAnimations = R.style.ShrinkAnimation
            alertDialog.show()
            view.findViewById<TextView>(R.id.yes).setOnClickListener {
                localeHelper.setStoryMark(ctx, keyStore)
                @Suppress("DEPRECATION")
                itemView.expansionCard.setCardBackgroundColor(ctx.resources.getColor(R.color.silver_grey))
                itemView.storyTitle.setTextColor(ctx.resources.getColor(R.color.grey_silver))
                alertDialog.dismiss()
            }
            view.findViewById<TextView>(R.id.notYet)
                .setOnClickListener { alertDialog.dismiss() }
        }
    }
}
