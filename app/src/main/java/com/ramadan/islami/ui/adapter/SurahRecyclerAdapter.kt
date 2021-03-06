package com.ramadan.islami.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.listener.SurahRecyclerListener
import com.ramadan.islami.data.model.Surah
import kotlinx.android.synthetic.main.item_quran_surah.view.*

class SurahRecyclerAdapter(
    val listener: SurahRecyclerListener,
) : RecyclerView.Adapter<SurahRecyclerAdapter.ViewHolder>() {

    var list: List<Surah> = listOf()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_quran_surah, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView
        val item = list[position]

        view.apply {
            tv_item_surah_verse.text = item.num
            tv_item_surah_arab.text = item.name
            tv_item_surah_total_ayah.text = "${item.ayahs!!.size}"
            setOnClickListener { listener.onClick(it, item) }
        }
    }

    fun updateList(list: List<Surah>) {
        this.list = list
        notifyDataSetChanged()
    }

}
