package local.julio.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import local.julio.data.Movie
import local.julio.databinding.FragmentMovieDetailBinding
import local.julio.repository.AppApplication
import local.julio.view_model.AppViewModelFactory
import local.julio.view_model.CategoryViewModel
import local.julio.view_model.MovieViewModel
import kotlin.getValue

/**
 * This class manages the movie detail fragment, who lets the users to watch the movie details and
 * delete if he wants
 */
class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "View has not been inflated" }

    private val movieViewModel: MovieViewModel by viewModels {
        val repository = (requireActivity().application as AppApplication).movieRepository
        AppViewModelFactory(repository)
    }

    private val catViewModel: CategoryViewModel by viewModels {
        val repository = (requireActivity().application as AppApplication).categoryRepository
        AppViewModelFactory(repository)
    }

    private val argument: MovieDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
        deleteMovie()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun deleteMovie() {
        lifecycleScope.launch {
            movieViewModel.movieData.collect {
                val movie: Movie? = movieViewModel.getMovieById(argument.movieId)
                if (movie != null) {
                    binding.deleteMovieButton.setOnClickListener {
                        showConfirmDialog(movie)
                    }
                }
            }
        }
    }

    private fun showConfirmDialog(movie: Movie) {
        val alert = AlertDialog.Builder(requireContext())
            .setTitle("Confirmación")
            .setMessage("¿Estás seguro de que quieres eliminar esta película?")
            .setPositiveButton("Sí") { _, _ ->
                movieViewModel.deleteMovie(movie)
                findNavController().popBackStack() //Go back to QueryMoviesFragment
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alert.show()
    }

    private fun updateUI() {
        lifecycleScope.launch {
            movieViewModel.movieData.collect {
                val movie: Movie? = movieViewModel.getMovieById(argument.movieId)
                if (movie != null) {
                    val category = catViewModel.getCategoryById(movie.categoryId)
                    binding.movieTitleDetail.text = movie.title
                    binding.movieCategoryDetail.text = category?.catName
                    binding.movieSummaryDetail.text = movie.summary
                }
            }
        }
    }
}