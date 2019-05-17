package com.arctouch.codechallenge.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class GenreResponse(val genres: List<Genre>)

data class Genre(val id: Int, val name: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel?, p1: Int) {
        parcel?.writeValue(id)
        parcel?.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Genre> {
        override fun createFromParcel(parcel: Parcel): Genre {
            return Genre(parcel)
        }

        override fun newArray(size: Int): Array<Genre?> {
            return arrayOfNulls(size)
        }
    }

}

data class UpcomingMoviesResponse(
        val page: Int,
        val results: List<Movie>,
        @Json(name = "total_pages") val totalPages: Int,
        @Json(name = "total_results") val totalResults: Int
)

data class Movie(var id: Long,
                 var title: String,
                 var overview: String?,
                 var genres: List<Genre>?,
                 @Json(name = "genre_ids") var genreIds: List<Int>?,
                 @Json(name = "poster_path") var posterPath: String?,
                 @Json(name = "backdrop_path") var backdropPath: String?,
                 @Json(name = "release_date") var releaseDate: String?) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(Genre),
            parcel.createIntArray().toList(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(overview)
        parcel.writeTypedList(genres)
        parcel.writeIntArray(genreIds?.toIntArray())
        parcel.writeString(posterPath)
        parcel.writeString(backdropPath)
        parcel.writeString(releaseDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}
