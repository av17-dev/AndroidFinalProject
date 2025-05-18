package local.julio.repository

import kotlinx.coroutines.flow.Flow
import local.julio.data.Movie
import local.julio.data.MovieDao

/**
 * An implementation of IRepository interface.
 *
 * It represents the Movie repository.
 */
class MovieRepository(private val movieDao: MovieDao) : IRepository<Movie> {
    override fun getAllValues(): Flow<List<Movie>> = movieDao.getAllMovies()
    override suspend fun getValueById(id: Int): Movie? = movieDao.getMovieById(id)

    //Before inserting a movie, I check, by consulting the DB, if the movie exists
    //If value is 0, this movie is not present in DB; other case, it is, so it can't be inserted
    override suspend fun insertValue(value: Movie): LongArray {
        if (movieDao.movieExists(value.title) == 0)
            return movieDao.insertMovie(value)
        return LongArray(0) //If nothing was inserted, I return an empty LongArray
    }

    //Method not included on IRepository because this is exclusive for this repo implementation
    suspend fun deleteMovie(movie: Movie) = movieDao.deleteMovie(movie)
}