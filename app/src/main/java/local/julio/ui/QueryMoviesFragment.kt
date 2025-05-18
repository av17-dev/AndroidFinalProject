package local.julio.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import local.julio.R
import local.julio.databinding.FragmentQueryMoviesBinding
import local.julio.repository.AppApplication
import local.julio.view_model.AppViewModelFactory
import local.julio.view_model.MovieViewModel
import kotlin.getValue

/**
 * This fragment manages the RecyclerView
 */
class QueryMoviesFragment : Fragment() {
    private var _binding: FragmentQueryMoviesBinding? = null
    private val binding
        get() = requireNotNull(_binding) { "View has not been inflated" }

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
        _binding = FragmentQueryMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            movieViewModel.movieData.collect {
                binding.recyclerView.adapter =
                    MovieAdapter(it) {
                        findNavController()
                            .navigate(QueryMoviesFragmentDirections.showMovieList(it.movieId))
                    }
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}