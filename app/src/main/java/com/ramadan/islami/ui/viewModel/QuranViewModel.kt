package com.ramadan.islami.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ramadan.islami.data.repo.QuranRepository

class QuranViewModel : ViewModel() {

    fun getAllSura(context: Context, search: String) =
        QuranRepository.retrieveAllQuran(context, search)

}