package com.ramadan.islamicAwareness.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ramadan.islamicAwareness.data.model.Category
import com.ramadan.islamicAwareness.data.model.Prophet
import com.ramadan.islamicAwareness.data.model.Quote
import com.ramadan.islamicAwareness.data.repo.Repo

class ViewModel : ViewModel() {
    private val repo = Repo()
    fun fetchStory(): LiveData<MutableList<Prophet>> {
        val mutableData = MutableLiveData<MutableList<Prophet>>()
        repo.fetchStory().observeForever { prophetList -> mutableData.value = prophetList }
        return mutableData
    }

    fun fetchCategory(): LiveData<MutableList<Category>> {
        val mutableData = MutableLiveData<MutableList<Category>>()
        repo.fetchCategory().observeForever { categoryList -> mutableData.value = categoryList }
        return mutableData
    }

    fun fetchQuote(category: String): LiveData<MutableList<Quote>> {
        val mutableData = MutableLiveData<MutableList<Quote>>()
        repo.getQuote(category).observeForever { quoteList -> mutableData.value = quoteList }
        return mutableData
    }

}