package com.moviesdb.model.interfaces

import androidx.recyclerview.widget.RecyclerView

interface IAdapterDataSource {
    fun adapterUserWantsLoadMoreData(apadter: RecyclerView.Adapter<*>?)
}
