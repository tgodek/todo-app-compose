package com.example.todocompose.feature_task.presentation.task_list.components

import androidx.compose.runtime.Composable
import com.example.todocompose.feature_task.presentation.task_list.TaskListViewModel
import com.example.todocompose.feature_task.presentation.task_list.SearchAppBarState
import com.example.todocompose.feature_task.presentation.task_list.TaskListEvent

@Composable
fun TaskListAppBar(
    viewModel: TaskListViewModel,
) {
    val searchBarState = viewModel.searchBarState.value
    val searchTextState = viewModel.searchTextState.value

    when (searchBarState) {
        is SearchAppBarState.OPEN -> {
            SearchTaskListAppBar(
                text = searchTextState,
                onTextChange = { userInput ->
                    viewModel.onEvent(TaskListEvent.EnteredSearchText(value = userInput))
                },
                onCloseClicked = {
                    viewModel.onEvent(TaskListEvent.SearchBarToggle(showSearchAppBar = false))
                    viewModel.onEvent(TaskListEvent.EnteredSearchText(value = ""))
                },
                onSearchClicked = {
                    viewModel.onEvent(TaskListEvent.SearchTaskClicked)
                }
            )
        }
        SearchAppBarState.CLOSE -> {
            DefaultTaskListAppBar(
                onSearchClicked = {
                    viewModel.onEvent(TaskListEvent.SearchBarToggle(showSearchAppBar = true))
                },
                onSortClicked = {
                    viewModel.onEvent(TaskListEvent.SearchTaskClicked)
                },
                onDeleteClicked = {
                    viewModel.onEvent(TaskListEvent.DeleteAllClicked)
                }
            )
        }
    }
}