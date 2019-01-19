package com.example.mg156.assignment8

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.LruCache
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.net.URI
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.experimental.coroutineContext


class RecyclerViewAdapter(context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.MovieViewHolder>() {

    var myListener: MyItemClickListener? = null
    var lastPosition = -1

    private lateinit var mMemoryCache : LruCache<String, Bitmap>
    val movieList = ArrayList<MovieData>()
    val mcontext = context


    init {
        var url = BASE_URL + "movies"
        val task = DownloadMovies(movieList)
        task.execute(url)
        val maxMemory = (Runtime.getRuntime().maxMemory() /
                1024).toInt()

        val cacheSize = maxMemory / 8
        mMemoryCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                return bitmap.byteCount / 1024
            }
        }
    }

    interface MyItemClickListener {
        fun onItemClickedFromAdapter(movie: MovieData)
        fun onOverFlowMenuClick(view: View, position: Int)
    }

    fun setMyItemClickListener(listener: MyItemClickListener) {
        this.myListener = listener
    }

    fun getMoviesByRating(url1 : String){
        val task = DownloadMovies(movieList)
        task.execute(url1)
    }


    inner class DownloadMovies(data: ArrayList<MovieData>) : AsyncTask<String, Void, String>() {
        val weakData = WeakReference<ArrayList<MovieData>>(data)
        // weak reference to main UI thread 's items
        override fun doInBackground(vararg params: String?): String {
            val result = MyUtility.downloadJSONusingHTTPGetRequest(params[0]!!)
            return result!!
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            var list = weakData.get()
            if (list != null) {
                list.clear()
                Log.i("Assignment 8", "Getting Movies from the database")
                val movies = Gson().fromJson(result, Array<MovieData>::class.java).asList()
                if(movies[0].error == true){
                    Toast.makeText(mcontext, movies[0].message, Toast.LENGTH_LONG).show()
                }
                else {
                    for (movie in movies) {
                        Log.i("Assignment 8",movie.toString())
                        list.add(movie)
                    }
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val v: View
        when (viewType) {
            1 -> v = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
            2 -> v = LayoutInflater.from(parent.context).inflate(R.layout.item_row2, parent, false)
            3 -> v = LayoutInflater.from(parent.context).inflate(R.layout.item_row3, parent, false)
            else -> v = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        }
        return MovieViewHolder(v)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class DownloadMovieImage(img: ImageView) : AsyncTask<String, Void, Bitmap>() {
        val weakImg = WeakReference<ImageView>(img)
        override fun doInBackground(vararg params: String?): Bitmap {
            val result = MyUtility.downloadImageusingHTTPGetRequest(params[0]!!)
            if (result != null) {
                mMemoryCache.put(params[0]!!, result)
            }
            return result!!
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            val img = weakImg.get()
            if (img != null) {
                img.setImageBitmap(result)
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]

        val url = "https://image.tmdb.org/t/p/w780/" + movie.poster_path!!
        val bitmap = mMemoryCache.get(url)
        if (bitmap != null) {
            holder.moviePoster.setImageBitmap(bitmap)
        } else {
            val task = DownloadMovieImage(holder.moviePoster)
            task.execute(url)
        }
        holder.movieGenre.setText("Rating: " + movie?.vote_average.toString())
        holder.movieTitle.text = movie.title
        holder.movieOverview.text = movie.overview
        setAnimation(holder.moviePoster, position)
    }


    fun setAnimation(view: View, position: Int) {
        if (position != lastPosition) {
            var animation = AnimationUtils.loadAnimation(view.context, android.R.anim.slide_in_left);
            animation.setDuration(1000);
            view.startAnimation(animation);
            lastPosition = position
        }
    }

    fun removeMovie(position: Int) {
        var movie = movieList[position]

        val json: String?
        if (movie != null) {
            json = Gson().toJson(movie)
        }
        else {
            json = null
        }
        val runnable = Runnable {
            val url = BASE_URL + "movies/delete"
            MyUtility.sendHttPostRequest(url, json!!)
        }
        Log.i("Assignment 8", "Movie Removed from the database")
        Log.i("Assignment 8",movie.toString())
        Thread(runnable).start()
        movieList.removeAt(position)

    }

    fun addMovie(position: Int) {
        var newMovie = movieList[position]
        newMovie.title = newMovie.title + "_New"

        val json: String?
        if (newMovie != null) {
            json = Gson().toJson(newMovie)
        }
        else {
            json = null
        }
        val runnable = Runnable {
            val url = BASE_URL + "movies/add"
            MyUtility.sendHttPostRequest(url, json!!)
        }
        Log.i("Assignment 8", "New Movie Added to the database")
        Log.i("Assignment 8",newMovie.toString())
        Thread(runnable).start()
        movieList.add(position + 1, newMovie)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < 3) {
            1
        } else if (position >= itemCount - 3) {
            3
        } else
            2
    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val moviePoster = view.findViewById<ImageView>(R.id.item_image)
        val movieTitle = view.findViewById<TextView>(R.id.item_title)
        val movieOverview = view.findViewById<TextView>(R.id.item_overview)
        val movieGenre = view.findViewById<TextView>(R.id.item_genre)
        val movie_overflow_image = view.findViewById<ImageView>(R.id.item_overflow_image)

        init {
            view.setOnClickListener(View.OnClickListener { v ->
                if (myListener != null) {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        myListener!!.onItemClickedFromAdapter(movieList[adapterPosition])
                    }
                }
            })
            movie_overflow_image.setOnClickListener(View.OnClickListener { v ->
                myListener!!.onOverFlowMenuClick(v, adapterPosition)
            })
        }
    }
}