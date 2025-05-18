package local.julio.repository

import android.app.Application
import local.julio.data.AppDatabase

/**
 * This class is used to create single instances of the database and the repositories.
 *
 * It uses the 'by lazy' delegated property to improve performance
 */
class AppApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val movieRepository by lazy { MovieRepository(database.getMovieDao()) }
    val categoryRepository by lazy { CategoryRepository(database.getCategoryDao()) }
}