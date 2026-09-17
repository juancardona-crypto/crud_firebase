package com.example.crud_firebase.domain.model

import com.google.firebase.Timestamp

data class Task(
    val id: String = "",
    val ownerId: String = "",
    val title: String = "",
    val description: String = "",
    val completed: Boolean = false,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)