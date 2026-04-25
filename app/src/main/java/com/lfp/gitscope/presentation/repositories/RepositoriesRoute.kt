package com.lfp.gitscope.presentation.repositories

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.lfp.gitscope.data.mock.MockRepositories
import com.lfp.gitscope.domain.model.Repository
import com.lfp.gitscope.domain.model.RepositorySortOption
import com.lfp.gitscope.presentation.theme.GitScopeTheme

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
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        onRetryClick = {
            viewModel.loadRepositories(
                language = uiState.languageQuery,
                sortOption = uiState.selectedSortOption
            )
        },
        onLanguageQueryChanged = viewModel::onLanguageQueryChanged,
        onSortOptionSelected = viewModel::onSortOptionSelected,
        onRepositoryClick = onClick
    )
}

@Preview(
    showBackground = true,
    name = "Repositories route",
    widthDp = 411,
    heightDp = 891
)
@Composable
private fun RepositoriesRoutePreview() {
    GitScopeTheme(dynamicColor = false) {
        RepositoriesScreen(
            repositories = MockRepositories.repositories,
            languageQuery = "kotlin",
            selectedSortOption = RepositorySortOption.STARS,
            isLoading = false,
            errorMessage = null,
            onRetryClick = {},
            onLanguageQueryChanged = {},
            onSortOptionSelected = {},
            onRepositoryClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "Repositories route (dark)",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 411,
    heightDp = 891
)
@Composable
private fun RepositoriesRoutePreviewDark() {
    GitScopeTheme(dynamicColor = false) {
        RepositoriesScreen(
            repositories = MockRepositories.repositories.take(2),
            languageQuery = "go",
            selectedSortOption = RepositorySortOption.FORKS,
            isLoading = false,
            errorMessage = null,
            onRetryClick = {},
            onLanguageQueryChanged = {},
            onSortOptionSelected = {},
            onRepositoryClick = {}
        )
    }
}