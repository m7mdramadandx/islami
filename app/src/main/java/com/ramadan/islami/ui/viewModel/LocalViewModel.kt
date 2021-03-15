package com.ramadan.islami.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ramadan.islami.data.repo.LocalRepository

class LocalViewModel : ViewModel() {

    companion object {
        val localRepository = LocalRepository()
    }

    fun getAllSura(context: Context) = localRepository.retrieveAllQuran(context)

    fun getAzkar(context: Context) = localRepository.retrieveAzkar(context)

    fun getVerseOfDay(context: Context) = localRepository.retrieveVerse(context)

}