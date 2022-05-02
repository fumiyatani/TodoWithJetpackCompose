package com.fumiyatani.todo.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fumiyatani.todo.model.CompleteTaskUseCase
import com.fumiyatani.todo.model.CreateTaskUseCase
import com.fumiyatani.todo.model.GetTasksUseCase
import com.fumiyatani.todo.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
) : ViewModel() {
    private val _uiState: MutableLiveData<TaskListState> = MutableLiveData(TaskListState())
    val uiState: LiveData<TaskListState> = _uiState

    init {
        viewModelScope.launch {
            if (_uiState.value != null) {
                val tasks = getTasksUseCase()
                _uiState.value = _uiState.value!!.copy(tasks = tasks)
            }
        }
    }

    fun onCreateTask(
        title: String,
        detail: String
    ) {
        viewModelScope.launch {
            val task = Task(
                title = title,
                detail = detail,
                isCompleted = false
            )
            createTaskUseCase(task)
            if (_uiState.value != null) {
                val uiState = _uiState.value!!
                val tasks = getTasksUseCase()
                _uiState.value = uiState.copy(tasks = tasks)
            }
        }
    }

    fun onFinishedTask(task: Task) {
        viewModelScope.launch {
            val finishedTask = task.copy(isCompleted = true)
            completeTaskUseCase(finishedTask)
            if (_uiState.value != null) {
                val uiState = _uiState.value!!
                val tasks = getTasksUseCase()
                _uiState.value = uiState.copy(tasks = tasks)
            }
        }
    }

    fun onUnfinishedTask(task: Task) {
        viewModelScope.launch {
            val finishedTask = task.copy(isCompleted = false)
            completeTaskUseCase(finishedTask)
            if (_uiState.value != null) {
                val uiState = _uiState.value!!
                val tasks = getTasksUseCase()
                _uiState.value = uiState.copy(tasks = tasks)
            }
        }
    }

    fun onSelectedDisplayMode(displayMode: DisplayMode) {
        viewModelScope.launch {
            if (_uiState.value != null) {
                val tasks = getTasksUseCase()
                    .filter { task ->
                        when (displayMode) {
                            DisplayMode.Finish -> task.isCompleted
                            DisplayMode.Unfinished -> !task.isCompleted
                            DisplayMode.ALL -> true
                        }
                    }
                _uiState.value = TaskListState(
                    tasks = tasks,
                    displayMode = displayMode
                )
            }
        }
    }
}

data class TaskListState(
    val tasks: List<Task> = emptyList(),
    val displayMode: DisplayMode = DisplayMode.ALL
)

enum class DisplayMode {
    Finish,
    Unfinished,
    ALL
}