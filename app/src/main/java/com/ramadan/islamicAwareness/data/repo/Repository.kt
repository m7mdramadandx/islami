package com.ramadan.islamicAwareness.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.ramadan.islamicAwareness.data.model.Category
import com.ramadan.islamicAwareness.data.model.Prophet
import com.ramadan.islamicAwareness.data.model.Quote

class Repository {
    private val arabicCollection = FirebaseFirestore.getInstance().collection("ar")
    private val englishCollection = FirebaseFirestore.getInstance().collection("en")

    fun fetchAllStories(isEnglish: Boolean): LiveData<MutableList<Prophet>> {
        val mutableData = MutableLiveData<MutableList<Prophet>>()
        var collection = arabicCollection
        if (isEnglish) collection = englishCollection
        collection.document("stories").collection("stories").get()
            .addOnSuccessListener { result ->
                val dataList: MutableList<Prophet> = mutableListOf()
                for (document: QueryDocumentSnapshot in result) {
                    val imgUrl: String? = document.getString("imgUrl")
                    val text: ArrayList<String>? = document.get("text") as ArrayList<String>?
                    val prophet = Prophet(name = document.id, imgUrl = imgUrl!!, text = text!!)
                    dataList.add(prophet)
                }
                mutableData.value = dataList
            }.addOnFailureListener { e -> println("Error!! + $e") }
        return mutableData
    }

    fun fetchStory(isEnglish: Boolean, prophetName: String): Prophet? {
        var prophet: Prophet? = null
        var collection = arabicCollection
        if (isEnglish) collection = englishCollection
        collection.document("stories").collection("stories").get()
            .addOnSuccessListener { result ->
                for (document: QueryDocumentSnapshot in result) {
                    if (prophetName == document.id) {
                        val imgUrl: String? = document.getString("imgUrl")
                        val text: ArrayList<String>? = document.get("text") as ArrayList<String>?
                        prophet = Prophet(name = document.id, imgUrl = imgUrl!!, text = text!!)
                    }
                }
            }.addOnFailureListener { e -> println("Error!! + $e") }
        return prophet
    }

    fun fetchCategory(isEnglish: Boolean): LiveData<MutableList<Category>> {
        val mutableData = MutableLiveData<MutableList<Category>>()
        FirebaseFirestore.getInstance().collection("hadith").get()
            .addOnSuccessListener { result ->
                val dataList: MutableList<Category> = mutableListOf()
                for (document: QueryDocumentSnapshot in result) {
                    val name: String? = document.getString("category")
                    val imgUrl: String? = document.getString("imgUrl")
                    val category = Category(name!!, imgUrl!!)
                    dataList.add(category)
                }
                mutableData.value = dataList
            }.addOnFailureListener { e -> println("Error!! + $e") }

        return mutableData
    }

    fun getQuote(category: String): LiveData<MutableList<Quote>> {
        var string: String = category
        if (category == "Ramadan") {
            string = "prophetMuhammad"
        }
        val mutableData = MutableLiveData<MutableList<Quote>>()
        FirebaseFirestore.getInstance().collection("hadith").document(string)
            .collection(category).get().addOnSuccessListener {
                val dataList: MutableList<Quote> = mutableListOf()
                for (document: QueryDocumentSnapshot in it) {
                    val imgUrl: String? = document.getString("imgUrl")
                    val quote = Quote(imgUrl!!)
                    dataList.add(quote)
                }
                mutableData.value = dataList
            }.addOnFailureListener { e -> println("Error!!$e") }
        return mutableData
    }

}