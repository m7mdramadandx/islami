package com.ramadan.islami.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.listener.SurahRecyclerListener
import com.ramadan.islami.data.model.Quran
import kotlinx.android.synthetic.main.item_quran_surah.view.*

class QuranRecyclerView(val listener: SurahRecyclerListener) :
    RecyclerView.Adapter<QuranRecyclerView.CustomView>() {

    private var surahList: MutableList<Quran.Surah> = mutableListOf()

    fun updateList(list: MutableList<Quran.Surah>) {
        this.surahList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): QuranRecyclerView.CustomView =
        CustomView(
            LayoutInflater.from(parent.context).inflate(R.layout.item_quran_surah, parent, false)
        )

    override fun getItemCount(): Int = surahList.size

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        return holder.surahView(surahList[position])

    }

    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun surahView(surah: Quran.Surah) {
            itemView.apply {
                surahNumber.text = surah.number.toString()
                surahName.text = surah.name
                versesNumber.text = surah.ayahs.size.toString()
                juzNumber.text = surah.ayahs.first().juz.toString()
                revelationType.text = surah.revelationType
                setOnClickListener { listener.onClick(it, surah) }
            }
        }

    }
}
