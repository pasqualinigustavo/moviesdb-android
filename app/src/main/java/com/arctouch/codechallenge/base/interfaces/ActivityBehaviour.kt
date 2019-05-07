package com.arctouch.codechallenge.base.interfaces

import androidx.fragment.app.Fragment

interface ActivityBehaviour {

    fun switchContent(fragment: Fragment, addToBackStack: Boolean)

    fun switchContent(fragment: Fragment, idRes: Int, animation: Boolean)

    fun clearFragmentStack()

    fun activityEffectTransition(enterAnim: Int, exitAnim: Int)

    fun hideKeyboard()

    fun showKeyboard()

    fun showProgressBar()

    fun hideProgressBar()

    fun backFragment()

}

