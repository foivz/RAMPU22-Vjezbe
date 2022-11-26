package hr.foi.rampu.memento.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.wear.compose.material.*
import hr.foi.rampu.memento.R
import hr.foi.rampu.memento.presentation.models.Task
import hr.foi.rampu.memento.presentation.theme.MementoBlue
import hr.foi.rampu.memento.presentation.theme.MementoDarkerBlue
import hr.foi.rampu.memento.presentation.theme.MementoTheme

@Composable
fun WearApp(tasks: List<Task>) {

    val listState = rememberScalingLazyListState()

    MementoTheme {
        Scaffold(positionIndicator = { PositionIndicator(scalingLazyListState = listState) },
            vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) }) {
            if (tasks.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
                Column(
                    modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        stringResource(R.string.msg_wait_tasks_syncing),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                ScalingLazyColumn {
                    items(tasks) { task ->
                        TaskTexts(task)
                    }
                }
            }
        }
    }
}


@Composable
fun TaskTexts(
    task: Task
) {
    Card(onClick = {}) {
        Column {
            Text(
                task.name, style = MaterialTheme.typography.title3, color = MementoBlue
            )
            Text(
                task.categoryName, style = MaterialTheme.typography.body2, color = MementoDarkerBlue
            )
        }
    }
}