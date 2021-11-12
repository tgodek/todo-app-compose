package com.example.todocompose.feature_task.domain.use_case

import com.example.todocompose.core.domain.model.Priority
import com.example.todocompose.core.util.Resource
import com.example.todocompose.core.util.SimpleResource
import com.example.todocompose.feature_task.domain.repository.TaskRepository
import java.io.IOException
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String,
        priority: Priority,
        id: Int
    ): SimpleResource {
        return try {
            if (fieldsAreEmpty(title, description)){
                return Resource.Error("Fields are empty")
            }
            val task = repository.addTask(title, description, priority, id)
            Resource.Success(task)
        } catch (e: IOException) {
            Resource.Error("Something went wrong")
        }
    }

    private fun fieldsAreEmpty(title: String, description: String): Boolean {
        return title.isEmpty() || description.isEmpty()
    }

}