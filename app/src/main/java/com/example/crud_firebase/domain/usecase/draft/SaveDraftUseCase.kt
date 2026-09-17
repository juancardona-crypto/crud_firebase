package com.example.crud_firebase.domain.usecase.draft

import com.example.crud_firebase.domain.model.TaskDraft
import com.example.crud_firebase.domain.repository.DraftRepository
import javax.inject.Inject

class SaveDraftUseCase @Inject constructor(
    private val draftRepository: DraftRepository
) {
    suspend operator fun invoke(draft: TaskDraft): Result<Long> {
        if (draft.ownerId.isBlank()) {
            return Result.failure(IllegalArgumentException("Usuario no autenticado"))
        }
        if (draft.title.isBlank()) {
            return Result.failure(IllegalArgumentException("El título no puede estar vacío"))
        }
        return try {
            Result.success(draftRepository.saveDraft(draft))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}