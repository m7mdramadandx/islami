package com.ramadan.islami.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ramadan.islami.data.repo.QuranRepository

class QuranAyahViewModel : ViewModel() {

    fun getAyahBySura(context: Context, suraID: String) =
        QuranRepository.retrieveAllQuran(context, suraID)

//    fun getFontSize() = pref.getAyahFontSize(context)
}