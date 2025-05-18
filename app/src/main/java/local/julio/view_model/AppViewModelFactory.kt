package local.julio.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import local.julio.repository.CategoryRepository
import local.julio.repository.IRepository
import local.julio.repository.MovieRepository

/**
 * View Model Factory
 */
class AppViewModelFactory<V>(val repository: IRepository<V>) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java))
            return MovieViewModel(repository as MovieRepository) as T
        else if (modelClass.isAssignableFrom(CategoryViewModel::class.java))
            return CategoryViewModel(repository as CategoryRepository) as T
        throw IllegalArgumentException("Not a valid repository")
    }
}