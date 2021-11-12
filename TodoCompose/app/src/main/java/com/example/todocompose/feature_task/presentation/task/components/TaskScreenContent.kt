package com.example.todocompose.feature_task.presentation.task.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todocompose.R
import com.example.todocompose.core.domain.model.Priority
import com.example.todocompose.core.presentation.ui.theme.inputTextColor

@Composable
fun TaskScreenContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(12.dp),
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background),
            value = title,
            onValueChange = { onTitleChange(it) },
            label = { Text(text = stringResource(id = R.string.title)) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colors.inputTextColor
            )
        )
        Divider(
            modifier = Modifier
                .height(8.dp),
            color = MaterialTheme.colors.background
        )
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = { onPrioritySelected(it) }
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = { Text(text = stringResource(id = R.string.description)) },
            textStyle = MaterialTheme.typography.body1,
        )
    }
}

@Preview
@Composable
fun TaskScreenContentPreview() {
    TaskScreenContent(
        title = "",
        onTitleChange = {},
        description = "",
        onDescriptionChange = {},
        priority = Priority.LOW,
        onPrioritySelected = {}
    )
}