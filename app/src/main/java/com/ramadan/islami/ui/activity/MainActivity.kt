package com.ramadan.islami.ui.activity

//import com.ramadan.islami.data.model.Qibla
import AuthenticationInterceptor
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ramadan.islami.R
import com.ramadan.islami.data.api.ApiHelper
import com.ramadan.islami.data.api.ApiService
import com.ramadan.islami.data.api.RetrofitBuilder
import com.ramadan.islami.ui.viewModel.MainViewModel
import com.ramadan.islami.ui.viewModel.ViewModelFactory
import com.ramadan.islami.utils.ResStatus
import com.ramadan.islami.utils.debug_tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prayer_times)
        setupViewModel()
        GlobalScope.launch((Dispatchers.Main)) {
            withContext(Dispatchers.IO) {
//                setupUI()
            }
        }
        setupObservers()
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private suspend fun setupUI() {
        val builder = OkHttpClient.Builder()
        builder.interceptors().add(AuthenticationInterceptor())
        val client = builder.build()


        val api = Retrofit.Builder() // Create retrofit builder.
            .baseUrl("https://v6.exchangerate-api.com/") // Base url for the api has to end with a slash.
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client) // Here we set the custom OkHttp client we just created.
            .build()
            .create(ApiService::class.java) // We create an API using the interface we defined.

        val currentFiatCurrencyCode = "EUR"

        // Let's make asynchronous network request to get all latest cryptocurrencies from the server.
        // For query parameter we pass "EUR" as we want to get prices in euros.
//        val call = api.getAllCurrencies("EUR")
//        val result = call.enqueue(object : Callback<Prayer> {
//            override fun onFailure(call: Call<Prayer>, t: Throwable) {
//                Snackbar.make(findViewById(android.R.id.content),
//                    "Call failed! " + t.localizedMessage,
//                    Snackbar.LENGTH_INDEFINITE).show()
//            }
//
//            override fun onResponse(call: Call<Prayer>, response: Response<Prayer>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(applicationContext, "Call OK.", Toast.LENGTH_LONG)
//                        .show();
//                } else Snackbar.make(findViewById(android.R.id.content),
//                    "Call error with HTTP status code " + response.code() + "!",
//                    Snackbar.LENGTH_INDEFINITE).show()
//            }
//        })


//        recyclerView.layoutManager = LinearLayoutManager(this)
//        adapter = MainAdapter(arrayListOf())
//        recyclerView.addItemDecoration(
//            DividerItemDecoration(
//                recyclerView.context,
//                (recyclerView.layoutManager as LinearLayoutManager).orientation
//            )
//        )
//        recyclerView.adapter = adapter
    }


    private fun setupObservers() {
        viewModel.getPrayers().observe(this, {
            it?.let { resource ->
                when (resource.resStatus) {
                    ResStatus.SUCCESS -> {
//                        recyclerView.visibility = View.VISIBLE
//                        progressBar.visibility = View.GONE
                        Toast.makeText(this, "0000", Toast.LENGTH_LONG).show()
                        it.res_data?.observe(this, { it -> Log.e(debug_tag, it.toString()) })
//                        Log.e(debug_tag, it.res_data?.value.toString())
//                        if (it.res_data != null) it.res_data.enqueue(object :
//                            Callback<CurrenciesLatest> {
//                            override fun onFailure(call: Call<CurrenciesLatest>, t: Throwable) {
//                                Snackbar.make(findViewById(android.R.id.content),
//                                    "Call failed! " + t.localizedMessage,
//                                    Snackbar.LENGTH_INDEFINITE).setBackgroundTint(Color.WHITE)
//                                    .show()
//                            }
//
//                            override fun onResponse(
//                                call: Call<CurrenciesLatest>,
//                                response: Response<CurrenciesLatest>,
//                            ) {
//                                if (response.isSuccessful) {
//                                    Toast.makeText(applicationContext,
//                                        "Call OK.",
//                                        Toast.LENGTH_LONG)
//                                        .show();
//                                } else Snackbar.make(findViewById(android.R.id.content),
//                                    "Call error with HTTP status code " + response.code() + "!",
//                                    Snackbar.LENGTH_INDEFINITE).setBackgroundTint(Color.WHITE)
//                                    .show()
//                            }
//                        })
                        println(it.res_data)
///                        resource.data?.let(this::retrieveList)
                    }
                    ResStatus.ERROR -> {
//                        recyclerView.visibility = View.VISIBLE
//                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.res_message, Toast.LENGTH_LONG).show()
                        Log.e(debug_tag, it.res_message.toString())
                    }
                    ResStatus.LOADING -> {
//                        progressBar.visibility = View.VISIBLE
//                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }
//
//    private fun retrieveList(users: Prayers) {
//        println(users.data)
////        println(users[1])
////        adapter.apply {
////            addUsers(users)
////            notifyDataSetChanged()
////    }
//    }
}
