@file:JvmName("UpcomingMoviesFragment")

package com.moviesdb.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviesdb.R
import com.moviesdb.di.Injectable
import com.moviesdb.model.MoviesDBConstants
import com.moviesdb.model.interfaces.IAdapterDataSource
import com.moviesdb.ui.BaseFragment
import com.moviesdb.ui.movies.adapter.MovieAdapter
import kotlinx.android.synthetic.main.fragment_upcoming_movies.*

/**
 * @author Gustavo Pasqualini
 */

class UpcomingMoviesFragment : BaseFragment<UpcomingMoviesViewModel>(), Injectable {

    companion object {
        val TAG = UpcomingMoviesFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_upcoming_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)
        setToolbarTitle(getString(R.string.fragment_upcoming_movies__lbl_title))

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        fragment_upcoming_movies__recyclerview.setHasFixedSize(false)
        fragment_upcoming_movies__recyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val dividerItemDecoration =
                DividerItemDecoration(fragment_upcoming_movies__recyclerview.getContext(), LinearLayoutManager.VERTICAL)
        fragment_upcoming_movies__recyclerview.addItemDecoration(dividerItemDecoration)

        context?.let { fragment_upcoming_movies__recyclerview.adapter = MovieAdapter(it) }

        (fragment_upcoming_movies__recyclerview.adapter as? MovieAdapter)?.clickEvent?.subscribe {
            viewModel.onMovieClicked(it)
        }

        (fragment_upcoming_movies__recyclerview.adapter as? MovieAdapter)?.setAdapterInteractionListener(object : IAdapterDataSource {
            override fun adapterUserWantsLoadMoreData(apadter: RecyclerView.Adapter<*>?) {
                viewModel.bumpPage()
                viewModel.getItemsFromServer()
            }
        })

        context?.let {
            ContextCompat.getColor(
                    it,
                    R.color.colorPrimary
            )
        }?.let {
            fragment_upcoming_movies__swiperefresh.setProgressBackgroundColorSchemeColor(it)
        }

        context?.let {
            ContextCompat.getColor(
                    it,
                    R.color.colorAccent
            )
        }?.let {
            fragment_upcoming_movies__swiperefresh.setColorSchemeColors(it)
        }
    }

    override fun setupObservers() {
        viewModel.onItemsData.value = emptyList()
        viewModel.onItemsData.observe(
                viewLifecycleOwner,
                Observer {
                    (fragment_upcoming_movies__recyclerview.adapter as? MovieAdapter)?.addElements(it)
                    fragment_upcoming_movies__recyclerview.visibility = View.VISIBLE
                    fragment_upcoming_movies__textview_nodata.visibility = View.GONE
                    if (it.size < MoviesDBConstants.LIST_ITEM_ITEMS_PER_PAGE)
                        (fragment_upcoming_movies__recyclerview.adapter as? MovieAdapter)?.setHasMoreData(false)
                    else (fragment_upcoming_movies__recyclerview.adapter as? MovieAdapter)?.setHasMoreData(true)
                }
        )

        viewModel.emptyStateEvent.observe(
                viewLifecycleOwner,
                Observer {
                    fragment_upcoming_movies__textview_nodata.visibility = View.VISIBLE
                }
        )

        viewModel.loading.observe(
                viewLifecycleOwner,
                Observer {
                    fragment_upcoming_movies__swiperefresh.isRefreshing = it
                }
        )

        viewModel.showError.observe(this,
                Observer {
                    Toast.makeText(context, "Não foi possível buscar dados", Toast.LENGTH_LONG).show()
                    fragment_upcoming_movies__textview_nodata.visibility = View.VISIBLE
                }
        )
    }

    override fun createViewModel(): UpcomingMoviesViewModel {
        return ViewModelProvider(this, viewModelFactory)
                .get(UpcomingMoviesViewModel::class.java)
    }
}