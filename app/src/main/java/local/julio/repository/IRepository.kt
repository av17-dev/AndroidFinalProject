package local.julio.repository

import kotlinx.coroutines.flow.Flow

/**
 * Generic repository interface.
 *
 * It defines common operations that both implementations, MovieRepository and CategoryRepository,
 * must implement
 */
interface IRepository<T> {
    fun getAllValues(): Flow<List<T>>
    suspend fun insertValue(value: T): LongArray
    suspend fun getValueById(id: Int): T?
}