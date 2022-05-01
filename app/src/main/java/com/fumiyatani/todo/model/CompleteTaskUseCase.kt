package com.fumiyatani.todo.model

import com.fumiyatani.todo.repository.TaskRepository

class CompleteTaskUseCase(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(task: Task) {
        val completedTask = task.copy(isCompleted = true)
        taskRepository.save(completedTask)
    }
}