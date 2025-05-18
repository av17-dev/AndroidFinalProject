package local.julio.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import local.julio.R
import local.julio.databinding.FragmentHomeBinding
import local.julio.repository.AppApplication
import local.julio.view_model.AppViewModelFactory
import local.julio.view_model.CategoryViewModel
import local.julio.view_model.MovieViewModel
import kotlin.getValue

/**
 * Main fragment of the application.
 *
 * This is the first screen the user see when they access the application.
 */
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "View hasn't been initialized" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root //Inflates the view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.insertMovieButton.setOnClickListener {
            findNavController()
                .navigate(HomeFragmentDirections.showInsertFragment()) //Starts and renders the InsertMovieFragment
        }
        binding.checkMovieButton.setOnClickListener {
            findNavController()
                .navigate(HomeFragmentDirections.showQueryFragment()) //Starts and renders the QueryMoviesFragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null //Prevents memory leaks
    }
}