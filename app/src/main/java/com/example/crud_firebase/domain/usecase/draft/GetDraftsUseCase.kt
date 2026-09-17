package com.example.crud_firebase.domain.usecase.draft

import com.example.crud_firebase.domain.model.TaskDraft
import com.example.crud_firebase.domain.repository.DraftRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDraftsUseCase @Inject constructor(
    private val draftRepository: DraftRepository
) {
    operator fun invoke(ownerId: String): Flow<List<TaskDraft>> {
        return draftRepository.observeDrafts(ownerId)
    }
}