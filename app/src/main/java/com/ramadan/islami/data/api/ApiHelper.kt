package com.ramadan.islami.data.api

import com.ramadan.islami.utils.dateOfDay
import com.ramadan.islami.utils.lat
import com.ramadan.islami.utils.lon


class ApiHelper(private val apiService: ApiService) {
    private val dateOfDay = dateOfDay()
    suspend fun allahNames() = apiService.allahNames()
    suspend fun gregorianCalender() = apiService.gregorianCalender(dateOfDay)
    suspend fun hijriCalender() = apiService.hijriCalender(dateOfDay)
    suspend fun fetchPrayers() = apiService.fetchPrayers(lat, lon)
    suspend fun hadithOfDay() = apiService.hadithOfDay()
}