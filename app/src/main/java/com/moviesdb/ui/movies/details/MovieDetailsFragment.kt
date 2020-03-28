@file:JvmName("MovieDetailsFragment")

package com.moviesdb.ui.movies.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.moviesdb.R
import com.moviesdb.model.Movie
import com.moviesdb.ui.BaseFragment
import com.moviesdb.ui.movies.details.di.DaggerMovieDetailsComponent
import com.moviesdb.ui.movies.details.di.MovieDetailsModule
import javax.inject.Inject


/**
 * @author Gustavo Pasqualini
 */

class MovieDetailsFragment : BaseFragment(), MovieDetailsView {

    companion object {
        val TAG = MovieDetailsFragment::class.java.simpleName
        private const val ARG_MOVIE = "ARG_MOVIE"

        fun bundleArgs(movie: Movie): Bundle {
            return Bundle().apply {
                this.putParcelable(ARG_MOVIE, movie)
            }
        }
    }

    @Inject
    lateinit var presenter: MovieDetailsPresenter
    var movie: Movie? = null

    override fun injectComponents() {
        DaggerMovieDetailsComponent.builder()
                .movieDetailsModule(MovieDetailsModule())
                .build()
                .inject(this)
    }

    override fun initData() {
        movie?.let {

        }
        //loadStates()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)
        super.onCreateView(inflater, container, savedInstanceState)
        return view
    }

    override fun initListeners() {

    }

    override fun initComponent(view: View?, savedInstanceState: Bundle?) {
        Log.d(TAG, "initComponents")

        //movie = getArguments()?.getParcelable<Movie?>(ARG_MOVIE)
        presenter.bindView(this)
        //setToolbarTitle(movie?.title)
        setHasOptionsMenu(true)
    }

    override fun showError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unbindView()
    }
}