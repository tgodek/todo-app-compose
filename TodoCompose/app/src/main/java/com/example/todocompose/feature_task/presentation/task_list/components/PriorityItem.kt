package com.example.todocompose.feature_task.presentation.task_list.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todocompose.core.domain.model.Priority

@Composable
fun PriorityItem(priority: Priority) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.foundation.Canvas(modifier = Modifier.size(16.dp)){
            if(priority == Priority.NONE){
                drawCircle(color = Color.DarkGray)
            } else {
                drawCircle(color = priority.color)
            }
        }
        Text(
            modifier = Modifier
                .padding(start = 12.dp),
            text = priority.name,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
@Preview
fun PriorityItemPreview(){
    PriorityItem(priority = Priority.NONE)
}