package local.julio.repository

import kotlinx.coroutines.flow.Flow
import local.julio.data.Category
import local.julio.data.CategoryDao
import local.julio.data.Movie

/**
 * An implementation of IRepository interface.
 *
 * It represents the Category repository.
 */
class CategoryRepository(private val categoryDao: CategoryDao) : IRepository<Category> {
    override fun getAllValues(): Flow<List<Category>> = categoryDao.getAllCategories()
    override suspend fun insertValue(value: Category): LongArray = categoryDao.insertCategory(value)
    override suspend fun getValueById(id: Int): Category? = categoryDao.getCategoryById(id)
    suspend fun getCategoryId(categoryName: String): Category = categoryDao.getCategoryId(categoryName)
}