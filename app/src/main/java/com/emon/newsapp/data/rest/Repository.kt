package com.emon.newsapp.data.rest

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.emon.newsapp.data.model.Article
import com.emon.newsapp.data.network.NetworkState
import com.emon.newsapp.data.NewsDataSource
import com.emon.newsapp.data.NewsDataSourceFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable

class Repository {

    lateinit var newsDataSourceFactory: NewsDataSourceFactory
    var apiService :ApiService = RetrofitClientInstance.retrofitInstance!!.create(ApiService::class.java)

    fun fetchLiveNewsPagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Article>> {

        newsDataSourceFactory= NewsDataSourceFactory(apiService,compositeDisposable)

        val config:PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE)
            .build()

        var newsPagedList= LivePagedListBuilder(newsDataSourceFactory,config).build()

        return newsPagedList

    }

    fun getNetworkState():LiveData<NetworkState>{
        return Transformations.switchMap(newsDataSourceFactory.newsLiveDataSource,NewsDataSource::networkState)
    }
}