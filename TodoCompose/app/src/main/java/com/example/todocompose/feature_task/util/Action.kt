package com.example.todocompose.feature_task.util

enum class Action {
    ADD,
    UPDATE,
    DELETE,
    NO_ACTION
}


fun String.toAction() : Action {
    return when(this) {
        "ADD" -> Action.ADD
        "UPDATE" -> Action.UPDATE
        "DELETE" -> Action.DELETE
        else -> Action.NO_ACTION
    }
}