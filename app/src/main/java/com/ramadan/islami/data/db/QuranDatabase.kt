package com.ramadan.islami.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ramadan.islami.data.db.dao.AyahDao
import com.ramadan.islami.data.db.entities.Ayah
import com.ramadan.islami.data.db.entities.Sura

@Database(
    entities = [Sura::class, Ayah::class],
    version = 1,
    exportSchema = false
)
abstract class QuranDatabase : RoomDatabase() {

    abstract fun getAyahDao(): AyahDao

    companion object {
        @Volatile
        private var INSTANCE: QuranDatabase? = null

        fun getInstance(context: Context): QuranDatabase {
            if (INSTANCE != null) return INSTANCE!!
            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, QuranDatabase::class.java, "Quran_DB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .createFromAsset("database/myQuran.db")
                    .build()
                return INSTANCE!!
            }
        }
    }

}