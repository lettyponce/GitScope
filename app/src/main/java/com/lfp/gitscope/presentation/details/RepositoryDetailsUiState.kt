package com.lfp.gitscope.presentation.details

import com.lfp.gitscope.domain.model.Repository

data class RepositoryDetailsUiState(
    val repository: Repository? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val pendingTitle: String = "Details"
)
