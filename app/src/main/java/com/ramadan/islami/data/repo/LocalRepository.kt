package com.ramadan.islami.data.repo

import android.content.Context
import com.ramadan.islami.data.database.LocalDb
import com.ramadan.islami.data.model.Azkar
import com.ramadan.islami.data.model.Surah
import com.ramadan.islami.data.model.Verse

class LocalRepository {

    companion object {
        private var localDb = LocalDb()
    }

    fun retrieveAllQuran(context: Context): MutableList<Surah> {
        return localDb.retrieveQuran(context)!!.toMutableList()
    }

    fun retrieveAzkar(context: Context): Azkar? {
        return localDb.retrieveAzkar(context)
    }

    fun retrieveVerse(context: Context): Verse? {
        return localDb.retrieveVerse(context)
    }

}