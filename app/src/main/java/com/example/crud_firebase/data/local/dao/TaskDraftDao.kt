package com.example.crud_firebase.data.local.dao

import androidx.room.*
import com.example.crud_firebase.data.local.entity.TaskDraftEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDraftDao {
    @Query("SELECT * FROM task_drafts WHERE ownerId = :ownerId ORDER BY savedAt DESC")
    fun observeDrafts(ownerId: String): Flow<List<TaskDraftEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDraft(draft: TaskDraftEntity): Long

    @Update
    suspend fun updateDraft(draft: TaskDraftEntity)

    @Query("DELETE FROM task_drafts WHERE id = :draftId")
    suspend fun deleteDraft(draftId: Int)

    @Query("SELECT * FROM task_drafts WHERE id = :draftId")
    suspend fun getDraftById(draftId: Int): TaskDraftEntity?
}