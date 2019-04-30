package com.arctouch.codechallenge.base.interfaces

import android.support.v7.widget.RecyclerView

interface IAdapterDataSource {
    fun adapterUserWantsLoadMoreData(apadter: RecyclerView.Adapter<*>?)
}
