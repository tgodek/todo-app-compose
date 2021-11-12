package com.example.todocompose.feature_task.presentation.task_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todocompose.core.domain.model.TodoTask
import com.example.todocompose.core.util.Constants
import com.example.todocompose.core.util.Resource
import com.example.todocompose.core.util.Screen
import com.example.todocompose.feature_task.domain.use_case.*
import com.example.todocompose.feature_task.presentation.util.UiEvent
import com.example.todocompose.feature_task.util.Action
import com.example.todocompose.feature_task.util.toAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteAllTasksUseCase: DeleteAllTasksUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _taskListState = mutableStateOf(TaskListState())
    val taskListState: State<TaskListState> = _taskListState

    private val _searchBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSE)
    val searchBarState: State<SearchAppBarState> = _searchBarState

    private val _searchTextState = mutableStateOf("")
    val searchTextState: State<String> = _searchTextState

    private val _snackBarState: MutableState<Action> = mutableStateOf(Action.NO_ACTION)
    val snackBarState: State<Action> = _snackBarState

    private var getTasksJob: Job? = null
    private var lastDeletedTask: TodoTask? = null

    init {
        getTasks()
        savedStateHandle.get<String>(Constants.PARAM_ACTION)?.let { userAction ->
            _snackBarState.value = userAction.toAction()
        }
    }

    fun onEvent(event: TaskListEvent) {
        when (event) {
            is TaskListEvent.SearchBarToggle -> {
                if (event.showSearchAppBar) _searchBarState.value = SearchAppBarState.OPEN
                else _searchBarState.value = SearchAppBarState.CLOSE
            }
            is TaskListEvent.DeleteAllClicked -> deleteAll()
            is TaskListEvent.SortTaskClicked -> TODO()
            is TaskListEvent.EnteredSearchText -> _searchTextState.value = event.value
            is TaskListEvent.SearchTaskClicked -> TODO()
            is TaskListEvent.TaskClicked -> taskClicked(event.task)
            is TaskListEvent.AddTaskClicked -> addTaskClicked()
            is TaskListEvent.DeleteTask -> deleteTask(event.task)
            is TaskListEvent.UndoDelete -> returnDeletedTask()
        }
    }

    private fun deleteTask(taskToDelete: TodoTask) {
        viewModelScope.launch {
            when (val result = deleteTaskUseCase(taskToDelete)) {
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.SnackbarEvent("Task Deleted"))
                    getTasks()
                }
                is Resource.Error -> _eventFlow.emit(UiEvent.SnackbarEvent(result.message ?: "Something went wrong"))
            }
        }
    }

    private fun addTaskClicked() {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.Navigate(Screen.TaskScreen.route + "?${Constants.PARAM_ACTION}=${Action.ADD.name}"))
        }
    }

    private fun taskClicked(task: TodoTask) {
        viewModelScope.launch {
            _eventFlow.emit(
                UiEvent.Navigate(Screen.TaskScreen.route + "?${Constants.PARAM_ACTION}=${Action.UPDATE.name}&${Constants.PARAM_TASK_ID}=${task.id}")
            )
        }
    }

    private fun returnDeletedTask() {
        viewModelScope.launch(Dispatchers.IO) {
            lastDeletedTask?.let {
                val result = addTaskUseCase(it.title, it.description, it.priority, it.id)
                when (result) {
                    is Resource.Success -> {
                        getTasks()
                        lastDeletedTask = null
                    }
                    is Resource.Error -> _eventFlow.emit(UiEvent.SnackbarEvent(result.message ?: "Something went wrong"))
                }

            }
        }
    }

    private fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = deleteAllTasksUseCase()) {
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.SnackbarEvent("All Tasks Deleted"))
                    getTasks()
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.SnackbarEvent(result.message ?: "Something went wrong"))
                }
            }
        }
    }

    private fun getTasks() {
        getTasksJob?.cancel()
        getTasksJob = getTasksUseCase().onEach { result ->
            _taskListState.value = taskListState.value.copy(isLoading = true)
            when (result) {
                is Resource.Success -> {
                    _taskListState.value = taskListState.value.copy(
                        isLoading = false,
                        tasks = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _taskListState.value = taskListState.value.copy(
                        isLoading = false,
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}