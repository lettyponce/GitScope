package com.lfp.gitscope.presentation.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.lfp.gitscope.presentation.components.EmptyContent
import com.lfp.gitscope.presentation.components.ErrorContent
import com.lfp.gitscope.presentation.components.LoadingContent

@Composable
fun RepositoryDetailsRoute(
    viewModel: RepositoryDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val errorMessage = uiState.errorMessage
    val repository = uiState.repository

    when {
        uiState.isLoading -> {
            LoadingContent()
        }

        errorMessage != null -> {
            ErrorContent(
                message = errorMessage,
                onRetryClick = viewModel::retryLoadRepositoryDetails
            )
        }

        repository != null -> {
            RepositoryDetailsScreen(repository = repository)
        }

        else -> {
            EmptyContent(message = "Repository details not available.")
        }
    }
}
