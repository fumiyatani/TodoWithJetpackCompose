package com.fumiyatani.todo.di

import com.fumiyatani.todo.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideDispatcherIo(): CoroutineContext = Dispatchers.IO

    @Singleton
    @Provides
    fun provideTaskRepository(
        dispatcherIo: CoroutineContext
    ): TaskRepository {
        return TaskRepository(dispatcherIo)
    }
}