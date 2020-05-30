package com.ramadan.theReminder.Adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.theReminder.Hadith
import com.ramadan.theReminder.HadithDashboard
import com.ramadan.theReminder.Model.Category
import com.ramadan.theReminder.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.hadith_item.view.*


class HadithAdapter(val context: HadithDashboard) :
    RecyclerView.Adapter<HadithAdapter.CustomView>() {
    private var dataList = mutableListOf<Category>()

    fun setDataList(data: MutableList<Category>) {
        dataList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.hadith_item, parent, false)
        return CustomView(view)
    }

    override fun getItemCount(): Int {
        if (dataList.size > 0) {
            return dataList.size
        } else {
            return 0

        }
    }

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        val category: Category = dataList[position]
        holder.customView(category)
    }


    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun customView(category: Category) {
            Picasso.get().load(category.imgUrl).error(R.drawable.error_img)
                .placeholder(R.drawable.load_img).into(itemView.categoryImg)
//            if (category.name == "prophetMuhammad") {
//                category.name = "Ramadan"
//                println(category.name)
//            }
            if (category.name == "Death") {
                itemView.categoryName.text = "Judgement Day"
            } else {
                itemView.categoryName.text = category.name
            }
            itemView.setOnClickListener(View.OnClickListener {
                val intent = Intent(itemView.context, Hadith::class.java)
                val bundle = Bundle()
                bundle.putString("category", category.name)
                intent.putExtras(bundle)
                itemView.context.startActivity(intent)
            })
        }
    }
}