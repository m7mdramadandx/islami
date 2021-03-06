package com.ramadan.islami.data.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.ramadan.islami.data.db.QuranDatabase
import com.ramadan.islami.data.db.QuranDb
import com.ramadan.islami.data.db.entities.Ayah
import com.ramadan.islami.data.db.entities.Sura
import com.ramadan.islami.data.model.Surah

class QuranRepository {

    companion object {
        private var quranDatabase: QuranDatabase? = null
        private var quranDb: QuranDb? = null

        fun retrieveAllSurahs(context: Context, search: String): LiveData<MutableList<Sura>> {
            quranDatabase = QuranDatabase.getInstance(context)
            return quranDatabase!!.getAyahDao().getAllSura(search)
        }

        fun retrieveAllQuran(context: Context, search: String): MutableList<Surah> {
            val liveData = QuranDb().getFullQuranSurahs(context)!!.toMutableList()
            return liveData
        }

        fun retrieveAllSurahs(context: Context, suraId: Int): LiveData<MutableList<Ayah>> {
            quranDatabase = QuranDatabase.getInstance(context)
            return quranDatabase!!.getAyahDao().getAllAyah(suraId)
        }

    }

}