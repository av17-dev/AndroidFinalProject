package local.julio.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Room database for the application.
 */
@Database(entities = [Movie::class, Category::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
    abstract fun getCategoryDao(): CategoryDao

    /**
     * Applying Singleton pattern to make sure there is only one instance of the database
     * in the entire application.
     */
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val dbLocation = context.getDatabasePath("database")

                if (!dbLocation.exists()) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "database"
                    ).createFromAsset("database")
                        .build()
                    INSTANCE = instance
                    return instance
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}