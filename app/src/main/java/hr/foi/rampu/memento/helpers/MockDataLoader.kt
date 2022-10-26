package hr.foi.rampu.memento.helpers

import hr.foi.rampu.memento.entities.Task
import hr.foi.rampu.memento.entities.TaskCategory
import java.util.*

object MockDataLoader {
    fun getDemoData(): MutableList<Task> {
        val categories = getDemoCategories()

        return mutableListOf(
            Task("Submit seminar paper", Date(), categories[0], false),
            Task("Prepare for exercises", Date(), categories[1], false),
            Task("Rally a project team", Date(), categories[0], false),
            Task("Work on 1st homework", Date(), categories[2], false)
        )
    }

    fun getDemoCategories(): List<TaskCategory> =
        listOf(
            TaskCategory(0, "RAMPU", "#000080"),
            TaskCategory(1, "RPP", "#FF0000"),
            TaskCategory(2, "RWA", "#CCCCCC")
        )
}
