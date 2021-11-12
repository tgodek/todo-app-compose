package com.example.todocompose.core.util

sealed class Screen(val route: String) {
    object TaskListScreen: Screen("task_list_screen")
    object TaskScreen: Screen("task_screen")
}
