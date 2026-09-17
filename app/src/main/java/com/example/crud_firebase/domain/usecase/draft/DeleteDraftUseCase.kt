package com.example.crud_firebase.domain.usecase.draft

import com.example.crud_firebase.domain.repository.DraftRepository

class DeleteDraftUseCase(
    private val draftRepository: DraftRepository
) {
    suspend operator fun invoke(draftId: Int): Result<Unit> {
        return try {
            draftRepository.deleteDraft(draftId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}