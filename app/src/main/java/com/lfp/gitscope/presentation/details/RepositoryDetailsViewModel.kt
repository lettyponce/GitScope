package com.lfp.gitscope.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lfp.gitscope.domain.repository.GithubRepository
import com.lfp.gitscope.presentation.navigation.Destinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class RepositoryDetailsViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val owner: String? = savedStateHandle[Destinations.OWNER_ARG]
    private val repoName: String? = savedStateHandle[Destinations.REPO_ARG]

    private val pendingTitle: String =
        listOfNotNull(owner?.takeIf { it.isNotBlank() }, repoName?.takeIf { it.isNotBlank() })
            .joinToString("/")
            .ifBlank { "Details" }

    private val _uiState = MutableStateFlow(RepositoryDetailsUiState(pendingTitle = pendingTitle))
    val uiState: StateFlow<RepositoryDetailsUiState> = _uiState.asStateFlow()

    init {
        loadRepositoryDetails()
    }

    private fun loadRepositoryDetails() {
        val safeOwner = owner
        val safeRepoName = repoName

        if (safeOwner.isNullOrBlank() || safeRepoName.isNullOrBlank()) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Invalid repository information"
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val repository = githubRepository.getRepositoryDetails(
                    owner = safeOwner,
                    repoName = safeRepoName
                )

                _uiState.value = _uiState.value.copy(
                    repository = repository,
                    isLoading = false,
                    errorMessage = null
                )

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                        ?: "Unable to load repository details"
                )
            }
        }
    }

    fun retryLoadRepositoryDetails() {
        loadRepositoryDetails()
    }
}