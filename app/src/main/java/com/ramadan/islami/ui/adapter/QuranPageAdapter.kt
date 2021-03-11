package com.ramadan.islami.ui.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Quran
import com.ramadan.islami.data.model.Surah
import com.ramadan.islami.ui.adapter.QuranPageAdapter.QuranPage
import com.ramadan.islami.utils.nf
import kotlinx.android.synthetic.main.item_ayah.view.*
import java.lang.String.valueOf


class QuranPageAdapter : RecyclerView.Adapter<QuranPage>() {

    private var ayahList: MutableList<Quran.Ayah> = mutableListOf()
    var surahName = String()

    fun setAyahDataList(surah: Surah) {
        this.ayahList = surah.ayahs.toMutableList()
        this.surahName = surah.name
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuranPage {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ayah, parent, false)
        return QuranPage(view);
    }

    override fun getItemCount(): Int {
        return ayahList.last().page - ayahList.first().page + 1
    }

    override
    fun onBindViewHolder(holder: QuranPage, position: Int) {
        holder.setAyah(ayahList, ayahList.first().page + position, surahName)
    }

    class QuranPage(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setAyah(ayahList: MutableList<Quran.Ayah>, position: Int, surahName: String) {
            var text = String()
            itemView.apply {
                ayahList.forEach {
                    if (it.page == position) {
                        var ayahNumber =
                            " \uFD3F" + valueOf(nf.format(it.numberInSurah)) + "\uFD3E "
                        ayahNumber = ayahNumber.replace(ayahNumber,
                            "<font color='#E1B34F'>$ayahNumber</font>")
                        text += it.text + ayahNumber
                        _juzNumber.text = it.juz
                        _hizbNumber.text =
                            context.getString(R.string.hizbNumber) + valueOf(nf.format(it.hizbQuarter / 4))
                    }
                }
                _surahName.text = surahName
                ayahText.text = (Html.fromHtml(text))

            }
        }
    }

}
