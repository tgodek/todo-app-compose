package com.example.todocompose.feature_task.data.repository

import com.example.todocompose.core.data.local.TodoDao
import com.example.todocompose.core.domain.model.Priority
import com.example.todocompose.core.domain.model.TodoTask
import com.example.todocompose.feature_task.domain.repository.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val dao: TodoDao) : TaskRepository {

    override suspend fun addTask(title: String, description: String, priority: Priority, id: Int) {
        dao.addTask(TodoTask(title, description, priority, id))
    }

    override suspend fun getTaskById(taskId: Int): TodoTask {
        return dao.getSelectedTask(taskId)
    }

    override suspend fun getAllTasks(): List<TodoTask> {
        return dao.getAllTasks()
    }

    override suspend fun deleteTask(task: TodoTask) {
        return dao.deleteTask(task)
    }

    override suspend fun deleteAll() {
        dao.deleteAllTasks()
    }
}