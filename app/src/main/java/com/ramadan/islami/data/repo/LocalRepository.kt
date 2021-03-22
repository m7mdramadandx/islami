package com.ramadan.islami.data.repo

import android.content.Context
import com.ramadan.islami.data.database.LocalDb
import com.ramadan.islami.data.model.AllahNames
import com.ramadan.islami.data.model.Azkar
import com.ramadan.islami.data.model.Surah
import com.ramadan.islami.data.model.Verse

class LocalRepository {

    companion object {
        private var localDb = LocalDb()
    }

    fun retrieveAllQuran(context: Context): MutableList<Surah> =
        localDb.retrieveQuran(context)!!.toMutableList()

    fun retrieveAzkar(context: Context): Azkar? = localDb.retrieveAzkar(context)

    fun retrieveVerse(context: Context): Verse? = localDb.retrieveVerse(context)

    fun retrieveAllahNames(context: Context): AllahNames? = localDb.retrieveAllahNames(context)

}