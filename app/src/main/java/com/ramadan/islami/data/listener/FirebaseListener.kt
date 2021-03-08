package com.ramadan.islami.data.listener

interface FirebaseListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)

}