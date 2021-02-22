package com.ramadan.islami.data.repo

import com.ramadan.islami.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

//    suspend fun gregorianCalender() = apiHelper.gregorianCalender()
    suspend fun allahNames() = apiHelper.allahNames()
    suspend fun gregorianCalender() = apiHelper.gregorianCalender()
    suspend fun hijriCalender() = apiHelper.hijriCalender()
    suspend fun fetchPrayers() = apiHelper.fetchPrayers()
    suspend fun hadithOfDay() = apiHelper.hadithOfDay()
}