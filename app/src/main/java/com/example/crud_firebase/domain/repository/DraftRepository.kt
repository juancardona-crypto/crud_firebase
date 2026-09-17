package com.example.crud_firebase.domain.repository

import com.example.crud_firebase.domain.model.TaskDraft
import kotlinx.coroutines.flow.Flow

interface DraftRepository {
    fun observeDrafts(ownerId: String): Flow<List<TaskDraft>>
    suspend fun saveDraft(draft: TaskDraft): Long
    suspend fun updateDraft(draft: TaskDraft)
    suspend fun deleteDraft(draftId: Int)
    suspend fun getDraftById(draftId: Int): TaskDraft?
}
