package local.julio.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import local.julio.data.Category
import local.julio.data.Movie
import local.julio.repository.CategoryRepository
import local.julio.repository.MovieRepository

/**
 * Category view model.
 */
class CategoryViewModel(val repository: CategoryRepository) : ViewModel() {
    val categoryData: StateFlow<List<Category>> = repository
        .getAllValues()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    suspend fun getCategoryById(categoryId: Int) = repository.getValueById(categoryId)
    suspend fun getCategoryId(categoryName: String) = repository.getCategoryId(categoryName)

    /*
        UNCOMMENT THE FOLLOWING LINES THE FIRST TIME YOU EXECUTE THE APPLICATION, SO SOME CATEGORIES
        WILL BE ADDED TO DATABASE. YOU CAN ADD MORE CATEGORIES OR REMOVE SOME, AS YOU WANT, BUT AT LEAST
        ONE CATEGORY IS NECESSARY FOR THE APP TO WORKS PROPERLY.

        EVEN IF ANY CATEGORY IS NOT INCLUDED ON THE DATABASE, APP SHOULD KEEP WORKING FINE, AS CATEGORY
        IS NOT A COMPULSORY FIELD TO FILL IN THE INSERT FORM, BUT THE SPINNER WILL BE EMPTY

     */

    //    init {
    //        insertCategory()
    //    }

    //    fun insertCategory() {
    //        viewModelScope.launch {
    //            val defaultCategoryOne = Category(catName = "Terror")
    //            val defaultCategoryTwo = Category(catName = "Comedy")
    //            val defaultCategoryThree = Category(catName = "Thriller")
    //            repository.insertValue(defaultCategoryOne)
    //            repository.insertValue(defaultCategoryTwo)
    //            repository.insertValue(defaultCategoryThree)
    //        }
    //    }
}





