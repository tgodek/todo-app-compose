package com.example.todocompose.feature_task.presentation.task

import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todocompose.R
import com.example.todocompose.feature_task.presentation.task.components.TaskAppBar
import com.example.todocompose.feature_task.presentation.task.components.TaskScreenContent
import com.example.todocompose.feature_task.presentation.util.UiEvent
import com.example.todocompose.feature_task.util.Action
import kotlinx.coroutines.flow.collectLatest

@ExperimentalComposeUiApi
@Composable
fun TaskScreen(
    navController: NavController,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val focusManager = LocalFocusManager.current
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.SnackbarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.uiText)
                }
                is UiEvent.Navigate -> {
                    focusManager.clearFocus()
                    navigateToTaskListScreen(navController, event.route)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (state.task == null) {
                TaskAppBar(
                    title = stringResource(id = R.string.new_task),
                    actions = listOf(Action.ADD),
                    onActionClick = { action ->
                        viewModel.onEvent(action)
                    }
                )
            } else {
                TaskAppBar(
                    title = stringResource(id = R.string.edit_task),
                    actions = listOf(Action.DELETE, Action.UPDATE),
                    onActionClick = { action ->
                        viewModel.onEvent(action)
                    }
                )
            }
        }
    ) {
        TaskScreenContent(
            title = viewModel.title.value,
            onTitleChange = {
                viewModel.setTitleState(it)
            },
            description = viewModel.description.value,
            onDescriptionChange = {
                viewModel.setDescriptionState(it)
            },
            priority = viewModel.priority.value,
            onPrioritySelected = {
                viewModel.setPriorityState(it)
            }
        )
    }
}

private fun navigateToTaskListScreen(navController: NavController, route: String) {
    navController.navigate(route) {
        popUpTo(route) { inclusive = true }
    }
}
