package com.enesarabaci.retrofitrxjava.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enesarabaci.retrofitrxjava.R
import com.enesarabaci.retrofitrxjava.adapter.RecyclerViewAdapter
import com.enesarabaci.retrofitrxjava.model.Model
import com.enesarabaci.retrofitrxjava.service.Service
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    var models : ArrayList<Model>? = null
    var compositeDisposable : CompositeDisposable? = null
    var recyclerView : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        compositeDisposable = CompositeDisposable()
        loadData()

    }

    fun loadData() {
        val BASE_URL = "https://api.spacexdata.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(Service::class.java)

        compositeDisposable?.add(retrofit.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Model>>() {
                    override fun onSuccess(t: List<Model>) {
                        models = ArrayList(t)
                        recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity)
                        var recyclerViewAdapter = RecyclerViewAdapter(models!!)
                        recyclerView!!.adapter = recyclerViewAdapter
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )


        /*

        val service = retrofit.create(Service::class.java)
        val call = service.getData()

        call.enqueue(object : Callback<List<Model>> {
            override fun onFailure(call: Call<List<Model>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<Model>>, response: Response<List<Model>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        models = ArrayList(it)

                        for (model : Model in models!!) {
                            println(model.mission + " - " + model.flight)
                        }
                    }
                }
            }

        })

         */

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }

}