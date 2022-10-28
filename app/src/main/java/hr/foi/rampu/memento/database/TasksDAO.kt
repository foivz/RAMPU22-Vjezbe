package hr.foi.rampu.memento.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import hr.foi.rampu.memento.entities.Task

@Dao
interface TasksDAO {
    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTask(id: Int): Task

    @Query("SELECT * FROM tasks WHERE completed = :completed ORDER BY due_date ASC")
    fun getAllTasks(completed: Boolean): List<Task>

    @Insert(onConflict = REPLACE)
    fun insertTask(vararg task: Task): List<Long>

    @Delete
    fun removeTask(vararg task: Task)
}
