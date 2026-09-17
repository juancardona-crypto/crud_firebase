package com.example.crud_firebase.domain.usecase.draft

import com.example.crud_firebase.domain.model.TaskDraft
import com.example.crud_firebase.domain.repository.DraftRepository

class UpdateDraftUseCase(
    private val draftRepository: DraftRepository
) {
    suspend operator fun invoke(draft: TaskDraft): Result<Unit> {
        if (draft.id == 0) {
            return Result.failure(IllegalArgumentException("El borrador no tiene id"))
        }
        return try {
            draftRepository.updateDraft(draft)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}