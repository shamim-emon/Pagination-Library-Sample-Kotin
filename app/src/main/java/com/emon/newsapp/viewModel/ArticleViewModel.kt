package com.emon.newsapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.emon.newsapp.data.model.Article
import com.emon.newsapp.data.network.NetworkState
import com.emon.newsapp.data.rest.Repository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ArticleViewModel :ViewModel() {

    private var compositeDisposable :CompositeDisposable = CompositeDisposable()
    private var repository :Repository  = Repository()

    var newsPagedList : LiveData<PagedList<Article>> = repository.fetchLiveNewsPagedList(compositeDisposable)
    var networkState : LiveData<NetworkState> =  repository.getNetworkState()


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }




}