package com.ramadan.islami.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.ramadan.islami.data.model.*
import com.ramadan.islami.utils.defaultImg
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList

class Repository {
    private val tag = "Repository"
    private val rootCollection = FirebaseFirestore.getInstance().collection("root")
    var language = "ar"

    suspend fun fetchAllStories(isEnglish: Boolean): LiveData<MutableList<Story>> {
        val mutableData = MutableLiveData<MutableList<Story>>()
        val dataList: MutableList<Story> = mutableListOf()
        if (isEnglish) language = "en"
        rootCollection.document(language).collection("stories").get()
            .addOnSuccessListener { result ->
                result.forEach { document ->
                    val displayName = document.getString("name") ?: document.id
                    val imgUrl = document.getString("image") ?: defaultImg
                    val text = document.get("text") as ArrayList<String>?
                    val prophet = Story(displayName, document.id, imgUrl, text!!)
                    dataList.add(prophet)
                }
                mutableData.value = dataList
            }.addOnFailureListener { e -> println("Error!! + $e") }.await()
        return mutableData
    }

    suspend fun fetchStory(isEnglish: Boolean, prophetName: String): Story {
        var prophet = Story("", "", defaultImg, ArrayList(0))
        if (isEnglish) language = "en"
        rootCollection.document(language).collection("stories").get().await()
            .forEach { document ->
                if (prophetName == document.id) {
                    val displayName: String =
                        document.getString("name") ?: document.id.toUpperCase(Locale.ROOT)
                    val name: String = document.id
                    val imgUrl: String = document.getString("image") ?: defaultImg
                    val text: ArrayList<String>? = document.get("text") as ArrayList<String>?
                    prophet = Story(displayName, name, imgUrl, text!!)
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
                val displayName: String =
                    snapshot.getString("name") ?: snapshot.id.toUpperCase(Locale.ROOT)
                val name: String = snapshot.id
                val imgUrl: String? = snapshot.getString("image")
                val category = Category(displayName, name, imgUrl!!)
                dataList.add(category)
            }
        mutableLiveData.value = dataList
        return mutableLiveData
    }

    suspend fun fetchQuote(isEnglish: Boolean, category: String): Quote {
        val quote: Quote
        if (isEnglish) language = "en"
        val data = rootCollection.document(language).collection("quotes")
            .document(category).get().await()
        val imgUrl: String = data.getString("image") ?: defaultImg
        val verses = (data?.get("verses") ?: ArrayList<String>(0)) as ArrayList<String>
        val hadiths = data.get("hadiths") as ArrayList<String>
        quote = Quote(imgUrl, verses, hadiths)
        return quote
    }

    suspend fun fetchHadiths(isEnglish: Boolean): Quote {
        val quote: Quote
        if (isEnglish) language = "en"
        val data = rootCollection.document(language).collection("quotes")
            .document("hadiths").get().await()
        val imgUrl: String = data.getString("image") ?: defaultImg
        val hadiths = data.get("hadiths") as ArrayList<String>
        quote = Quote(imgUrl, ArrayList(0), hadiths)
        return quote
    }

    suspend fun fetchVideos(isEnglish: Boolean): MutableLiveData<MutableList<Video>> {
        val mutableLiveData = MutableLiveData<MutableList<Video>>()
        val dataList: MutableList<Video> = mutableListOf()
        if (isEnglish) language = "en"
        rootCollection.document(language).collection("videos").get().await()
            .forEach {
                val category: String? = it.getString("category")
                val id: String? = it.getString("id")
                val title: String? = it.getString("title")
                val video = Video(category!!, id!!, title!!)
                dataList.add(video)
            }
        mutableLiveData.value = dataList
        return mutableLiveData
    }

    suspend fun fetchInformation(isEnglish: Boolean): MutableLiveData<MutableList<Information>> {
        val mutableLiveData = MutableLiveData<MutableList<Information>>()
        val dataList: MutableList<Information> = mutableListOf()
        if (isEnglish) language = "en"
        rootCollection.document(language).collection("information").get().await()
            .forEach {
                val title: String = it.getString("title") ?: it.id
                val brief: String? = it.getString("brief")
                val image: String = it.getString("imageF") ?: defaultImg
                val text: ArrayList<String> = it.get("text") as ArrayList<String>
                val info = Information(title, it.id, brief!!, image, text)
                dataList.add(info)
            }
        mutableLiveData.value = dataList
        return mutableLiveData
    }

    fun sendFeedback(msg: String) {
        rootCollection.document("feedback").collection("messages")
            .add(mapOf("msg" to msg))
            .addOnCompleteListener { println("DONE") }
            .addOnFailureListener { println(it.message) }
    }
}