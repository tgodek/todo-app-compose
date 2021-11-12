package com.example.todocompose.feature_task.domain.repository

import com.example.todocompose.core.domain.model.Priority
import com.example.todocompose.core.domain.model.TodoTask

interface TaskRepository {

    suspend fun addTask(title: String, description: String, priority: Priority, id: Int)

    suspend fun getTaskById(taskId: Int) : TodoTask

    suspend fun getAllTasks() : List<TodoTask>

    suspend fun deleteTask(task: TodoTask)

    suspend fun deleteAll()
}