package com.lfp.gitscope.presentation.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lfp.gitscope.domain.model.RepositorySortOption
import com.lfp.gitscope.domain.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RepositoriesUiState())
    val uiState: StateFlow<RepositoriesUiState> = _uiState.asStateFlow()

    init {
        loadRepositories()
    }

    fun loadRepositories(
        language: String = "kotlin",
        sortOption: RepositorySortOption = RepositorySortOption.STARS
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            runCatching {
                repository.searchRepositories(
                    language = language,
                    sortOption = sortOption
                )
            }.onSuccess { repositories ->
                _uiState.value = _uiState.value.copy(
                    repositories = repositories,
                    isLoading = false,
                    errorMessage = null
                )
            }.onFailure { throwable ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = throwable.message ?: "Unable to load repositories"
                )
            }
        }
    }
}