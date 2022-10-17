package hr.foi.rampu.memento.helpers

import hr.foi.rampu.memento.entities.Task
import hr.foi.rampu.memento.entities.TaskCategory
import java.util.*

object MockDataLoader {
    fun getDemoData(): List<Task> = listOf(
        Task("Submit seminar paper", Date(), TaskCategory("RAMPU", "#000080"), false),
        Task("Prepare for exercises", Date(), TaskCategory("RPP", "#FF0000"), false),
        Task("Rally a project team", Date(), TaskCategory("RAMPU", "#000080"), false),
        Task("Work on 1st homework", Date(), TaskCategory("RWA", "#CCCCCC"), false)
    )
}
