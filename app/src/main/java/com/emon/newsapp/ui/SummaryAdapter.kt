package com.emon.newsapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.emon.newsapp.R
import com.emon.newsapp.data.model.Article
import com.emon.newsapp.data.network.NetworkState
import com.emon.newsapp.databinding.LayoutSummaryBinding
import com.emon.newsapp.databinding.LayoutWaitViewBinding


class SummaryAdapter() :
    PagedListAdapter<Article, SummaryAdapter.SummaryViewHolder>(DIFF_CALLBACK) {

    val CELL_WAIT_VIEW = 0
    val CELL_DATA_VIEW = 1
    var mNetworkState: NetworkState? = null

    fun setNetworkState(networkState: NetworkState?) {
        mNetworkState = networkState
        if (mNetworkState == NetworkState.EOF) {
            notifyItemRemoved(getItemCount())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {

        var binding: ViewBinding? = null
        var viewHolder: SummaryViewHolder

        if (viewType == CELL_DATA_VIEW) {
            binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_summary,
                parent,
                false
            )
            viewHolder = SummaryViewHolder(binding as LayoutSummaryBinding)
        } else {
            binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_wait_view,
                parent,
                false
            )
            viewHolder = SummaryViewHolder(binding as LayoutWaitViewBinding)
        }

        return viewHolder
    }

    override fun getItemViewType(position: Int): Int {
        if (hasExtraRow() && position == itemCount - 1)
            return CELL_WAIT_VIEW
        return CELL_DATA_VIEW
    }

    private fun hasExtraRow(): Boolean {
        return if (mNetworkState != null && mNetworkState == NetworkState.LOADING) {
            true
        } else {
            false
        }
    }


    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {

        holder.contentBinding?.article = getItem(position)
    }


    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Article> =
            object : DiffUtil.ItemCallback<Article>() {
                override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                    return oldItem.url == newItem.url
                }

                override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                    return oldItem == newItem
                }
            }
    }


    class SummaryViewHolder : RecyclerView.ViewHolder {

        var contentBinding: LayoutSummaryBinding? = null

        constructor(binding: LayoutSummaryBinding) : super(binding.root) {
            contentBinding = binding
        }

        var waitBinding: LayoutWaitViewBinding? = null

        constructor(binding: LayoutWaitViewBinding) : super(binding.root) {
            waitBinding = binding
        }

    }
}