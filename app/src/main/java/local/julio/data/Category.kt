package local.julio.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a category entity in the database
 */
@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val categoryId: Int = 0,
    val catName: String
) {
    override fun toString(): String = catName
}