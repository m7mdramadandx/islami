package com.ramadan.islamicAwareness.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.ramadan.islamicAwareness.data.model.Category
import com.ramadan.islamicAwareness.data.model.Prophet
import com.ramadan.islamicAwareness.data.model.Quote

class Repo {

    fun fetchStory(): LiveData<MutableList<Prophet>> {
        val mutableData = MutableLiveData<MutableList<Prophet>>()
        FirebaseFirestore.getInstance().collection("prophet").get()
            .addOnSuccessListener { result ->
                val dataList: MutableList<Prophet> = mutableListOf<Prophet>()
                for (document: QueryDocumentSnapshot in result) {
                    val name: String? = document.getString("name")
                    val imgUrl: String? = document.getString("imgUrl")
                    val section1: String? = document.getString("section1")
                    val section2: String? = document.getString("section2")
                    val section3: String? = document.getString("section3")
                    val section4: String? = document.getString("section4")
                    val section5: String? = document.getString("section5")
                    val section6: String? = document.getString("section6")
                    val section7: String? = document.getString("section7")
                    val prophet = Prophet(
                        name!!, imgUrl!!, section1!!, section2!!,
                        section3!!, section4!!, section5!!, section6!!, section7!!
                    )
                    dataList.add(prophet)
                }
                mutableData.value = dataList
            }.addOnFailureListener { e -> println("Error!! + $e") }

        return mutableData
    }

    fun fetchCategory(): LiveData<MutableList<Category>> {
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