package com.arctouch.codechallenge.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.interfaces.ActivityBehaviour
import java.util.*

abstract class AbstractActivity : AppCompatActivity, ActivityBehaviour {
    protected val TAG: String

    protected var layoutId: Int = 0
    protected var viewContentId: Int = 0

    private var tryToClose: Date? = null
    private var blockOnBackPress = false

    private var inputManager: InputMethodManager? = null
    protected var progressDialog: ProgressDialog? = null

    /**
     * Message before close the activity
     */
    protected var questionBeforeClose = true

    /**
     * Default constructor, need to pass the layoutId and contentId
     *
     * @param layoutId
     * @param viewContentId
     */
    constructor(layoutId: Int, viewContentId: Int) : super() {
        this.TAG = this.javaClass.simpleName

        this.layoutId = layoutId
        this.viewContentId = viewContentId
    }

    constructor(layoutId: Int) : super() {
        this.TAG = this.javaClass.simpleName

        this.layoutId = layoutId
        this.viewContentId = 0
    }

    override fun backFragment() {
        this.onBackPressed()
    }

    /**
     * Method responsible to onStart the activity and define the behavior of the activity
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(this.layoutId)

        // The Theme's windowBackground is masked by the opaque background of the activity, and the windowBackground causes an
        // uncecessarry overdraw. Nulllfying the windowBackground removes that background
        this.window.setBackgroundDrawable(null)

        this.init(savedInstanceState)
    }

    /**
     * Method responsible to call all init method.
     * If you need to change the sequence/behavior of all init call override this method
     */
    protected fun init(savedInstanceState: Bundle?) {
        this.blockOnBackPress = false

        this.initComponents()
        this.initListeners()
        this.initData()
    }

//    /**
//     * Method onBackPressed was override putting the default behavior to the activity in doesn't
//     * close immediately, given the the opportunity to change their mind
//     */
//    override fun onBackPressed() {
//
//        if (this.blockOnBackPress) {
//            return
//        }
//
//        if (!questionBeforeClose) {
//            super.onBackPressed()
//            return
//        }
//
//        if (this.hasBackStack()) {
//            super.onBackPressed()
//            return
//        }
//
//        if (this.tryToClose != null) {
//            val tryToCloseAgain = Date()
//            val diff = tryToCloseAgain.time - tryToClose!!.time
//            val diffSec = diff / 1000
//
//            if (diffSec > 1) {
//                this.tryToClose = null
//            } else {
//                super.onBackPressed()
//                return
//            }
//        }
//
//        this.tryToClose = Date()
//        Toast.makeText(this, getString(R.string.message_close_app), Toast.LENGTH_SHORT).show()
//    }

    protected abstract fun initComponents()

    protected abstract fun initData()

    protected abstract fun initListeners()

    override fun switchContent(fragment: Fragment, addBackStack: Boolean) {
        this.switchContent(fragment, this.viewContentId, addBackStack)
    }

    override fun switchContent(fragment: Fragment, idRes: Int, addToBackStack: Boolean) {
        val transaction = this.supportFragmentManager.beginTransaction()
        val tag = fragment.javaClass.canonicalName
        transaction.replace(idRes, fragment, tag)
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
    }

    override fun clearFragmentStack() {
        while (this.hasBackStack()) {
            this.supportFragmentManager.popBackStackImmediate()
        }
    }

    override fun activityEffectTransition(enterAnim: Int, exitAnim: Int) {
        this.overridePendingTransition(enterAnim, exitAnim)
    }

    protected fun hasBackStack(): Boolean {
        return this.supportFragmentManager.backStackEntryCount > 0
    }

    override fun hideKeyboard() {
        if (this.inputManager == null) {
            this.inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        }

        if (this.currentFocus != null && this.currentFocus!!.windowToken != null) {
            this.inputManager!!.hideSoftInputFromWindow(
                    this.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    override fun showKeyboard() {
        if (this.inputManager == null) {
            this.inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        }

        if (this.currentFocus != null && this.currentFocus!!.windowToken != null) {
            this.inputManager!!.showSoftInputFromInputMethod(
                    this.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    protected abstract fun injectComponents()

    override fun showProgressBar() {
        if (progressDialog == null || !progressDialog!!.isShowing) {
            val dialog = ProgressDialog(this)

            dialog.setMessage(getString(R.string.label_please_wait))
            dialog.isIndeterminate = true
            dialog.setCancelable(false)
            dialog.show()

            progressDialog = dialog
        }
    }

    override fun hideProgressBar() {
        if (progressDialog != null) {
            progressDialog!!.dismiss()
            progressDialog = null
        }
    }

    override fun setTitle(@StringRes title: Int) {
        actionBar?.setTitle(title)
        supportActionBar?.setTitle(title)
    }
}

