package com.ramadan.islami.data.repo

import com.ramadan.islami.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    fun getPrayers() = apiHelper.getPrayers()
}