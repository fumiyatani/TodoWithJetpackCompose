package com.fumiyatani.todo.model

import com.fumiyatani.todo.repository.TaskRepository

class GetTasksUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(): List<Task> = taskRepository.getAll()
}