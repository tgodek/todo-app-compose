package com.example.todocompose.feature_task.data.repository

import android.util.Log
import com.example.todocompose.core.domain.model.Priority
import com.example.todocompose.core.domain.model.TodoTask
import com.example.todocompose.feature_task.domain.repository.TaskRepository

class FakeTaskRepositoryImpl : TaskRepository {

    private val emptyTask = TodoTask("", "", Priority.NONE)

    private val fakeTodoList = mutableListOf(
        TodoTask("Fake Task 1", "This is a fake task", Priority.HIGH, 1),
        TodoTask("Fake Task 2", "This is a fake task", Priority.MEDIUM, 2),
        TodoTask("Fake Task 3", "This is a fake task", Priority.LOW, 3),
        TodoTask("Fake Task 4", "This is a fake task", Priority.HIGH, 4),
        TodoTask("Fake Task 5", "This is a fake task", Priority.MEDIUM, 5),
        TodoTask("Fake Task 6", "This is a fake task", Priority.LOW, 6),
        TodoTask("Fake Task 7", "This is a fake task", Priority.HIGH, 7),
        TodoTask("Fake Task 8", "This is a fake task", Priority.MEDIUM, 8),
        TodoTask("Fake Task 9", "This is a fake task", Priority.LOW, 9),
        TodoTask("Fake Task 10", "This is a fake task", Priority.LOW, 10)
    )

    override suspend fun addTask(title: String, description: String, priority: Priority, id: Int) {
        if (id != 0) {
            val newTask = TodoTask(title, description, priority, id)
            val oldTask = fakeTodoList.find {
                it.id == newTask.id
            }
            Log.d("oldTask", oldTask.toString())
            Log.d("newTask", newTask.toString())
            fakeTodoList.remove(oldTask)
            fakeTodoList.add(newTask)
        } else {
            val task = TodoTask(title, description, priority, fakeTodoList.size+10)
            fakeTodoList.add(task)

        }
    }

    override suspend fun getTaskById(taskId: Int): TodoTask {
        val task = fakeTodoList.find { todoTask ->
            todoTask.id == taskId
        }
        return task ?: emptyTask
    }

    override suspend fun getAllTasks(): List<TodoTask> {
        fakeTodoList.sortByDescending {
            it.id
        }
        return fakeTodoList
    }

    override suspend fun deleteTask(task: TodoTask) {
        fakeTodoList.remove(task)
    }

    override suspend fun deleteAll() {
        fakeTodoList.removeAll(fakeTodoList)
    }
}