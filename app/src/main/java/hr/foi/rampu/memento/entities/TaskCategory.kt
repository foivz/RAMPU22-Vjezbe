package hr.foi.rampu.memento.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_categories")
data class TaskCategory(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val color: String
) {
    override fun toString(): String {
        return name
    }
}