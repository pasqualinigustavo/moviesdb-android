@file:JvmName("MoviesFragment")

package com.arctouch.codechallenge.ui.movies

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.BaseFragment
import com.arctouch.codechallenge.base.interfaces.IAdapterDataSource
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.ui.movies.adapter.MovieAdapter
import com.arctouch.codechallenge.ui.movies.di.DaggerMoviesComponent
import com.arctouch.codechallenge.ui.movies.di.MoviesModule
import com.arctouch.codechallenge.util.ArchtouchConstants
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

/**
 * @author Gustavo Pasqualini
 */

class MoviesFragment : BaseFragment(), MoviesView, IAdapterDataSource {

    companion object {
        val TAG = MoviesFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): MoviesFragment {
            val fragment = MoviesFragment()
            return fragment
        }
    }

    @Inject
    lateinit var presenter: MoviesPresenter
    private var currentDataSetPage = 1
    private var overallYScroll = 0
    var adapter: MovieAdapter? = null

    override fun injectComponents() {
        DaggerMoviesComponent.builder()
                .mainComponent(getMainComponent())
                .moviesModule(MoviesModule())
                .build()
                .inject(this)
    }

    override fun initData() {
        currentDataSetPage = 1
        presenter.getMovies(currentDataSetPage)
        presenter.getGenres()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun initListeners() {
        fragment_movies__swiperefresh?.setOnRefreshListener {
            currentDataSetPage = 1
            presenter.getMovies(currentDataSetPage)
        }

        fragment_movies__recyclerview.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                overallYScroll = lastVisibleItem(recyclerView)
            }
        })
    }

    private fun lastVisibleItem(recyclerView: RecyclerView?): Int {
        var lastItemVisiblePos = 0
        if (recyclerView != null && recyclerView.adapter != null) {
            if (recyclerView.adapter!!.itemCount != 0) {
                lastItemVisiblePos = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

            }
        }
        return lastItemVisiblePos
    }

    override fun showLoadingView(show: Boolean) {
        if (getContext() == null) return
        if (fragment_movies__swiperefresh == null) return

        fragment_movies__swiperefresh.post({ fragment_movies__swiperefresh.setRefreshing(show) })
    }

    override fun initComponent(view: View?, savedInstanceState: Bundle?) {
        Log.d(TAG, "initComponents")
        presenter.attachView(this)
        setHasOptionsMenu(false)
        setToolbarTitle(getString(R.string.fragment_movies_lbl_title))

        fragment_movies__recyclerview.setHasFixedSize(false)
        fragment_movies__recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val dividerItemDecoration = DividerItemDecoration(fragment_movies__recyclerview.getContext(), LinearLayoutManager.VERTICAL)
        fragment_movies__recyclerview.addItemDecoration(dividerItemDecoration)
    }

    override fun showMovies(movies: List<Movie>?) {
        initAdapter(movies)
    }

    private fun initAdapter(movies: List<Movie>?) {
        if (context == null) return
        if (fragment_movies__swiperefresh == null || fragment_movies__swiperefresh == null)
            return

        showLoadingView(false)
        if (movies != null) {
            if (currentDataSetPage == 1 || adapter == null) {
                adapter = MovieAdapter(context)
                adapter?.items = movies?.toMutableList()
                adapter?.setAdapterInteractionListener(this)
                adapter?.clickEvent?.subscribe {
                    if (it != null)
                        presenter.showMovieDetails(it)
                }
                fragment_movies__recyclerview.adapter = adapter
                fragment_movies__recyclerview.visibility = View.VISIBLE
                fragment_movies__textview_noinfo.visibility = View.GONE
                if (movies.size < ArchtouchConstants.LIST_ITEM_ITEMS_PER_PAGE)
                    adapter?.setHasMoreData(false)
                if (movies.isEmpty()) {
                    fragment_movies__textview_noinfo.visibility = View.VISIBLE
                    fragment_movies__recyclerview.visibility = View.GONE
                }
            } else {
                if (movies == null || movies.size == 0)
                    adapter?.setHasMoreData(false)
                else {
                    if (movies.size < ArchtouchConstants.LIST_ITEM_ITEMS_PER_PAGE)
                        adapter?.setHasMoreData(false)
                    adapter?.addMoreItemsToDateSet(movies.toMutableList())
                }
                if (currentDataSetPage > 1) {
                    fragment_movies__recyclerview.setAdapter(adapter)
                    fragment_movies__recyclerview.scrollToPosition(overallYScroll)
                }
                if (movies.isEmpty()) {
                    fragment_movies__textview_noinfo.visibility = View.VISIBLE
                    fragment_movies__recyclerview.visibility = View.GONE
                }
            }
        } else {
            fragment_movies__textview_noinfo.visibility = View.VISIBLE
            fragment_movies__recyclerview.visibility = View.GONE
        }
    }

    override fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun adapterUserWantsLoadMoreData(apadter: RecyclerView.Adapter<*>?) {
        currentDataSetPage++
        presenter.getMovies(currentDataSetPage)
    }
}