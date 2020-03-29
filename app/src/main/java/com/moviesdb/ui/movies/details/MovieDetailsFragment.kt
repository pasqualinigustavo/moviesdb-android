@file:JvmName("MovieDetailsFragment")

package com.moviesdb.ui.movies.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.moviesdb.R
import com.moviesdb.model.Movie
import com.moviesdb.rest.endpoint.request.MovieImageUrlBuilder
import com.moviesdb.ui.BaseFragment
import com.moviesdb.ui.movies.details.di.DaggerMovieDetailsComponent
import com.moviesdb.ui.movies.details.di.MovieDetailsModule
import kotlinx.android.synthetic.main.fragment_movie_details.*
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
    private val movieImageUrlBuilder = MovieImageUrlBuilder()

    override fun injectComponents() {
        DaggerMovieDetailsComponent.builder()
                .movieDetailsModule(MovieDetailsModule())
                .build()
                .inject(this)
    }

    override fun initData() {
        movie?.let {

        }
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
        context?.let { ctx ->
            movie?.let {
                fragment_movie_details__textview_title.text = it.title
                fragment_movie_details__textview_genre.text = it.genres?.joinToString(separator = ", ") { it.name }
                fragment_movie_details__textview_releasedate.text = it.releaseDate

                Glide.with(ctx)
                        .load(it.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                        .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                        .into(fragment_movie_details__imageview_poster)
            }
        }
    }

    override fun initComponent(view: View?, savedInstanceState: Bundle?) {
        Log.d(TAG, "initComponents")

        movie = getArguments()?.getParcelable<Movie?>(ARG_MOVIE)
        presenter.bindView(this)
        movie?.title?.let { setToolbarTitle(it) }
    }

    override fun showError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unbindView()
    }
}