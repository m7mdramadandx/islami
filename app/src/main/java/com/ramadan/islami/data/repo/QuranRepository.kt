package com.ramadan.islami.data.repo

import android.content.Context
import com.ramadan.islami.data.database.QuranDb
import com.ramadan.islami.data.model.Surah

class QuranRepository {

    companion object {
        private var quranDb: QuranDb? = null
        fun retrieveAllQuran(context: Context, search: String): MutableList<Surah> {
            return QuranDb().retrieveQuran(context)!!.toMutableList()
        }
    }

}