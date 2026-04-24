package com.lfp.gitscope.presentation.repositories

import androidx.lifecycle.ViewModel
import com.lfp.gitscope.data.mock.MockRepositories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RepositoriesViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(
        RepositoriesUiState(
            repositories = MockRepositories.repositories
        )
    )

    val uiState: StateFlow<RepositoriesUiState> = _uiState.asStateFlow()
}