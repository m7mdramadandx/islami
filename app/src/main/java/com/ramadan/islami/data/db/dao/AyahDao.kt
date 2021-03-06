package com.ramadan.islami.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.ramadan.islami.data.db.entities.Ayah
import com.ramadan.islami.data.db.entities.Sura

@Dao
interface AyahDao {

    @Query("SELECT * FROM ayah WHERE suraID = :suraId")
    fun getAllAyah(suraId: Int): LiveData<MutableList<Ayah>>

    @Query("SELECT * FROM sura WHERE englishName LIKE '%' || :search || '%' OR id LIKE '%' || :search || '%'")
    fun getAllSura(search: String): LiveData<MutableList<Sura>>

}