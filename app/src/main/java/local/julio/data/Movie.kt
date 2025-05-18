package local.julio.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Represents a Movie entity in the database
 */
@Entity(
    tableName = "movies", foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["categoryId"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Movie(
    @PrimaryKey(autoGenerate = true) val movieId: Int = 0,
    val title: String,
    val summary: String?,
    val categoryId: Int
)