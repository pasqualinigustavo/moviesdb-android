@file:JvmName("UpcomingMoviesFragment")

package com.moviesdb.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviesdb.R
import com.moviesdb.model.Movie
import com.moviesdb.ui.BaseFragment
import com.moviesdb.ui.movies.di.DaggerUpcomingMoviesComponent
import com.moviesdb.ui.movies.di.UpcomingMoviesModule
import kotlinx.android.synthetic.main.fragment_upcoming_movies.*
import javax.inject.Inject

/**
 * @author Gustavo Pasqualini
 */

class UpcomingMoviesFragment : BaseFragment(), UpcomingMoviesView {

    companion object {
        val TAG = UpcomingMoviesFragment::class.java.simpleName
    }

    @Inject
    lateinit var presenter: UpcomingMoviesPresenter
    private var currentDataSetPage = 1L
    private var overallYScroll = 0
    //var adapter: ContractAdapter? = null

    override fun injectComponents() {
        DaggerUpcomingMoviesComponent.builder()
                .homeComponent(getMainComponent())
                .upcomingMoviesModule(UpcomingMoviesModule())
                .build()
                .inject(this)
    }

    override fun initData() {
        currentDataSetPage = 1
        presenter.getUpcomingMovies(currentDataSetPage)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_upcoming_movies, container, false)
    }

    override fun initListeners() {
        fragment_upcoming_movies__swiperefresh?.setOnRefreshListener {
            currentDataSetPage = 1
            presenter.getUpcomingMovies(currentDataSetPage)
        }

        fragment_upcoming_movies__recyclerview.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                overallYScroll = lastVisibleItem(recyclerView)
            }
        })
    }

    private fun lastVisibleItem(recyclerView: RecyclerView?): Int {
        var lastItemVisiblePos = 0
        if (recyclerView != null && recyclerView.adapter != null) {
            if (recyclerView.adapter?.itemCount != 0) {
                lastItemVisiblePos =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

            }
        }
        return lastItemVisiblePos
    }

    override fun showLoadingView(show: Boolean) {
        if (getContext() == null) return
        if (fragment_upcoming_movies__swiperefresh == null) return

        fragment_upcoming_movies__swiperefresh.post({ fragment_upcoming_movies__swiperefresh.setRefreshing(show) })
    }

    override fun initComponent(view: View?, savedInstanceState: Bundle?) {
        Log.d(TAG, "initComponents")
        presenter.bindView(this)
        setHasOptionsMenu(false)
        setToolbarTitle(getString(R.string.fragment_upcoming_movies__lbl_title))

        fragment_upcoming_movies__recyclerview.setHasFixedSize(false)
        fragment_upcoming_movies__recyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val dividerItemDecoration =
                DividerItemDecoration(fragment_upcoming_movies__recyclerview.getContext(), LinearLayoutManager.VERTICAL)
        fragment_upcoming_movies__recyclerview.addItemDecoration(dividerItemDecoration)
    }

    override fun showUpcomingMovies(movies: List<Movie>?) {
        initAdapter(movies)
    }

    private fun initAdapter(movies: List<Movie>?) {
//        if (context == null) return
//        if (fragment_upcoming_movies__swiperefresh == null || fragment_upcoming_movies__swiperefresh == null)
//            return
//        if (contracts != null && !contracts.isEmpty()) {
//            if (currentDataSetPage == 1 || adapter == null) {
//                adapter = ContractAdapter()
//                adapter?.items = contracts.toMutableList()
//                adapter?.clickEvent?.subscribe{
//                        if (it != null)
//                            presenter.showContractDetails(it)
//                    }
//
//                fragment_upcoming_movies.adapter = adapter
//                fragment_upcoming_movies__swiperefresh.visibility = View.VISIBLE
//                fragment_contracts__textview_noinfo.visibility = View.GONE
//                if (contracts.size < VeloConstants.LIST_ITEM_ITEMS_PER_PAGE)
//                    adapter?.setHasMoreData(false)
//            } else {
//                if (contracts == null || contracts.size == 0)
//                    adapter?.setHasMoreData(false)
//                else {
//                    overallYScroll = contracts?.size
//                    if (contracts.size < VeloConstants.LIST_ITEM_ITEMS_PER_PAGE)
//                        adapter?.setHasMoreData(false)
//                    adapter?.addMoreItemsToDateSet(contracts.toMutableList())
//                }
//            }
//        } else {
//            adapter = null
//            fragment_upcoming_movies__swiperefresh.visibility = View.GONE
//            fragment_contracts__textview_noinfo.visibility = View.VISIBLE
//        }
    }

    override fun showError(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }
}