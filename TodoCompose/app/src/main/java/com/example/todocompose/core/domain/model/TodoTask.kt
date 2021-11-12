package com.example.todocompose.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todocompose.core.util.Constants.DATABASE_TODO_TABLE

@Entity(tableName = DATABASE_TODO_TABLE)
data class TodoTask(
    val title: String,
    val description: String,
    val priority: Priority,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
