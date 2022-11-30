package hr.foi.rampu.memento.helpers

import hr.foi.rampu.memento.database.TasksDatabase
import hr.foi.rampu.memento.entities.Task
import java.io.*
import java.util.*

private const val RECOVERY_SUCCESS = 0
private const val RECOVERY_NO_TASKS = 1
private const val RECOVERY_COULD_NOT_READ = 2

object DeletedTaskRecovery {
    fun pushTask(deletedTask: Task, cacheDir: File): Boolean {
        val folder = File(cacheDir, "deleted_tasks_cache")
        if (!folder.exists()) {
            folder.mkdir()
        }

        val newTaskIndex = folder.listFiles()?.size?.plus(1)

        val newDeletedTaskFile = File(folder.path + "/task_" + newTaskIndex)
        newDeletedTaskFile.createNewFile()

        var success = true

        FileOutputStream(newDeletedTaskFile).use { fos ->
            ObjectOutputStream(fos).apply {
                try {
                    writeObject(DeletedTask(deletedTask))
                } catch (ex: IOException) {
                    ex.printStackTrace()
                    success = false
                } finally {
                    flush()
                    close()
                }
            }
        }

        return success
    }

    fun popTask(cacheDir: File): Int {
        val folder = File(cacheDir, "deleted_tasks_cache")
        if (!folder.exists()) {
            folder.mkdir()
        }

        var result = RECOVERY_SUCCESS

        val fileCount = folder.listFiles()?.size
        var restoredTaskId: Int = -1

        if (fileCount == 0) {
            result = RECOVERY_NO_TASKS
        } else {
            val currentDeletedTaskFile = File(folder.path + "/task_" + fileCount)

            FileInputStream(currentDeletedTaskFile).use { fis ->
                ObjectInputStream(fis).apply {
                    try {
                        val recoveredFile = readObject() as DeletedTask
                        val tasksDao = TasksDatabase.getInstance().getTasksDao()
                        restoredTaskId = tasksDao.insertTask(
                            Task(
                                0,
                                recoveredFile.name,
                                recoveredFile.dueDate,
                                recoveredFile.categoryId,
                                recoveredFile.wasCompleted
                            )
                        )[0].toInt()
                    } catch (ex: IOException) {
                        ex.printStackTrace()
                        result = RECOVERY_COULD_NOT_READ
                    } finally {
                        close()
                    }
                }
            }
        }

        if (result != RECOVERY_SUCCESS || restoredTaskId == -1) {
            val msg = when (result) {
                RECOVERY_NO_TASKS -> "No tasks found!"
                RECOVERY_COULD_NOT_READ -> "Corrupt cache! Consider cleaning app's cache."
                else -> "Unknown error occured: $result"
            }
            throw Exception(msg)
        }

        return restoredTaskId
    }

    class DeletedTask(task: Task) : Serializable {
        var categoryId: Int
        var name: String
        var dueDate: Date
        var wasCompleted: Boolean

        init {
            this.name = task.name
            this.categoryId = task.categoryId
            this.dueDate = task.dueDate
            this.wasCompleted = task.completed
        }
    }
}