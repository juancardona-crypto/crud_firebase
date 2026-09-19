package com.example.crud_firebase.data.mapper

import com.example.crud_firebase.data.local.entity.TaskDraftEntity
import com.example.crud_firebase.data.remote.model.TaskDocument
import com.example.crud_firebase.domain.model.Task
import com.example.crud_firebase.domain.model.TaskDraft

// --- Room Mappers ---

fun TaskDraftEntity.toDomain(): TaskDraft {
    return TaskDraft(
        id = id,
        ownerId = ownerId,
        title = title,
        description = description,
        savedAt = savedAt
    )
}

fun TaskDraft.toEntity(): TaskDraftEntity {
    return TaskDraftEntity(
        id = id,
        ownerId = ownerId,
        title = title,
        description = description,
        savedAt = savedAt
    )
}

// --- Firestore Mappers ---

fun TaskDocument.toDomain(): Task {
    return Task(
        id = id,
        ownerId = ownerId,
        title = title,
        description = description,
        completed = completed,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Task.toDocument(): TaskDocument {
    return TaskDocument(
        id = id,
        ownerId = ownerId,
        title = title,
        description = description,
        completed = completed,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}