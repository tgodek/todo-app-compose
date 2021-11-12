package com.example.todocompose.feature_task.domain.use_case

import com.example.todocompose.core.util.Resource
import com.example.todocompose.core.domain.model.TodoTask
import com.example.todocompose.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<Resource<List<TodoTask>>> = flow {
        try {
            val tasks = repository.getAllTasks()
            emit(Resource.Success<List<TodoTask>>(tasks))
        } catch (e: IOException) {
            emit(Resource.Error<List<TodoTask>>("Something went wrong"))
        }
    }
}