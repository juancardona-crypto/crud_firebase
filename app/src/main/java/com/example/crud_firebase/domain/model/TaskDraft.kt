package com.example.crud_firebase.domain.model

data class TaskDraft(
    val id: Int = 0,
    val ownerId: String = "",
    val title: String = "",
    val description: String = "",
    val savedAt: Long = 0L
)