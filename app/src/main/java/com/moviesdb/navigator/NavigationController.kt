package com.moviesdb.navigator

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import java.io.Serializable

class NavigationController(
        private val navController: NavController,
        private val context: Context
) {

    companion object {
        const val DATA_KEY = "data"
    }

    val currentId: Int?
        get() = navController.currentDestination?.id

    fun navigate(@IdRes resId: Int, args: Serializable? = null) {
        val bundle = Bundle()
        bundle.putSerializable(DATA_KEY, args)
        navController.navigate(resId, bundle)
    }

    fun finish(): Boolean {
        return navController.popBackStack()
    }
}
