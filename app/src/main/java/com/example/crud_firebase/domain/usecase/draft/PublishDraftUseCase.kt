package com.example.crud_firebase.domain.usecase.draft

import com.example.crud_firebase.domain.model.Task
import com.example.crud_firebase.domain.model.TaskDraft
import com.example.crud_firebase.domain.repository.DraftRepository
import com.example.crud_firebase.domain.repository.TaskRepository

class PublishDraftUseCase(
    private val taskRepository: TaskRepository,
    private val draftRepository: DraftRepository
) {

    suspend operator fun invoke(draft: TaskDraft): Result<String> {
        if (draft.title.isBlank()) {
            return Result.failure(IllegalArgumentException("El título no puede estar vacío"))
        }

        val now = System.currentTimeMillis()
        val task = Task(
            id = "",
            ownerId = draft.ownerId,
            title = draft.title,
            description = draft.description,
            completed = false,
            createdAt = now,
            updatedAt = now
        )

        val remoteResult = taskRepository.createTask(task)
        
        return if (remoteResult.isSuccess) {
            // Paso 3: solo eliminar el borrador DESPUÉS del éxito remoto
            draftRepository.deleteDraft(draft.id)
            Result.success(remoteResult.getOrThrow())
        } else {
            // Paso 4: conservar el borrador y devolver el error
            Result.failure(remoteResult.exceptionOrNull() ?: Exception("Error al publicar"))
        }
    }
}