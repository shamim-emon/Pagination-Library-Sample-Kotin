package com.emon.newsapp.data.network


class NetworkState (val status: Status, val message:String){

    companion object{
        var LOADED : NetworkState =
            NetworkState(
                Status.SUCCESS,
                "Success"
            )
        var LOADING : NetworkState =
            NetworkState(
                Status.RUNNING,
                "Loading"
            )
        var ERROR : NetworkState =
            NetworkState(
                Status.FAILED,
                "Error"
            )
        var EOF : NetworkState =
            NetworkState(
                Status.ENDOFLIST,
                "No more data to load"
            )
    }

}