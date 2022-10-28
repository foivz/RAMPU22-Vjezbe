package hr.foi.rampu.memento.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hr.foi.rampu.memento.entities.Task
import hr.foi.rampu.memento.entities.TaskCategory

@Database(version = 1, entities = [Task::class, TaskCategory::class], exportSchema = false)
abstract class TasksDatabase : RoomDatabase() {

    abstract fun getTasksDao(): TasksDAO
    abstract fun getTaskCategoriesDao(): TaskCategoriesDAO

    companion object {

        @Volatile
        private var implementedInstance: TasksDatabase? = null

        fun getInstance(): TasksDatabase {
            if (implementedInstance == null) {
                throw NullPointerException("Database instance has not yet been created!")
            }
            return implementedInstance!!
        }

        fun buildInstance(context: Context) {
            if (implementedInstance == null) {
                val instanceBuilder = Room.databaseBuilder(
                    context,
                    TasksDatabase::class.java,
                    "tasks.db"
                )
                instanceBuilder.fallbackToDestructiveMigration()
                instanceBuilder.allowMainThreadQueries()
                instanceBuilder.build()

                implementedInstance = instanceBuilder.build()
            }
        }
    }
}
