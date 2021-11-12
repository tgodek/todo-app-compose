package com.example.todocompose.feature_task.presentation.task_list

import com.example.todocompose.core.domain.model.TodoTask
import com.example.todocompose.feature_task.util.Action

sealed class TaskListEvent {
    data class EnteredSearchText(val value: String) : TaskListEvent()
    data class SearchBarToggle(val showSearchAppBar: Boolean) : TaskListEvent()
    data class SortTaskClicked(val action: Action) : TaskListEvent()
    data class TaskClicked(val task: TodoTask) : TaskListEvent()
    data class DeleteTask(val task: TodoTask) : TaskListEvent()
    object AddTaskClicked : TaskListEvent()
    object DeleteAllClicked : TaskListEvent()
    object SearchTaskClicked : TaskListEvent()
    object UndoDelete : TaskListEvent()
}
