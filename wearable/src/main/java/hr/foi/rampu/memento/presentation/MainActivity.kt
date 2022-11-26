/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package hr.foi.rampu.memento.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import hr.foi.rampu.memento.presentation.models.Task

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp(
                listOf(
                    Task(0, "Task1", "Category1"),
                    Task(0, "Task2", "Category2"),
                    Task(0, "Task3", "Category3"),
                    Task(0, "Task4", "Category4"),
                    Task(0, "Task5", "Category5")
                )
            )
        }
    }
}