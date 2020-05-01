package com.emon.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.emon.newsapp.R
import com.emon.newsapp.data.network.NetworkState
import com.emon.newsapp.databinding.ActivityMainBinding
import com.emon.newsapp.viewModel.ArticleViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var model: ArticleViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SummaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        model = ViewModelProviders.of(this).get(ArticleViewModel::class.java)

        binding.list.layoutManager= LinearLayoutManager(this)

        adapter= SummaryAdapter()


        model.newsPagedList.observe(this, Observer {
            adapter.submitList(it)
        })

        model.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
            when(it){
                NetworkState.ERROR->{
                    Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
                }

                NetworkState.EOF->{
                    Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
                }
            }
        })

        binding.list.adapter=adapter
       // binding.list.recycledViewPool.setMaxRecycledViews(0, 0)


    }
}
