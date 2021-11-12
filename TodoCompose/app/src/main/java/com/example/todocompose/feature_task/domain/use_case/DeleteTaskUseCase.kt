package com.example.todocompose.feature_task.domain.use_case

import com.example.todocompose.core.domain.model.TodoTask
import com.example.todocompose.core.util.Resource
import com.example.todocompose.core.util.SimpleResource
import com.example.todocompose.feature_task.domain.repository.TaskRepository
import java.io.IOException
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    val repository: TaskRepository
) {
    suspend operator fun invoke(task: TodoTask) : SimpleResource {
        return try {
            val deleteTask = repository.deleteTask(task)
            Resource.Success(deleteTask)
        } catch (e: IOException) {
            Resource.Error("Something went wrong")
        }
    }
}