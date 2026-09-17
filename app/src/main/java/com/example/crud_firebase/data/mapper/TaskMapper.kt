package com.example.crud_firebase.data.mapper

import com.example.crud_firebase.data.local.entity.TaskDraftEntity
import com.example.crud_firebase.domain.model.TaskDraft

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