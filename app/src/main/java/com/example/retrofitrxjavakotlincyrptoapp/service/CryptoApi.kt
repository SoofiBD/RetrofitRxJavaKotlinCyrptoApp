package com.example.retrofitrxjavakotlincyrptoapp.service

import retrofit2.Call
import com.example.retrofitrxjavakotlincyrptoapp.model.CryptoModel
import retrofit2.http.GET

interface CryptoApi {

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    //@GET("prices?key=2187154b7695237334aa34f7dc98a")
    fun getData(): Call<List<CryptoModel>>

}
