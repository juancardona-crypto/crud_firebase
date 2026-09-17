package com.example.crud_firebase.data.repository

import com.example.crud_firebase.data.local.dao.TaskDraftDao
import com.example.crud_firebase.data.mapper.toDomain
import com.example.crud_firebase.data.mapper.toEntity
import com.example.crud_firebase.domain.model.TaskDraft
import com.example.crud_firebase.domain.repository.DraftRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DraftRepositoryImpl @Inject constructor(
    private val taskDraftDao: TaskDraftDao
) : DraftRepository {

    override fun observeDrafts(ownerId: String): Flow<List<TaskDraft>> {
        return taskDraftDao.observeDrafts(ownerId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun saveDraft(draft: TaskDraft): Long {
        return taskDraftDao.insertDraft(draft.toEntity())
    }

    override suspend fun updateDraft(draft: TaskDraft) {
        taskDraftDao.updateDraft(draft.toEntity())
    }

    override suspend fun deleteDraft(draftId: Int) {
        taskDraftDao.deleteDraft(draftId)
    }

    override suspend fun getDraftById(draftId: Int): TaskDraft? {
        return taskDraftDao.getDraftById(draftId)?.toDomain()
    }
}