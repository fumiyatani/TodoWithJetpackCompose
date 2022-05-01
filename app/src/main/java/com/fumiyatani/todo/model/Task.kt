package com.fumiyatani.todo.model

import java.util.UUID

data class Task(
    private val _id: String = UUID.randomUUID().toString(),
    val title: String,
    val detail: String,
    val isCompleted: Boolean
) {
    val id: String
        get() = _id
}
