package com.example.todocompose.di

import android.content.Context
import androidx.room.Room
import com.example.todocompose.core.util.Constants.DATABASE_NAME
import com.example.todocompose.core.data.local.TodoDatabase
import com.example.todocompose.feature_task.data.repository.FakeTaskRepositoryImpl
import com.example.todocompose.feature_task.domain.repository.TaskRepository
import com.example.todocompose.feature_task.domain.use_case.AddTaskUseCase
import com.example.todocompose.feature_task.domain.use_case.DeleteAllTasksUseCase
import com.example.todocompose.feature_task.domain.use_case.DeleteTaskUseCase
import com.example.todocompose.feature_task.domain.use_case.GetTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        TodoDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: TodoDatabase) = database.todoDao()

    @Singleton
    @Provides
    fun provideTaskRepository() : TaskRepository {
        return FakeTaskRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideGetTaskUseCase(repository: TaskRepository): GetTaskUseCase {
        return GetTaskUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddTaskUseCase(repository: TaskRepository): AddTaskUseCase {
        return AddTaskUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteTaskUseCase(repository: TaskRepository): DeleteTaskUseCase {
        return DeleteTaskUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteAllTaskUseCase(repository: TaskRepository): DeleteAllTasksUseCase {
        return DeleteAllTasksUseCase(repository)
    }

}