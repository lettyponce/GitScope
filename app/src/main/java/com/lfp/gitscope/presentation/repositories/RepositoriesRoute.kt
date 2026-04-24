package com.lfp.gitscope.presentation.repositories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.lfp.gitscope.domain.model.Repository

@Composable
fun RepositoriesRoute(
    onClick: (Repository) -> Unit,
    viewModel: RepositoriesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    RepositoriesScreen(
        repositories = uiState.repositories,
        languageQuery = uiState.languageQuery,
        selectedSortOption = uiState.selectedSortOption,
        onLanguageQueryChanged = viewModel::onLanguageQueryChanged,
        onSortOptionSelected = viewModel::onSortOptionSelected,
        onRepositoryClick = onClick
    )
}