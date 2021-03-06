package com.ramadan.islami.data.listener

import android.view.View
import com.ramadan.islami.data.model.Quran.Surah

interface SurahRecyclerListener {

    fun onClick(view: View, sura: Surah)
}