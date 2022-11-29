package hr.foi.rampu.memento.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hr.foi.rampu.memento.R
import hr.foi.rampu.memento.adapters.TasksAdapter
import hr.foi.rampu.memento.database.TasksDatabase
import hr.foi.rampu.memento.helpers.NewTaskDialogHelper

class PendingFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnCreateTask: FloatingActionButton

    private val tasksDao = TasksDatabase.getInstance().getTasksDao()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_pending_tasks)
        loadTaskList()
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        btnCreateTask = view.findViewById(R.id.fab_pending_fragment_create_task)
        btnCreateTask.setOnClickListener { showDialog() }
    }

    private fun loadTaskList() {
        val tasks = tasksDao.getAllTasks(false)
        val tasksAdapter = TasksAdapter(tasks.toMutableList()) { taskId ->
            parentFragmentManager.setFragmentResult(
                "task_completed",
                bundleOf("task_id" to taskId)
            )
        }
        recyclerView.adapter = tasksAdapter
    }

    private fun showDialog() {
        val newTaskDialogView = LayoutInflater.from(context).inflate(R.layout.new_task_dialog, null)
        val dialogHelper = NewTaskDialogHelper(newTaskDialogView)

        AlertDialog.Builder(context)
            .setView(newTaskDialogView)
            .setTitle(getString(R.string.create_a_new_task))
            .setPositiveButton(getString(R.string.create_a_new_task)) { _, _ ->
                var newTask = dialogHelper.buildTask()
                val newTaskId = tasksDao.insertTask(newTask)[0]
                newTask = tasksDao.getTask(newTaskId.toInt())
                val tasksAdapter = (recyclerView.adapter as TasksAdapter)
                tasksAdapter.addTask(newTask)

                incrementTasksCreatedCounter()
            }
            .show()

        val categories = TasksDatabase.getInstance().getTaskCategoriesDao().getAllCategories()
        dialogHelper.setDefaultTaskName()
        dialogHelper.populateSpinner(categories)
        dialogHelper.activateDateTimeListeners()
    }

    private fun incrementTasksCreatedCounter() {
        context?.getSharedPreferences("tasks_preferences", Context.MODE_PRIVATE)?.apply {
            val currentCount = getInt("tasks_created_counter", 0)
            edit().putInt("tasks_created_counter", currentCount + 1).apply()
        }
    }
}
