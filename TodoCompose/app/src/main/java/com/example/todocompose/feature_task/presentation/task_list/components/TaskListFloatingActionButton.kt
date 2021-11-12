package com.example.todocompose.feature_task.presentation.task_list.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import com.example.todocompose.core.presentation.ui.theme.fabIconColor

@Composable
fun TaskListFloatingActionButton(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Fab Icon",
            tint = MaterialTheme.colors.fabIconColor
        )
    }
}