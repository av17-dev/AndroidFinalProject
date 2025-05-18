package local.julio.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import local.julio.data.Movie
import local.julio.repository.MovieRepository

/**
 * Movie view model.
 */
class MovieViewModel(val repository: MovieRepository) : ViewModel() {
    val movieData: StateFlow<List<Movie>> = repository
        .getAllValues()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    suspend fun insertMovie(movie: Movie): LongArray = repository.insertValue(movie)

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            repository.deleteMovie(movie)
        }
    }

    suspend fun getMovieById(movieId: Int) = repository.getValueById(movieId)
}