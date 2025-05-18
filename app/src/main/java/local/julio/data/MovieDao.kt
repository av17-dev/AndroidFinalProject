package local.julio.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * DAO for the Movie entity.
 *
 * It defines some of the database access operations related to the entity Movie.
 */
@Dao
interface MovieDao {
    //In Room, the @Insert method can only return Unit or LongArray, for what I investigated
    //So returning a LongArray, I get the number of records that were inserted after executing the insertion
    @Insert
    suspend fun insertMovie(vararg movie: Movie): LongArray

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<Movie>>

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM movies WHERE movieId = :movieId LIMIT 1")
    suspend fun getMovieById(movieId: Int): Movie?

    //I decided to make a query that checks if the movie already exists in the DB.
    //I first tried the @Insert method to returns a Boolean, but Room can't manage this type directly.
    @Query("SELECT COUNT(*) FROM movies WHERE title = :title")
    suspend fun movieExists(title: String): Int
}