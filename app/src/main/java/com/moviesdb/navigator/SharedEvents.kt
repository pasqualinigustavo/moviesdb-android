package com.moviesdb.navigator

import androidx.lifecycle.MutableLiveData
import com.moviesdb.util.SingleLiveEvent
import java.io.Serializable

class SharedEvents {
    val navigationEvent = MutableLiveData<Navigator.NavigationEventData>()
    val navigationResultEvent = SingleLiveEvent<Serializable>()
}