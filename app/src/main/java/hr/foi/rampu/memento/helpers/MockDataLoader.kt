package hr.foi.rampu.memento.helpers

import hr.foi.rampu.memento.database.TasksDatabase
import hr.foi.rampu.memento.entities.Task
import hr.foi.rampu.memento.entities.TaskCategory
import java.util.*

object MockDataLoader {

    fun loadMockData() {
        val tasksDao = TasksDatabase.getInstance().getTasksDao()
        val taskCategoriesDao = TasksDatabase.getInstance().getTaskCategoriesDao()

        if (tasksDao.getAllTasks(false).isEmpty() &&
            tasksDao.getAllTasks(true).isEmpty() &&
            taskCategoriesDao.getAllCategories().isEmpty()
        ) {
            val categories = arrayOf(
                TaskCategory(1, "RAMPU", "#000080"),
                TaskCategory(2, "RPP", "#FF0000"),
                TaskCategory(3, "RWA", "#CCCCCC")
            )
            taskCategoriesDao.insertCategory(*categories)

            val dbCategories = taskCategoriesDao.getAllCategories()

            val tasks = arrayOf(
                Task(1, "Submit seminar paper", Date(), dbCategories[0].id, false),
                Task(2, "Prepare for exercises", Date(), dbCategories[1].id, false),
                Task(3, "Rally a project team", Date(), dbCategories[0].id, false),
                Task(4, "Work on 1st homework", Date(), dbCategories[2].id, false)
            )
            tasksDao.insertTask(*tasks)
        }
    }
}
