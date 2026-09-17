package com.example.crud_firebase.ui.screen.drafts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crud_firebase.domain.model.TaskDraft
import com.example.crud_firebase.domain.repository.AuthRepository
import com.example.crud_firebase.domain.usecase.draft.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DraftUiState(
    val drafts: List<TaskDraft> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val publishSuccess: Boolean = false
)

@HiltViewModel
class DraftViewModel @Inject constructor(
    private val getDraftsUseCase: GetDraftsUseCase,
    private val saveDraftUseCase: SaveDraftUseCase,
    private val deleteDraftUseCase: DeleteDraftUseCase,
    private val publishDraftUseCase: PublishDraftUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DraftUiState())
    val uiState: StateFlow<DraftUiState> = _uiState.asStateFlow()

    init {
        authRepository.currentUserId?.let { observeDrafts(it) }
    }

    private fun observeDrafts(ownerId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getDraftsUseCase(ownerId)
                .catch { e -> _uiState.update { it.copy(errorMessage = e.message, isLoading = false) } }
                .collect { drafts ->
                    _uiState.update { it.copy(drafts = drafts, isLoading = false, errorMessage = null) }
                }
        }
    }

    fun saveDraft(title: String, description: String) {
        val ownerId = authRepository.currentUserId ?: return
        viewModelScope.launch {
            val draft = TaskDraft(ownerId = ownerId, title = title, description = description, savedAt = System.currentTimeMillis())
            saveDraftUseCase(draft)
        }
    }

    fun deleteDraft(draftId: Int) {
        viewModelScope.launch {
            deleteDraftUseCase(draftId)
        }
    }

    fun publishDraft(draft: TaskDraft) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, publishSuccess = false) }
            publishDraftUseCase(draft)
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false, publishSuccess = true) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
                }
        }
    }
    
    fun resetPublishState() {
        _uiState.update { it.copy(publishSuccess = false) }
    }
}