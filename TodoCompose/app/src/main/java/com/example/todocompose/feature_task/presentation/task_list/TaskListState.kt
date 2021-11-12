package com.example.todocompose.feature_task.presentation.task_list

import com.example.todocompose.core.domain.model.TodoTask

data class TaskListState(
    val isLoading: Boolean = false,
    val tasks: List<TodoTask> = emptyList(),
    val error: String = ""
)
