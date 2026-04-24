package com.lfp.gitscope.presentation.repositories

import com.lfp.gitscope.domain.model.Repository
import com.lfp.gitscope.domain.model.RepositorySortOption

data class RepositoriesUiState(
    val repositories: List<Repository> = emptyList(),
    val isLoading: Boolean = false,
    val languageQuery: String = "kotlin",
    val selectedSortOption: RepositorySortOption = RepositorySortOption.STARS,
    val errorMessage: String? = null
)