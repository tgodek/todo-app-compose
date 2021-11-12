package com.example.todocompose.feature_task.presentation.task_list.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todocompose.core.domain.model.Priority
import com.example.todocompose.core.domain.model.TodoTask
import com.example.todocompose.core.presentation.ui.theme.taskItemBackgroundColor
import com.example.todocompose.core.presentation.ui.theme.taskItemTextColor

@Composable
fun TodoTaskItem(
    task: TodoTask,
    onTaskItemClick: (TodoTask) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.taskItemBackgroundColor)
            .clickable { onTaskItemClick(task) },
        shape = MaterialTheme.shapes.medium,
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.weight(8f),
                        text = task.title,
                        color = MaterialTheme.colors.taskItemTextColor,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Canvas(
                            modifier = Modifier.size(16.dp),
                        ) {
                            drawCircle(color = task.priority.color)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = task.description, color = MaterialTheme.colors.taskItemTextColor,
                    style = MaterialTheme.typography.body2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
fun TodoTaskItemPreview() {
    val task by remember { mutableStateOf(TodoTask("task1", "This is a fake task", Priority.HIGH)) }
    TodoTaskItem(task = task, onTaskItemClick = {})
}