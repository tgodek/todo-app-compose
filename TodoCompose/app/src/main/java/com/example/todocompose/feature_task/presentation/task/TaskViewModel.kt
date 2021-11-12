package com.example.todocompose.feature_task.presentation.task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todocompose.core.domain.model.Priority
import com.example.todocompose.core.util.Constants
import com.example.todocompose.core.util.Constants.MAX_TITLE_LENGTH
import com.example.todocompose.core.util.Resource
import com.example.todocompose.core.util.Screen
import com.example.todocompose.feature_task.domain.use_case.AddTaskUseCase
import com.example.todocompose.feature_task.domain.use_case.DeleteTaskUseCase
import com.example.todocompose.feature_task.domain.use_case.GetTaskUseCase
import com.example.todocompose.feature_task.presentation.util.UiEvent
import com.example.todocompose.feature_task.util.Action
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTaskUseCase: GetTaskUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _state = mutableStateOf(TaskState())
    val state: State<TaskState> = _state

    private val _title = mutableStateOf("")
    val title: State<String> = _title

    private val _description = mutableStateOf("")
    val description: State<String> = _description

    private val _priority = mutableStateOf(Priority.LOW)
    val priority: State<Priority> = _priority

    fun setTitleState(title: String) {
        if (title.length < MAX_TITLE_LENGTH) {
            _title.value = title
        }
    }

    fun setDescriptionState(description: String) {
        _description.value = description
    }

    fun setPriorityState(priority: Priority) {
        _priority.value = priority
    }

    init {
        savedStateHandle.get<String>(Constants.PARAM_TASK_ID)?.let { taskId ->
            getTask(taskId.toInt())
        }
    }

    fun onEvent(event: Action) {
        when (event) {
            Action.ADD -> handleTask(event)
            Action.UPDATE -> handleTask(event)
            Action.DELETE -> deleteTask(event)
            Action.NO_ACTION -> navigateBack(event)
        }
    }

    private fun navigateBack(event: Action) {
        viewModelScope.launch {
            navigate(event)
        }
    }

    private suspend fun makeSnackBar(message: String?) {
        _eventFlow.emit(UiEvent.SnackbarEvent(message ?: "Something went wrong"))
    }

    private suspend fun navigate(event: Action) {
        _eventFlow.emit(UiEvent.Navigate(Screen.TaskListScreen.route + "?${Constants.PARAM_ACTION}=${event.name}"))
    }

    private fun deleteTask(event: Action) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = deleteTaskUseCase(state.value.task!!)) {
                is Resource.Success -> navigate(event)
                is Resource.Error -> makeSnackBar(result.message)
            }
        }
    }

    private fun handleTask(event: Action) {
        viewModelScope.launch(Dispatchers.IO) {
            val taskId = state.value.task?.id ?: 0
            val result = addTaskUseCase(
                title = title.value,
                description = description.value,
                priority = priority.value,
                id = taskId
            )
            when (result) {
                is Resource.Success -> navigate(event)
                is Resource.Error -> makeSnackBar(result.message)
            }
        }
    }

    private fun getTask(taskId: Int) {
        viewModelScope.launch {
            when (val result = getTaskUseCase(taskId)) {
                is Resource.Success -> {
                    _state.value = state.value.copy(task = result.data)
                    _title.value = _state.value.task!!.title
                    _description.value = _state.value.task!!.description
                    _priority.value = _state.value.task!!.priority
                }
                is Resource.Error -> _state.value =
                    state.value.copy(error = result.message ?: "Something went wrong")
            }
        }
    }
}