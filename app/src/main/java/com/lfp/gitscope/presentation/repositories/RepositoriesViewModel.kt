package com.lfp.gitscope.presentation.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lfp.gitscope.domain.model.RepositorySortOption
import com.lfp.gitscope.domain.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RepositoriesUiState())
    val uiState: StateFlow<RepositoriesUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null
    private var loadJob: Job? = null

    init {
        loadRepositories(
            language = _uiState.value.languageQuery,
            sortOption = _uiState.value.selectedSortOption
        )
    }

    fun onLanguageQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(
            languageQuery = query
        )

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            loadRepositories(
                language = _uiState.value.languageQuery,
                sortOption = _uiState.value.selectedSortOption
            )
        }
    }

    fun onSortOptionSelected(sortOption: RepositorySortOption) {
        _uiState.value = _uiState.value.copy(
            selectedSortOption = sortOption
        )

        loadRepositories(
            language = _uiState.value.languageQuery,
            sortOption = _uiState.value.selectedSortOption
        )
    }

    fun loadRepositories(
        language: String,
        sortOption: RepositorySortOption
    ) {
        loadJob?.cancel()

        val normalizedLanguage = language
            .trim()
            .ifBlank { "kotlin" }

        loadJob = viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            runCatching {
                repository.searchRepositories(
                    language = normalizedLanguage,
                    sortOption = sortOption
                )
            }.onSuccess { repositories ->
                _uiState.value = _uiState.value.copy(
                    repositories = repositories,
                    isLoading = false,
                    errorMessage = null
                )
            }.onFailure { throwable ->
                val errorMessage = when {
                    throwable is HttpException && throwable.code() == 403 ->
                        "GitHub request unavailable or access restricted. Please try again later."

                    else ->
                        throwable.message ?: "Unable to load repositories"
                }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = errorMessage
                )
            }
        }
    }
}