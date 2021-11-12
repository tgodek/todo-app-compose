package com.example.todocompose.feature_task.presentation.task_list


sealed class SearchAppBarState {
    object OPEN : SearchAppBarState()
    object CLOSE : SearchAppBarState()
}


