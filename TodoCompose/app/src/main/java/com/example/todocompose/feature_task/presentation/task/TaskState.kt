package com.example.todocompose.feature_task.presentation.task

import com.example.todocompose.core.domain.model.TodoTask

data class TaskState(
    val task: TodoTask? = null,
    val error: String = "",
)
