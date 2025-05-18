package local.julio.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import local.julio.data.Movie
import local.julio.databinding.ItemListBinding

/**
 * Adapter for displaying a list of movies in a RecyclerView.
 *
 * It binds each movie item to a view and allows interaction with the list item through a click listener.
 */
class MovieAdapter(val movies: List<Movie>, val onItemListener: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(val binding: ItemListBinding, val onItemListener: (Movie) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            binding.root.setOnClickListener { onItemListener(movie) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding, onItemListener)
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size
}