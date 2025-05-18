package local.julio.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import local.julio.data.Category
import local.julio.data.Movie
import local.julio.databinding.FragmentInsertMovieBinding
import local.julio.repository.AppApplication
import local.julio.view_model.AppViewModelFactory
import local.julio.view_model.CategoryViewModel
import local.julio.view_model.MovieViewModel

/**
 * This fragment manages the insertion of Movies in the DB.
 *
 * The associated layout displays a form that lets users to insert a movie
 */
class InsertMovieFragment : Fragment() {
    private var _binding: FragmentInsertMovieBinding? = null
    private val binding
        get() = requireNotNull(_binding) { "View has not been inflated" }

    private val catViewModel: CategoryViewModel by viewModels {
        val repository = (requireActivity().application as AppApplication).categoryRepository
        AppViewModelFactory(repository)
    }

    private val movieViewModel: MovieViewModel by viewModels {
        val repository = (requireActivity().application as AppApplication).movieRepository
        AppViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInsertMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        assignCategoriesToSpinner()
        insertMovie()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun insertMovie() {
        binding.sendButton.setOnClickListener {
            lifecycleScope.launch {
                val categoryName = binding.spinnerCategory.selectedItem.toString()
                val category = catViewModel.getCategoryId(categoryName)
                val movie = Movie(
                    title = binding.inputTitle.text.toString(),
                    summary = binding.inputSummary.text.toString(),
                    categoryId = category.categoryId
                )
                showFeedback(movie)
            }
        }
    }

    private suspend fun showFeedback(movie: Movie) {
        if (movie.title.isNotBlank()) {
            val isInsert = wasInserted(movie)
            if (isInsert) {
                toast(requireContext(), "Película añadida correctamente")
                cleanForm()
            } else
                toast(requireContext(), "Esta película ya existe")
        } else {
            toast(requireContext(), "El campo título no puede estar vacío")
        }
    }

    private suspend fun wasInserted(movie: Movie) = movieViewModel.insertMovie(movie).isNotEmpty()

    private fun toast(context: Context, message: String) {
        Toast
            .makeText(context, message, Toast.LENGTH_SHORT)
            .show()
    }

    private fun cleanForm() {
        binding.inputTitle.setText("")
        binding.inputSummary.setText("")
    }

    private fun assignCategoriesToSpinner() {
        lifecycleScope.launch {
            catViewModel.categoryData.collect {
                val arrayAdapter = ArrayAdapter<Category>(
                    requireContext(),
                    android.R.layout.select_dialog_item,
                    it
                )
                binding.spinnerCategory.adapter = arrayAdapter
            }
        }
    }
}
