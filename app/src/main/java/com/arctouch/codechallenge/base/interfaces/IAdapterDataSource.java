package com.arctouch.codechallenge.base.interfaces;

import android.support.v7.widget.RecyclerView;

public interface IAdapterDataSource {
    void adapterUserWantsLoadMoreData(RecyclerView.Adapter apadter);
}
