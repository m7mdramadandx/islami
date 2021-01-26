package com.ramadan.islami.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.ramadan.islami.data.model.Category
import com.ramadan.islami.data.model.Prophet
import com.ramadan.islami.data.model.Quote
import com.ramadan.islami.utils.defaultImg
import kotlinx.coroutines.tasks.await

class Repository {
    private val tag = "Repository"
    private val rootCollection = FirebaseFirestore.getInstance().collection("root")
    var language = "ar"

    suspend fun fetchAllStories(isEnglish: Boolean): LiveData<MutableList<Prophet>> {
        val mutableData = MutableLiveData<MutableList<Prophet>>()
        val dataList: MutableList<Prophet> = mutableListOf()
        if (isEnglish) language = "en"
        rootCollection.document(language).collection("stories").get()
            .addOnSuccessListener { result ->
                result.forEach { document ->
                    val displayName = document.getString("name") ?: document.id
                    val imgUrl = document.getString("image")
                    val text = document.get("text") as ArrayList<String>?
                    val prophet = Prophet(displayName, document.id, imgUrl!!, text!!)
                    dataList.add(prophet)
                }
                mutableData.value = dataList
            }.addOnFailureListener { e -> println("Error!! + $e") }.await()
        return mutableData
    }

    suspend fun fetchStory(isEnglish: Boolean, prophetName: String): Prophet {
        var prophet = Prophet("", "", defaultImg, ArrayList(0))
        if (isEnglish) language = "en"
        rootCollection.document(language).collection("stories").get().await()
            .forEach { document ->
                if (prophetName == document.id) {
                    val displayName: String = document.getString("name") ?: document.id
                    val imgUrl: String? = document.getString("image")
                    val text: ArrayList<String>? = document.get("text") as ArrayList<String>?
                    prophet = Prophet(displayName, document.id, imgUrl!!, text!!)
                }
            }
        return prophet
    }

    suspend fun fetchCategories(isEnglish: Boolean): LiveData<MutableList<Category>> {
        val mutableLiveData = MutableLiveData<MutableList<Category>>()
        val dataList: MutableList<Category> = mutableListOf()
        if (isEnglish) language = "en"
        rootCollection.document(language).collection("quotes").get().await()
            .forEach { snapshot ->
                val displayName: String = snapshot.getString("name") ?: snapshot.id
                val name: String = snapshot.id
                val imgUrl: String? = snapshot.getString("image")
                val category = Category(displayName, name, imgUrl!!)
                dataList.add(category)
            }
        mutableLiveData.value = dataList
        return mutableLiveData
    }

    suspend fun getQuote(isEnglish: Boolean, category: String): Quote {
        var quote: Quote
        if (isEnglish) language = "en"
        val data = rootCollection.document(language).collection("quotes")
            .document(category).get().await()
        val imgUrl: String? = data.getString("image")
        val verses = data?.get("verses") as ArrayList<String>
        val hadiths = data.get("hadiths") as ArrayList<String>
        quote = Quote(imgUrl!!, verses, hadiths)
        return quote
    }

    fun sendFeedback(msg: String) {
        rootCollection.document("feedback").collection("messages")
            .add(mapOf("msg" to msg))
            .addOnCompleteListener { println("DONE") }
            .addOnFailureListener { println(it.message) }
    }
}