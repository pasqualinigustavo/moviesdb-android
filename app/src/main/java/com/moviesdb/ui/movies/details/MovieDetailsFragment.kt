@file:JvmName("MovieDetailsFragment")

package com.moviesdb.ui.movies.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.moviesdb.R
import com.moviesdb.di.Injectable
import com.moviesdb.model.Movie
import com.moviesdb.rest.endpoint.request.MovieImageUrlBuilder
import com.moviesdb.ui.BaseFragment
import com.moviesdb.ui.movies.UpcomingMoviesFragment
import kotlinx.android.synthetic.main.fragment_movie_details.*


/**
 * @author Gustavo Pasqualini
 */

class MovieDetailsFragment : BaseFragment<MovieDetailsViewModel>(), Injectable {

    companion object {
        val TAG = UpcomingMoviesFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)
    }

    private val movieImageUrlBuilder = MovieImageUrlBuilder()

    override fun setupObservers() {
        viewModel.basketPayables.observe(viewLifecycleOwner, Observer { showMovie(it) })
    }

    override fun createViewModel(): MovieDetailsViewModel {
        return ViewModelProvider(this, viewModelFactory)
                .get(MovieDetailsViewModel::class.java)
    }

    fun showMovie(movie: Movie) {
        context?.let { ctx ->
            movie?.let {
                it.title?.let { setToolbarTitle(it) }
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
}