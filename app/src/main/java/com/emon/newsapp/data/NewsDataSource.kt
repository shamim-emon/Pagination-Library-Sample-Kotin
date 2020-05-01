package com.emon.newsapp.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.emon.newsapp.data.model.Article
import com.emon.newsapp.data.network.NetworkState
import com.emon.newsapp.data.rest.ApiService
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class NewsDataSource (private var apiService: ApiService,private var compositeDisposable: CompositeDisposable):PageKeyedDataSource<Int, Article>(){

    companion object {
        //the size of a page that we want
        private const val PAGE_SIZE = 10

        //we will start from the first page which is 1
        private const val FIRST_PAGE = 1

    }
    var networkState : MutableLiveData<NetworkState> = MutableLiveData()



    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getNewsHeading(FIRST_PAGE.toString(), PAGE_SIZE.toString())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {

                        networkState.postValue(NetworkState.LOADED)
                        callback.onResult(it.articles,null, FIRST_PAGE+1)

                    },
                    {

                        Log.i("ERROR",it.localizedMessage)
                        networkState.postValue(NetworkState.ERROR)
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getNewsHeading(params.key.toString(), PAGE_SIZE.toString())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {

                        if(it.status !="error"){
                            networkState.postValue(NetworkState.LOADED)
                            callback.onResult(it.articles, params.key+1)
                        }
                        else{
                            networkState.postValue(NetworkState.EOF)
                        }

                    },
                    {

                        if(it.localizedMessage.contains("HTTP 426")== true){
                            networkState.postValue(NetworkState.EOF)
                        }
                        else{
                            networkState.postValue(NetworkState.ERROR)
                        }


                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
       //we will do nothing as once data is loaded,we leave it as it is
    }

}