package com.example.todocompose.feature_task.presentation.task_list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todocompose.core.domain.model.TodoTask
import com.example.todocompose.feature_task.presentation.task_list.components.*
import com.example.todocompose.feature_task.presentation.util.UiEvent
import com.example.todocompose.feature_task.util.Action
import kotlinx.coroutines.flow.collectLatest

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun TaskListScreen(
    navController: NavController,
    viewModel: TaskListViewModel = hiltViewModel(),
) {
    val state = viewModel.taskListState.value
    val scaffoldState = rememberScaffoldState()

    var myAction by rememberSaveable { mutableStateOf(viewModel.snackBarState.value) }

    LaunchedEffect(key1 = myAction) {
        myAction = when (myAction) {
            Action.ADD -> {
                scaffoldState.snackbarHostState.showSnackbar(message = "Task Added")
                Action.NO_ACTION
            }
            Action.UPDATE -> {
                scaffoldState.snackbarHostState.showSnackbar(message = "Task Updated")
                Action.NO_ACTION
            }
            Action.DELETE -> {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = "Task Deleted",
                    actionLabel = "UNDO"
                )
                undoDeleteTask(viewModel, snackbarResult)
                Action.NO_ACTION
            }
            else -> return@LaunchedEffect
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.SnackbarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.uiText)
                }
                is UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TaskListAppBar(viewModel = viewModel) },
        floatingActionButton = {
            TaskListFloatingActionButton(onClick = {
                viewModel.onEvent(TaskListEvent.AddTaskClicked)
            })
        }
    ) {
        TaskListScreenContent(
            state = state,
            onSwipeDelete = { viewModel.onEvent(TaskListEvent.DeleteTask(it)) },
            onTaskItemClick = { viewModel.onEvent(TaskListEvent.TaskClicked(it)) }
        )
    }
}

private fun undoDeleteTask(
    viewModel: TaskListViewModel,
    snackbarResult: SnackbarResult
) {
    if (snackbarResult == SnackbarResult.ActionPerformed) {
        viewModel.onEvent(TaskListEvent.UndoDelete)
    }
}


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
private fun TaskListScreenContent(
    state: TaskListState,
    onSwipeDelete: (TodoTask) -> Unit,
    onTaskItemClick: (TodoTask) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (state.tasks.isEmpty() && !state.isLoading) {
            EmptyList()
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 4.dp,
                    bottom = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(
                    items = state.tasks,
                    key = { _, item ->
                        item.hashCode()
                    }
                ) { _, task ->

                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                onSwipeDelete(task)
                            }
                            true
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        dismissThresholds = { FractionalThreshold(fraction = 0.2f) },
                        background = { SwipeTaskBackground() },
                        dismissContent = {
                            TodoTaskItem(
                                task = task,
                                onTaskItemClick = { onTaskItemClick(it) }
                            )
                        }
                    )
                }
            }
        }
    }
}