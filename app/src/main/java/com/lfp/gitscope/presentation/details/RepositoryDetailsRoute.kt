package com.lfp.gitscope.presentation.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RepositoryDetailsRoute(
    viewModel: RepositoryDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val repository = uiState.repository

    if (repository != null) {
        RepositoryDetailsScreen(repository = repository)
    } else {
        Text(text = uiState.errorMessage ?: "Loading repository...")
    }
}