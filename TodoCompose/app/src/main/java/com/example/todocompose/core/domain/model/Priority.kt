package com.example.todocompose.core.domain.model

import androidx.compose.ui.graphics.Color
import com.example.todocompose.core.presentation.ui.theme.HighPriorityColor
import com.example.todocompose.core.presentation.ui.theme.LowPriorityColor
import com.example.todocompose.core.presentation.ui.theme.MediumPriorityColor
import com.example.todocompose.core.presentation.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}
