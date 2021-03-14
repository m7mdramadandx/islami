package com.ramadan.islami.data.database

import android.content.Context
import com.google.gson.Gson
import com.ramadan.islami.data.model.Azkar
import com.ramadan.islami.data.model.Quran
import com.ramadan.islami.data.model.Surah
import com.ramadan.islami.utils.showMessage
import java.io.BufferedInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.Charset

class LocalDb {

    fun retrieveQuran(context: Context): List<Surah>? {
        return try {
            val fileIn: InputStream = context.assets.open("quran.json")
            val bufferedIn = BufferedInputStream(fileIn)
            val reader: Reader = InputStreamReader(bufferedIn, Charset.forName("UTF-8"))
            Gson().fromJson(reader, Quran::class.java).data.surahs
        } catch (e: Exception) {
            showMessage(context, e.localizedMessage!!)
            return mutableListOf()
        }
    }
    fun retrieveTafseer(context: Context): List<Surah>? {
        return try {
            val fileIn: InputStream = context.assets.open("quran.json")
            val bufferedIn = BufferedInputStream(fileIn)
            val reader: Reader = InputStreamReader(bufferedIn, Charset.forName("UTF-8"))
            Gson().fromJson(reader, Quran::class.java).data.surahs
        } catch (e: Exception) {
            showMessage(context, e.localizedMessage!!)
            return mutableListOf()
        }
    }

    fun retrieveAzkar(context: Context): Azkar? {
        return try {
            val fileIn: InputStream = context.assets.open("azkar.json")
            val bufferedIn = BufferedInputStream(fileIn)
            val reader: Reader = InputStreamReader(bufferedIn, Charset.forName("UTF-8"))
            Gson().fromJson(reader, Azkar::class.java)
        } catch (e: Exception) {
            showMessage(context, e.localizedMessage!!)
            return Azkar()
        }
    }
}