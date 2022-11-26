package hr.foi.rampu.memento.sync

import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.PutDataMapRequest
import hr.foi.rampu.memento.entities.Task

object WearableSynchronizer {
    fun sendTasks(tasks: List<Task>, dataClient: DataClient) {
        val request = PutDataMapRequest.create("/tasks").apply {
            dataMap.putInt("tasks_count", tasks.size)

            tasks.withIndex().forEach { (index, task) ->
                dataMap.putInt("task_id_$index", task.id)
                dataMap.putString("task_name_$index", task.name)
                dataMap.putString("task_category_name_$index", task.category.name)
            }
        }.setUrgent().asPutDataRequest()

        dataClient.putDataItem(request)
    }
}