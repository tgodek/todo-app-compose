package com.example.todocompose.feature_task.domain.use_case

import com.example.todocompose.core.util.Resource
import com.example.todocompose.core.domain.model.TodoTask
import com.example.todocompose.feature_task.domain.repository.TaskRepository
import java.io.IOException
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: Int): Resource<TodoTask> {
        val task = taskRepository.getTaskById(taskId)
        return try {
            Resource.Success(task)
        }catch (e: IOException){
            Resource.Error("Something went wrong")
        }
    }
}