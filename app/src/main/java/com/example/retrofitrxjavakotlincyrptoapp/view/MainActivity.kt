package com.example.retrofitrxjavakotlincyrptoapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitrxjavakotlincyrptoapp.R
import com.example.retrofitrxjavakotlincyrptoapp.adapter.RecyclerViewAdapter
import com.example.retrofitrxjavakotlincyrptoapp.model.CryptoModel
import com.example.retrofitrxjavakotlincyrptoapp.service.CryptoApi
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() , RecyclerViewAdapter.Listener {

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager : RecyclerView.LayoutManager= LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.recyclerView).layoutManager = layoutManager

        compositeDisposable = CompositeDisposable()
        loadData()
        //2187154b7695237334aa34f7dc98a
    }

    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoApi::class.java)

        compositeDisposable?.add(retrofit.getData()).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse)
        //val service = retrofit.create(CryptoApi::class.java)
        //val call = service.getData()

/*
        call.enqueue(object : retrofit2.Callback<List<CryptoModel>> {
            override fun onResponse(
                call: retrofit2.Call<List<CryptoModel>>?,
                response: retrofit2.Response<List<CryptoModel>>?
            ) {
                if (response != null) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            cryptoModels = ArrayList(it)

                            recyclerViewAdapter = RecyclerViewAdapter(cryptoModels!!, this@MainActivity)
                            findViewById<RecyclerView>(R.id.recyclerView).adapter = recyclerViewAdapter

                            for (cryptoModel: CryptoModel in cryptoModels!!) {
                                println(cryptoModel.currency)
                                println(cryptoModel.price)

                        }
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<List<CryptoModel>>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })
    */

    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        println(cryptoModel.currency)
        println(cryptoModel.price)
    }

    private fun handleResponse(cryptoList: List<CryptoModel>) {
        cryptoModels = ArrayList(cryptoList)

        cryptoModels?.let {
            recyclerViewAdapter = RecyclerViewAdapter(it, this@MainActivity)
            findViewById<RecyclerView>(R.id.recyclerView).adapter = recyclerViewAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }


}