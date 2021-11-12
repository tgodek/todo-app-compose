package com.example.todocompose.feature_task.presentation.task.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.todocompose.core.presentation.ui.theme.iconColor
import com.example.todocompose.feature_task.util.Action

@Composable
fun TaskAppBar(
    title: String,
    actions: List<Action>,
    onActionClick: (Action) -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = title, color = MaterialTheme.colors.iconColor)
        },
        navigationIcon = {
            IconButton(onClick = {onActionClick(Action.NO_ACTION)}) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back Arrow",
                    tint = MaterialTheme.colors.iconColor
                )
            }
        },
        actions = {
            for (action in actions) {
                IconButton(onClick = {onActionClick(action)}) {
                    Icon(
                        imageVector = when (action) {
                            Action.ADD -> Icons.Filled.Check
                            Action.DELETE -> Icons.Filled.Delete
                            Action.NO_ACTION -> Icons.Filled.ArrowBack
                            Action.UPDATE -> Icons.Filled.Save
                        },
                        contentDescription = "Action Icon",
                        tint = MaterialTheme.colors.iconColor
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp
    )
}