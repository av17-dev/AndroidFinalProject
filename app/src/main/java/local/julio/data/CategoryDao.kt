package local.julio.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO for the Category entity.
 *
 * It defines some of the database access operations related to the entity Category.
 */
@Dao
interface CategoryDao {
    @Insert
    suspend fun insertCategory(vararg category: Category): LongArray

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE categoryId = :categoryId LIMIT 1")
    suspend fun getCategoryById(categoryId: Int): Category

    @Query("SELECT * FROM categories WHERE catName = :categoryName LIMIT 1")
    suspend fun getCategoryId(categoryName: String): Category
}