package hr.foi.rampu.memento.helpers

import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import hr.foi.rampu.memento.R
import hr.foi.rampu.memento.entities.TaskCategory
import java.text.SimpleDateFormat
import java.util.*

class NewTaskDialogHelper(private val view: View) {

    private val selectedDate: Calendar = Calendar.getInstance()

    private val sdfDate = SimpleDateFormat("dd.MM.yyyy.", Locale.ENGLISH)
    private val sdfTime = SimpleDateFormat("HH:mm", Locale.ENGLISH)

    private val spinner = view.findViewById<Spinner>(R.id.spn_new_task_dialog_category)
    private val dateSelection = view.findViewById<EditText>(R.id.et_new_task_dialog_date)
    private val timeSelection = view.findViewById<EditText>(R.id.et_new_task_dialog_time)

    fun populateSpinner(categories: List<TaskCategory>) {
        val spinnerAdapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
    }
}
