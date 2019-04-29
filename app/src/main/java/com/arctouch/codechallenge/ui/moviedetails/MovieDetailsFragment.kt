@file:JvmName("MovieDetailsFragment")

package com.arctouch.codechallenge.ui.moviedetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.BaseFragment
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.ui.moviedetails.di.DaggerMovieDetailsComponent
import com.arctouch.codechallenge.ui.moviedetails.di.MovieDetailsModule
import javax.inject.Inject

/**
 * @author Gustavo Pasqualini
 */

class MovieDetailsFragment : BaseFragment(), MovieDetailsView {

    companion object {
        val TAG = MovieDetailsFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(movie: Movie): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            fragment.movie = movie
            return fragment
        }
    }

    @Inject
    lateinit var presenter: MovieDetailsPresenter
    lateinit var movie: Movie

    override fun injectComponents() {
        DaggerMovieDetailsComponent.builder()
                .mainComponent(getMainComponent())
                .movieDetailsModule(MovieDetailsModule())
                .build()
                .inject(this)
    }

    override fun initData() {
//        presenter.getMovies(1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun initListeners() {

    }

    override fun initComponent(view: View?, savedInstanceState: Bundle?) {
        Log.d(TAG, "initComponents")
        presenter.attachView(this)
        setHasOptionsMenu(false)
        setToolbarTitle(movie?.title)
    }

    override fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}