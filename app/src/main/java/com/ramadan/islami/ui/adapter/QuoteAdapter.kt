package com.ramadan.islami.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Category
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.quote_item.view.*
import com.ramadan.islami.ui.activity.Quote as QuoteActivity


class QuoteAdapter(val context: Context, val isDashboard: Boolean) :
    RecyclerView.Adapter<QuoteAdapter.CustomView>() {
    private var dataList = mutableListOf<Category>()

    fun setDataList(data: MutableList<Category>) {
        dataList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.quote_item, parent, false)
        return CustomView(view)
    }

    override fun getItemCount(): Int = if (dataList.size > 0) dataList.size else 0

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        val category: Category = dataList[position]
        holder.customView(category)
    }


    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun customView(category: Category) {
            Picasso.get().load(category.imgUrl).error(R.drawable.error_img)
                .placeholder(R.drawable.failure_img).into(itemView.categoryImg)
            if (isDashboard) itemView.categoryImg.maxHeight =
                itemView.resources.getDimension(R.dimen.max_card_height_1).toInt()
            itemView.categoryName.text = category.displayName
            itemView.setOnClickListener {
                Intent(itemView.context, QuoteActivity::class.java).apply {
                    putExtra("title", category.displayName)
                    putExtra("category", category.name)
                    itemView.context.startActivity(this)
                }
            }
        }
    }
}