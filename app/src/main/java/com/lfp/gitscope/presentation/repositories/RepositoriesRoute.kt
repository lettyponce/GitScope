package com.lfp.gitscope.presentation.repositories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lfp.gitscope.domain.model.Repository

@Composable
fun RepositoriesRoute(
    onClick: (Repository) -> Unit,
    viewModel: RepositoriesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    RepositoriesScreen(
        repositories = uiState.repositories,
        onRepositoryClick = onClick
    )
}