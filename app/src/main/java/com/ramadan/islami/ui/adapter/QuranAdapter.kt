package com.ramadan.islami.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.listener.SurahRecyclerListener
import com.ramadan.islami.data.model.Quran
import kotlinx.android.synthetic.main.item_quran_ayah.view.*
import kotlinx.android.synthetic.main.item_quran_surah.view.*

class QuranAdapter : RecyclerView.Adapter<QuranAdapter.CustomView>() {
    var listener: SurahRecyclerListener? = null
    private var surahList: MutableList<Quran.Surah> = mutableListOf()
    private var ayahList: MutableList<Quran.Ayah> = mutableListOf()

    fun setSuraDataList(list: MutableList<Quran.Surah>, listener: SurahRecyclerListener) {
        this.listener = listener
        this.surahList = list
        notifyDataSetChanged()
    }

    fun setAyahDataList(list: MutableList<Quran.Ayah>) {
        this.ayahList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): QuranAdapter.CustomView {
        val view: View = when {
            surahList.size > 1 -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_quran_surah, parent, false)
            ayahList.size > 1 -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_quran_ayah, parent, false)
            else -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_quran_ayah, parent, false)
        }
        return CustomView(view)
    }

    override fun getItemCount(): Int {
        return if (surahList.size > 0) surahList.size else ayahList.size
    }

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        return if (surahList.size > 0) holder.surahView(surahList[position])
        else holder.ayahView(ayahList[position])
    }

    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun surahView(surah: Quran.Surah) {
            itemView.apply {
                surahNumber.text = surah.number.toString()
                surahName.text = surah.name
                versesNumber.text = surah.ayahs.size.toString()
                juzNumber.text = surah.ayahs.first().juz.toString()
                revelationType.text = surah.revelationType
                setOnClickListener { listener?.onClick(it, surah) }
            }
        }

        fun ayahView(ayah: Quran.Ayah) {
            itemView.apply {
                ayahNumber.text = ayah.numberInSurah.toString()
                ayahText.text = ayah.text
            }
        }

    }
}
