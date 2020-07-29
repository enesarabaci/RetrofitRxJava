package com.enesarabaci.retrofitrxjava.service

import com.enesarabaci.retrofitrxjava.model.Model
import io.reactivex.Single
import retrofit2.http.GET

interface Service {

    @GET("v2/launches")
    fun getData() : Single<List<Model>>

}