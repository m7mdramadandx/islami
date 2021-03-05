package com.ramadan.islami.data.listener

interface DataListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)

}