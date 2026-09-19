package com.example.crud_firebase.data.remote.model

import com.google.firebase.firestore.DocumentId

data class TaskDocument(
    @DocumentId
    val id: String = "",
    val ownerId: String = "",
    val title: String = "",
    val description: String = "",
    val completed: Boolean = false,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)