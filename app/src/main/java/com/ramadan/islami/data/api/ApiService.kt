package com.ramadan.islami.data.api

import com.ramadan.islami.data.model.Calender
import com.ramadan.islami.data.model.Hadith
import com.ramadan.islami.data.model.Prayer
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

//    @GET("v1/qibla")
//    suspend fun qibla(@Path("/") latlng: String): Qibla

//    @GET("asmaAlHusna")
//    suspend fun allahNames(): AllahNames

    @GET("v1/hToG")
    suspend fun gregorianCalender(@Query("date") date: String): Calender

    @GET("v1/gToH")
    suspend fun hijriCalender(@Query("date") date: String): Calender

    @GET("v1/calendar?method=5")
    suspend fun fetchPrayers(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
    ): Prayer

    @Headers("X-API-Key: SqD712P3E82xnwOAEOkGd5JZH8s9wRR24TqNFzjk")
    @GET("v1/hadiths/random")
    suspend fun hadithOfDay(): Hadith

}