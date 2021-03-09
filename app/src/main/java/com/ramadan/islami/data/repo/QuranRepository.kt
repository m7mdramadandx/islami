package com.ramadan.islami.data.repo

import android.content.Context
import com.ramadan.islami.data.database.QuranDb
import com.ramadan.islami.data.model.Surah

class QuranRepository {

    companion object {
        private var quranDb = QuranDb()
         fun retrieveAllQuran(context: Context, search: String): MutableList<Surah> {
            return quranDb.retrieveQuran(context)!!.toMutableList()
        }
    }

}