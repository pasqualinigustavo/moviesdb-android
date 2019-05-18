@file:JvmName("MovieDetailsFragment")

package com.arctouch.codechallenge.ui.moviedetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.BaseFragment
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.ui.moviedetails.di.DaggerMovieDetailsComponent
import com.arctouch.codechallenge.ui.moviedetails.di.MovieDetailsModule
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_movie_details.*
import javax.inject.Inject

/**
 * @author Gustavo Pasqualini
 */

class MovieDetailsFragment : BaseFragment(), MovieDetailsView {

    private val args: MovieDetailsFragmentArgs by navArgs()

    companion object {
        val TAG = MovieDetailsFragment::class.java.simpleName
        private const val ARG_MOVIE = "movie"

        fun bundleArgs(movie: Movie): Bundle {
            return Bundle().apply {
                this.putParcelable(ARG_MOVIE, movie)
            }
        }
    }

    @Inject
    lateinit var presenter: MovieDetailsPresenter
    lateinit var movie: Movie

    override fun injectComponents() {
        DaggerMovieDetailsComponent.builder()
                .homeComponent(getMainComponent())
                .movieDetailsModule(MovieDetailsModule())
                .build()
                .inject(this)
    }

    override fun initData() {
        presenter.getMovie(movie?.id)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun initListeners() {

    }

    override fun initComponent(view: View?, savedInstanceState: Bundle?) {
        Log.d(TAG, "initComponents")
        movie = args?.movie
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

    override fun showMovie(movie: Movie) {
        val movieImageUrlBuilder = MovieImageUrlBuilder()

        //title
        titleTextView.text = movie.title
        //genres
        genresTextView.text = movie.genres?.joinToString(separator = ", ") { it.name }
        //releaseDate
        releaseDateTextView.text = movie.releaseDate
        //poster
        context?.let {
            Glide.with(context!!)
                    .load(movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                    .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(posterImageView)

            //backdropimage
            Glide.with(context!!)
                    .load(movie.backdropPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                    .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(posterImageView)
        }
        //overview
        movieOverview.text = movie.overview
    }

    override fun showProgressDialog() {

    }

    override fun hideProgressDialog() {

    }
}