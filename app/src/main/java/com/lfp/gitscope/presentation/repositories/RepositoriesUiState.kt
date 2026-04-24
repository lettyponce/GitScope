package com.lfp.gitscope.presentation.repositories

import com.lfp.gitscope.domain.model.Repository

data class RepositoriesUiState(
    val repositories: List<Repository> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)