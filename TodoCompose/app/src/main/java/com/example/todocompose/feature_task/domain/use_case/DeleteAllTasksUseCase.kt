package com.example.todocompose.feature_task.domain.use_case

import com.example.todocompose.core.util.Resource
import com.example.todocompose.core.util.SimpleResource
import com.example.todocompose.feature_task.domain.repository.TaskRepository
import java.io.IOException
import javax.inject.Inject

class DeleteAllTasksUseCase @Inject constructor(
    val taskRepository: TaskRepository
) {
    suspend operator fun invoke() : SimpleResource {
        return try {
            val result = taskRepository.deleteAll()
            Resource.Success(result)
        } catch (e: IOException) {
            Resource.Error("Something went wrong")
        }
    }
}