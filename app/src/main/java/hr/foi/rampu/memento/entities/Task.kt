package hr.foi.rampu.memento.entities

import androidx.room.*
import hr.foi.rampu.memento.converters.DateConverter
import hr.foi.rampu.memento.database.TasksDatabase
import java.util.*

@Entity(
    tableName = "tasks",
    foreignKeys = [ForeignKey(
        entity = TaskCategory::class,
        parentColumns = ["id"],
        childColumns = ["category_id"],
        onDelete = ForeignKey.RESTRICT
    )]
)
@TypeConverters(DateConverter::class)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    @ColumnInfo(name = "due_date") val dueDate: Date,
    @ColumnInfo(name = "category_id", index = true) val categoryId: Int,
    val completed: Boolean
) {
    @delegate:Ignore
    val category: TaskCategory by lazy {
        TasksDatabase.getInstance().getTaskCategoriesDao().getCategoryById(categoryId)
    }
}
