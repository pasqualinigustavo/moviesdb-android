package com.moviesdb.ui.movies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.moviesdb.R
import com.moviesdb.model.Movie
import com.moviesdb.model.interfaces.IAdapterDataSource
import com.moviesdb.rest.endpoint.request.MovieImageUrlBuilder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.row_load_more.view.*
import kotlinx.android.synthetic.main.row_movie.view.*

class MovieAdapter
constructor(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    private val movieImageUrlBuilder = MovieImageUrlBuilder()
    private var adapterInteractionListener: IAdapterDataSource? = null
    private var hasMoreData = true
    private var loadProgressBar: ProgressBar? = null

    var items: MutableList<Movie> = emptyList<Movie>().toMutableList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val clickSubject = PublishSubject.create<Movie>()
    val clickEvent: Observable<Movie> = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_movie, parent, false)
            return RecyclerViewAdapterViewHolder(itemView)
        } else {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_load_more, parent, false)
            return LoadViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (items.size > 0 && items.size == position)
            return VIEW_TYPE_LOADING
        return VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LoadViewHolder) {
            if (adapterInteractionListener != null) {
                val visibility = if (hasMoreData) View.VISIBLE else View.GONE

                val loadViewHolder = holder
                loadViewHolder.progressBar.setVisibility(visibility)

                userWantsLoadMore(loadViewHolder.progressBar)
            }
        } else if (holder is RecyclerViewAdapterViewHolder) {
            val itemRowHolder = holder
            val entity = items[position]

            itemRowHolder.titleTextView.text = entity.title
            itemRowHolder.genresTextView.text = entity.genres?.joinToString(separator = ", ") { it.name }
            itemRowHolder.releaseDateTextView.text = entity.releaseDate

            Glide.with(context)
                    .load(entity.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                    .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(itemRowHolder.posterImageView)
        }
    }

    override fun getItemCount(): Int {
        if (items.size > 0) {
            if (hasMoreData && adapterInteractionListener != null)
                return items.size + 1
            else return items.size
        }
        return 0
    }

    fun setAdapterInteractionListener(adapterInteractionListener: IAdapterDataSource) {
        this.adapterInteractionListener = adapterInteractionListener
    }

    fun setHasMoreData(hasMoreData: Boolean) {
        this.hasMoreData = hasMoreData
        if (loadProgressBar != null) {
            val visibility = if (hasMoreData) View.VISIBLE else View.GONE
            loadProgressBar!!.setVisibility(visibility)
        }
    }

    private fun userWantsLoadMore(progressBar: ProgressBar) {
        loadProgressBar = progressBar
        if (hasMoreData && adapterInteractionListener != null) {
            loadProgressBar!!.setVisibility(View.VISIBLE)
            adapterInteractionListener!!.adapterUserWantsLoadMoreData(this)
        }
    }

    fun addMoreItemsToDateSet(newList: MutableList<Movie>?) {
        if (newList != null && newList.size > 0) {
            for (i in newList.indices) {
                if (!items.contains(newList[i]))
                    items.add(newList[i])
            }
        }
        notifyDataSetChanged()
    }

    internal class LoadViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val progressBar = view.row_load_more_progress_bar
    }

    inner class RecyclerViewAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val row_movie_container = view.row_movie_container
        val posterImageView = view.posterImageView
        val titleTextView = view.titleTextView
        val genresTextView = view.genresTextView
        val releaseDateTextView = view.releaseDateTextView

        init {
            row_movie_container.setOnClickListener {
                clickSubject?.onNext(items[layoutPosition])
            }
        }
    }
}
