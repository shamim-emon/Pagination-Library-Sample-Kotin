package com.emon.newsapp.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.emon.newsapp.data.model.Article
import com.emon.newsapp.data.rest.ApiService
import io.reactivex.rxjava3.disposables.CompositeDisposable


class NewsDataSourceFactory(
    private var apiService: ApiService,
    private var compositeDisposable: CompositeDisposable
) :
    DataSource.Factory<Int, Article>() {

    var newsLiveDataSource = MutableLiveData<NewsDataSource>()
    lateinit var newsDataSource: NewsDataSource

    override fun create(): DataSource<Int, Article> {
        newsDataSource = NewsDataSource(apiService, compositeDisposable)
        newsLiveDataSource.postValue(newsDataSource)
        return newsDataSource
    }

}