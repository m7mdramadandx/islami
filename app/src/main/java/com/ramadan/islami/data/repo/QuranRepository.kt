package com.ramadan.islami.data.repo

import android.content.Context
import com.ramadan.islami.data.db.QuranDb
import com.ramadan.islami.data.model.Surah

class QuranRepository {

    companion object {
        //        private var quranDatabase: QuranDatabase? = null
        private var quranDb: QuranDb? = null

        fun retrieveAllQuran(context: Context, search: String): MutableList<Surah> {
            return QuranDb().retrieveQuran(context)!!.toMutableList()
        }

//        fun retrieveAllSurahs(context: Context, suraId: Int): LiveData<MutableList<Ayah>> {
//            quranDatabase = QuranDatabase.getInstance(context)
//            return quranDatabase!!.getAyahDao().getAllAyah(suraId)
//        }

    }

}