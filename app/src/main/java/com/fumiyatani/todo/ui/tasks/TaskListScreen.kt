package com.fumiyatani.todo.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fumiyatani.todo.model.Task

@Composable
fun TaskListScreen(viewModel: TaskListViewModel = viewModel()) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onCreateTask("テスト", "テスト")
            }) {}
        }
    ) {
        TaskContent(
            viewModel.uiState.observeAsState().value?.tasks ?: emptyList(),
            onCheckedChange = { isCompleted, task ->
                if (isCompleted) {
                    viewModel.onFinishedTask(task)
                } else {
                    viewModel.onUnfinishedTask(task)
                }
            }
        )
    }
}

@Composable
fun TaskContent(tasks: List<Task>, onCheckedChange: (Boolean, Task) -> Unit) {
    LazyColumn(content = {
        items(tasks) { task ->
            TodoRow(
                task,
                onCheckedChange
            )
        }
    })
}

@Composable
fun TodoRow(task: Task, onCheckedChange: (Boolean, Task) -> Unit) {
    Row {
        Checkbox(checked = task.isCompleted, onCheckedChange = { onCheckedChange(it, task) })
        Column {
            Text(text = task.title)
            Text(text = task.detail)
        }
    }
}