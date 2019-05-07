package com.arctouch.codechallenge.base.interfaces

import androidx.recyclerview.widget.RecyclerView

interface IAdapterDataSource {
    fun adapterUserWantsLoadMoreData(apadter: RecyclerView.Adapter<*>?)
}
