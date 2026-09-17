package com.example.crud_firebase.domain.usecase.draft

import com.example.crud_firebase.domain.model.TaskDraft
import com.example.crud_firebase.domain.repository.DraftRepository
import kotlinx.coroutines.flow.Flow

class GetDraftsUseCase(
    private val draftRepository: DraftRepository
) {
    operator fun invoke(ownerId: String): Flow<List<TaskDraft>> {
        return draftRepository.observeDrafts(ownerId)
    }
}