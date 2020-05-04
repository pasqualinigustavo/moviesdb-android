@file:JvmName("UpcomingMoviesFragment")

package com.moviesdb.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviesdb.R
import com.moviesdb.di.Injectable
import com.moviesdb.ui.BaseFragmentNew
import com.moviesdb.ui.movies.adapter.MovieAdapter
import kotlinx.android.synthetic.main.fragment_upcoming_movies.*

/**
 * @author Gustavo Pasqualini
 */

class UpcomingMoviesFragment : BaseFragmentNew<UpcomingMoviesViewModel>(), Injectable {

    companion object {
        val TAG = UpcomingMoviesFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_upcoming_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        fragment_upcoming_movies__recyclerview.setHasFixedSize(false)
        fragment_upcoming_movies__recyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val dividerItemDecoration =
                DividerItemDecoration(fragment_upcoming_movies__recyclerview.getContext(), LinearLayoutManager.VERTICAL)
        fragment_upcoming_movies__recyclerview.addItemDecoration(dividerItemDecoration)

        context?.let { fragment_upcoming_movies__recyclerview.adapter = MovieAdapter(it) }

//        fragment_upcoming_movies__recyclerview.adapter = MovieAdapter { item ->
//            viewModel.onMessageClicked(
//                    message = item,
//                    successEvent = { isSuccess ->
//                        if (isSuccess) {
//                            (message_list.adapter as? MessageAdapter)?.updateToReadMessageWithoutViewUpdate(
//                                    item
//                            )
//                        }
//                    }
//            )
//        }
//
//        if (viewModel.messagesList.isNotEmpty())
//            (fragment_upcoming_movies__recyclerview.adapter as? MovieAdapter)?.setup(viewModel.messagesList)
//
//        context?.let {
//            ContextCompat.getColor(
//                    it,
//                    R.color.utilita_page
//            )
//        }?.let {
//            swipe_refresh_list.setProgressBackgroundColorSchemeColor(it)
//        }
    }

    private fun setupObservers() {
//        viewModel.showError.observe(this,
//                Observer {
//                    message_error.show(true)
//                    message_list.show(false)
//                    empty_inbox_container.show(false)
//                }
//        )
//
        viewModel.onMessagesData.value = emptyList()
        viewModel.onMessagesData.observe(
                viewLifecycleOwner,
                Observer {
                    (fragment_upcoming_movies__recyclerview.adapter as? MovieAdapter)?.addElements(it)
                    fragment_upcoming_movies__recyclerview.visibility = View.VISIBLE
//                    empty_inbox_container.show(false)
//                    message_error.show(false)
                }
        )

//        viewModel.emptyStateEvent.observe(
//                viewLifecycleOwner,
//                Observer {
//                    empty_inbox_container.show(true)
//                    message_list.show(false)
//                    message_error.show(false)
//                }
//        )
//
//        viewModel.loading.observe(
//                viewLifecycleOwner,
//                Observer {
//                    loading_card?.show(it)
//                }
//        )
    }

    override fun initComponent(view: View?, savedInstanceState: Bundle?) {
        Log.d(TAG, "initComponents")
        setHasOptionsMenu(false)
        setToolbarTitle(getString(R.string.fragment_upcoming_movies__lbl_title))
    }

    override fun createViewModel(): UpcomingMoviesViewModel {
        return ViewModelProvider(this, viewModelFactory)
                .get(UpcomingMoviesViewModel::class.java)
    }
}