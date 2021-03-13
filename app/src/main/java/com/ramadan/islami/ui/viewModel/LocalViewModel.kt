package com.ramadan.islami.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ramadan.islami.data.repo.LocalRepository

class LocalViewModel : ViewModel() {

    fun getAllSura(context: Context, search: String) =
        LocalRepository.retrieveAllQuran(context, search)

    fun getAzkar(context: Context) = LocalRepository.retrieveAzkar(context)

}