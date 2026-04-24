package com.lfp.gitscope.presentation.details
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.lfp.gitscope.data.mock.MockRepositories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RepositoryDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val repoId: Long? = savedStateHandle["repoId"]

    private val selectedRepository = MockRepositories.repositories
        .firstOrNull { repository -> repository.id == repoId }

    private val _uiState = MutableStateFlow(
        RepositoryDetailsUiState(
            repository = selectedRepository
        )
    )

    val uiState: StateFlow<RepositoryDetailsUiState> = _uiState.asStateFlow()
}