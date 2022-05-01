package com.fumiyatani.todo.model

import com.fumiyatani.todo.repository.TaskRepository

class CreateTaskUseCase(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(task: Task) = taskRepository.save(task)
}