package com.example.todocompose.feature_task.presentation.util

sealed class UiEvent {
    data class SnackbarEvent(val uiText: String, val uiActionLabel: String? = null) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
}