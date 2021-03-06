package com.ramadan.islami.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Aya
import kotlinx.android.synthetic.main.item_quran_ayah.view.*

class AyahRecyclerAdapter(val fontSize: String?) :
    RecyclerView.Adapter<AyahRecyclerAdapter.ViewHolder>() {

    var list: List<Aya> = listOf()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_quran_ayah, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView
        val item = list[position]

        view.apply {
            tv_item_ayah_verse.text = item.num
//            tv_item_ayah_latin.text = item.readText
            tv_item_ayah_arab.text = item.text
//            tv_item_ayah_translate.text = item.indoText

            val color = if (position % 2 == 0) {
                R.color.silver_grey
            } else {
                R.color.colorBackground
            }
            setBackgroundColor(ContextCompat.getColor(view.context, color))

            fontSize?.let {
                tv_item_ayah_arab.textSize = it.toFloat()
            }
        }
    }

    fun updateList(list: List<Aya>) {
        this.list = list
        notifyDataSetChanged()
    }
}
