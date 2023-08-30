package com.example.retrofitrxjavakotlincyrptoapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.retrofitrxjavakotlincyrptoapp.R
import com.example.retrofitrxjavakotlincyrptoapp.model.CryptoModel
import com.example.retrofitrxjavakotlincyrptoapp.service.CryptoApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels: ArrayList<CryptoModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()
        //2187154b7695237334aa34f7dc98a
    }

    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CryptoApi::class.java)
        val call = service.getData()

        call.enqueue(object : retrofit2.Callback<List<CryptoModel>> {
            override fun onResponse(
                call: retrofit2.Call<List<CryptoModel>>?,
                response: retrofit2.Response<List<CryptoModel>>?
            ) {
                if (response != null) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            cryptoModels = ArrayList(it)
                            for (cryptoModel: CryptoModel in cryptoModels!!) {
                                println(cryptoModel.currency)
                                println(cryptoModel.price)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<List<CryptoModel>>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }
}