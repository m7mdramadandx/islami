package com.ramadan.islami.ui.viewModel

interface Listener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)

}