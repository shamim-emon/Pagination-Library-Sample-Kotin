package com.emon.newsapp.data.rest

import com.emon.newsapp.data.model.ApiResponse
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{
    @GET("everything?q=bitcoin&apiKey=1e6c865701ab42b5b074eec1994d1990")
    fun getNewsHeading(@Query("page")page:String,@Query("pageSize")pageSize:String): Flowable<ApiResponse>
}