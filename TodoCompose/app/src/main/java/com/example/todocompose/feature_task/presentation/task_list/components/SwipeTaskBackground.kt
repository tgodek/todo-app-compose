package com.example.todocompose.feature_task.presentation.task_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todocompose.core.presentation.ui.theme.HighPriorityColor


@Composable
fun SwipeTaskBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HighPriorityColor)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "Delete Task",
            tint = Color.White
        )
    }
}