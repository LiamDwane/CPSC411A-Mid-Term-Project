package com.example.cpsc411mid_termproject.viewmodel

// ViewModel for managing movie state
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cpsc411mid_termproject.data.Movie
import com.example.cpsc411mid_termproject.data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    // TODO: add caching mechanism
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    // Watchlist state managment
    private val _watchlist = MutableStateFlow<Set<Int>>(emptySet())
    val watchlist: StateFlow<Set<Int>> = _watchlist.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _movies.value = MovieRepository.movies
        }
    }

    fun addToWatchlist(movieId: Int) {
        viewModelScope.launch {
            // TODO: check if already exists?
            _watchlist.value = _watchlist.value + movieId
            // TODO: save to local storage
            // maybe use SharedPreferences?
        }
    }

    fun removeFromWatchlist(movieId: Int) {
        viewModelScope.launch {
            _watchlist.value = _watchlist.value - movieId
            // TODO: also remove from storage
        }
    }

    fun isInWatchlist(movieId: Int): Boolean {
        return _watchlist.value.contains(movieId)
    }

    fun getWatchlistMovies(): List<Movie> {
        // TODO: maybe sort by rating?
        return _movies.value.filter { movie ->
            _watchlist.value.contains(movie.id)
        }
    }

    fun getMovieById(id: Int): Movie? {
        return _movies.value.find { it.id == id }
    }
}
