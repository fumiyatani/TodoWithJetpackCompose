package com.fumiyatani.todo.repository

import com.fumiyatani.todo.model.Task
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class TaskRepository @Inject internal constructor(
    private val dispatcherIO: CoroutineContext,
) {
    private val tasks: MutableMap<String, Task> = mutableMapOf()

    suspend fun save(task: Task) = withContext(dispatcherIO) {
        tasks[task.id] = task
    }

    suspend fun getAll(): List<Task> = withContext(dispatcherIO) {
        tasks.values.toList()
    }
}