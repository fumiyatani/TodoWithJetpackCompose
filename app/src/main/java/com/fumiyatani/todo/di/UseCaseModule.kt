package com.fumiyatani.todo.di

import com.fumiyatani.todo.model.CompleteTaskUseCase
import com.fumiyatani.todo.model.CreateTaskUseCase
import com.fumiyatani.todo.model.GetTasksUseCase
import com.fumiyatani.todo.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {
    @Provides
    fun provideCreateTaskUseCase(taskRepository: TaskRepository): CreateTaskUseCase {
        return CreateTaskUseCase(taskRepository)
    }

    @Provides
    fun provideGetTasksUseCase(taskRepository: TaskRepository): GetTasksUseCase {
        return GetTasksUseCase(taskRepository)
    }

    @Provides
    fun provideCompleteTasksUseCase(taskRepository: TaskRepository): CompleteTaskUseCase {
        return CompleteTaskUseCase(taskRepository)
    }
}