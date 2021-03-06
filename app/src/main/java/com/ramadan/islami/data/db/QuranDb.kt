package com.ramadan.islami.data.db

import android.content.Context
import com.google.gson.Gson
import com.ramadan.islami.data.model.FullQuran
import com.ramadan.islami.data.model.Surah
import java.io.BufferedInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.Charset

class QuranDb {

    fun getFullQuranSurahs(context: Context): List<Surah>? {
        return try {
            val fileIn: InputStream = context.assets.open("quran.json")
            val bufferedIn: BufferedInputStream = BufferedInputStream(fileIn)
            val reader: Reader = InputStreamReader(bufferedIn, Charset.forName("UTF-8"))
            Gson().fromJson<FullQuran>(reader, FullQuran::class.java).getData()!!.getSurahs()
        } catch (e: Exception) {
            null
        }
    }
}