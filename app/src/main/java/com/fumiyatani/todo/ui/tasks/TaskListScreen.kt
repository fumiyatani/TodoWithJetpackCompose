package com.fumiyatani.todo.ui.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fumiyatani.todo.model.Task
import com.fumiyatani.todo.ui.component.TodoTopAppBar

@ExperimentalMaterialApi
@Composable
fun TaskListScreen(viewModel: TaskListViewModel = viewModel()) {
    val titleTextState = remember { mutableStateOf(TextFieldValue()) }
    val detailTextState = remember { mutableStateOf(TextFieldValue()) }
    val openBottomSheet = remember { mutableStateOf(false) }
    val checkedDisplayModeState = viewModel.uiState.observeAsState().value?.displayMode ?: DisplayMode.ALL
    val tasksState = viewModel.uiState.observeAsState().value?.tasks ?: emptyList()

    Scaffold(
        topBar = {
            TodoTopAppBar(
                title = "タスク管理",
                onClickMenuIcon = { displayMode ->
                    viewModel.onSelectedDisplayMode(displayMode)
                },
                checkedDisplayMode = checkedDisplayModeState
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { openBottomSheet.value = true }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        TaskContent(
            tasks = tasksState,
            onCheckedChange = { isCompleted, task ->
                if (isCompleted) {
                    viewModel.onFinishedTask(task)
                } else {
                    viewModel.onUnfinishedTask(task)
                }
            },
        )
    }

    if (openBottomSheet.value) {
        TaskFormBottomSheet(
            titleTextState = titleTextState,
            detailTextState = detailTextState,
            onTitleTextChange = { titleTextState.value = it },
            onDetailTextChange = { detailTextState.value = it },
            onTaskCreate = {
                viewModel.onCreateTask(
                    titleTextState.value.text,
                    detailTextState.value.text,
                )
                openBottomSheet.value = false
            },
        )
    }
}

@Composable
fun TaskContent(
    tasks: List<Task>,
    onCheckedChange: (Boolean, Task) -> Unit,
) {
    LazyColumn {
        items(tasks) { task ->
            TodoRow(
                task,
                onCheckedChange
            )
        }
    }
}

@Composable
fun TodoRow(
    task: Task,
    onCheckedChange: (Boolean, Task) -> Unit,
) {
    Row {
        Checkbox(checked = task.isCompleted, onCheckedChange = { onCheckedChange(it, task) })
        Column {
            Text(text = task.title)
            Text(text = task.detail)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TaskFormBottomSheet(
    titleTextState: State<TextFieldValue>,
    detailTextState: State<TextFieldValue>,
    onTitleTextChange: (TextFieldValue) -> Unit,
    onDetailTextChange: (TextFieldValue) -> Unit,
    onTaskCreate: () -> Unit,
) {
    BottomSheetScaffold(
        sheetContent = {}
    ) {
        TaskForm(
            titleTextState = titleTextState,
            detailTextState = detailTextState,
            onTitleTextChange = onTitleTextChange,
            onDetailTextChange = onDetailTextChange,
            onTaskCreate = onTaskCreate,
        )
    }
}

@Composable
fun TaskForm(
    titleTextState: State<TextFieldValue>,
    detailTextState: State<TextFieldValue>,
    onTitleTextChange: (TextFieldValue) -> Unit,
    onDetailTextChange: (TextFieldValue) -> Unit,
    onTaskCreate: () -> Unit,
) {
    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = titleTextState.value,
            onValueChange = {
                onTitleTextChange(it)
            }
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = detailTextState.value,
            onValueChange = {
                onDetailTextChange(it)
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )
        Button(
            onClick = onTaskCreate
        ) {
            Text(text = "保存")
        }
    }
}