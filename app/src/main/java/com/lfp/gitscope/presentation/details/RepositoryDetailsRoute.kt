package com.lfp.gitscope.presentation.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RepositoryDetailsRoute(
    viewModel: RepositoryDetailsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val repository = uiState.repository

    if (repository != null) {
        RepoDetailsScreen(repository = repository)
    } else {
        Text(text = "Repository not found")
    }
}