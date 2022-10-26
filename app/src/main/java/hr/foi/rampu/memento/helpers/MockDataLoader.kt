package hr.foi.rampu.memento.helpers

import hr.foi.rampu.memento.entities.Task
import hr.foi.rampu.memento.entities.TaskCategory
import java.util.*

object MockDataLoader {
    fun getDemoData(): MutableList<Task> {
        return mutableListOf(
            Task(0, "Submit seminar paper", Date(), 0, false),
            Task(1, "Prepare for exercises", Date(), 1, false),
            Task(2, "Rally a project team", Date(), 0, false),
            Task(3, "Work on 1st homework", Date(), 2, false)
        )
    }

    fun getDemoCategories(): List<TaskCategory> =
        listOf(
            TaskCategory(0, "RAMPU", "#000080"),
            TaskCategory(1, "RPP", "#FF0000"),
            TaskCategory(2, "RWA", "#CCCCCC")
        )
}
