package com.example.todocompose.feature_task.presentation.task_list.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todocompose.R
import com.example.todocompose.core.domain.model.Priority
import com.example.todocompose.core.presentation.ui.theme.iconColor

@Composable
fun DefaultTaskListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.task_list_title))
        },
        backgroundColor = MaterialTheme.colors.background,
        actions = {
            SearchAction(onSearchClicked = onSearchClicked)
            SortAction(onSortClicked = onSortClicked)
            DeleteAllAction(onDeleteClicked = onDeleteClicked)
        },
        elevation = 0.dp
    )
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {
    IconButton(onClick = onSearchClicked) {
        Icon(
            imageVector = Icons.Filled.Search,
            tint = MaterialTheme.colors.iconColor,
            contentDescription = stringResource(id = R.string.search_action)
        )
    }
}

@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.Sort,
            tint = MaterialTheme.colors.iconColor,
            contentDescription = stringResource(id = R.string.sort_action)
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.LOW)
                }
            ) {
                PriorityItem(priority = Priority.LOW)
            }

            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.MEDIUM)
                }
            ) {
                PriorityItem(priority = Priority.MEDIUM)
            }

            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.HIGH)
                }
            ) {
                PriorityItem(priority = Priority.HIGH)
            }

            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.NONE)
                }
            ) {
                PriorityItem(priority = Priority.NONE)
            }
        }
    }
}

@Composable
fun DeleteAllAction(
    onDeleteClicked: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            tint = MaterialTheme.colors.iconColor,
            contentDescription = stringResource(id = R.string.delete_all_action)
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onDeleteClicked()
                },
            ) {
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = stringResource(
                        id = R.string.delete_all_action,
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
fun DefaultTaskListAppBarPreview() {
    DefaultTaskListAppBar(onSearchClicked = {}, onSortClicked = {}, onDeleteClicked = {})
}