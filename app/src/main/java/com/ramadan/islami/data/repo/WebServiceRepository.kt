package com.ramadan.islami.data.repo

import com.ramadan.islami.data.api.ApiHelper

class WebServiceRepository(private val apiHelper: ApiHelper) {

    suspend fun allahNames() = apiHelper.allahNames()
    suspend fun gregorianCalender(dateOfDay: String) = apiHelper.gregorianCalender(dateOfDay)
    suspend fun hijriCalender(dateOfDay: String) = apiHelper.hijriCalender(dateOfDay)
    suspend fun fetchPrayers(lat: Double, lon: Double) = apiHelper.fetchPrayers(lat, lon)
    suspend fun hadithOfDay() = apiHelper.hadithOfDay()
}