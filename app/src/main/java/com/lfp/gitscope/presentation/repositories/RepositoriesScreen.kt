package com.lfp.gitscope.presentation.repositories

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lfp.gitscope.data.mock.MockRepositories
import com.lfp.gitscope.domain.model.Repository
import com.lfp.gitscope.domain.model.RepositorySortOption
import com.lfp.gitscope.presentation.components.EmptyContent
import com.lfp.gitscope.presentation.components.ErrorContent
import com.lfp.gitscope.presentation.components.LoadingContent
import com.lfp.gitscope.presentation.theme.GitScopeTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoriesScreen(
    repositories: List<Repository>,
    languageQuery: String,
    selectedSortOption: RepositorySortOption,
    isLoading: Boolean,
    errorMessage: String?,
    onRetryClick: () -> Unit,
    onLanguageQueryChanged: (String) -> Unit,
    onSortOptionSelected: (RepositorySortOption) -> Unit,
    onRepositoryClick: (Repository) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Github Repositories")
                },
                actions = {
                    SortOptionsMenu(
                        selectedSortOption = selectedSortOption,
                        onSortOptionSelected = onSortOptionSelected
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LanguageSearchField(
                value = languageQuery,
                onValueChange = onLanguageQueryChanged,
                modifier = Modifier.padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
            )
            Text(
                text = "Searching by: $languageQuery | Sort: ${selectedSortOption.label}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            when {
                isLoading -> {
                    LoadingContent()
                }

                errorMessage != null -> {
                    ErrorContent(
                        message = errorMessage,
                        onRetryClick = onRetryClick
                    )
                }

                repositories.isEmpty() -> {
                    EmptyContent(
                        message = "No repositories found for \"$languageQuery\"."
                    )
                }

                else -> {
                    RepositoriesGrid(
                        repositories = repositories,
                        onRepositoryClick = onRepositoryClick
                    )
                }
            }
        }
    }
}

@Composable
private fun RepositoriesGrid(
    repositories: List<Repository>,
    onRepositoryClick: (Repository) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = repositories,
            key = { repository -> repository.id }
        ) { repository ->
            RepositoryItem(
                repo = repository,
                onClick = { onRepositoryClick(repository) }
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "Repositories screen",
    widthDp = 411,
    heightDp = 891
)
@Composable
private fun RepositoriesScreenPreview() {
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
    name = "Repositories screen (dark)",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 411,
    heightDp = 891
)
@Composable
private fun RepositoriesScreenPreviewDark() {
    GitScopeTheme(dynamicColor = false) {
        RepositoriesScreen(
            repositories = MockRepositories.repositories.take(3),
            languageQuery = "Rust, TypeScript",
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

@Preview(
    showBackground = true,
    name = "Repositories screen (empty)",
    widthDp = 411,
    heightDp = 891
)
@Composable
private fun RepositoriesScreenPreviewEmpty() {
    GitScopeTheme(dynamicColor = false) {
        RepositoriesScreen(
            repositories = emptyList(),
            languageQuery = "",
            selectedSortOption = RepositorySortOption.UPDATED,
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
    name = "Repositories screen (loading)",
    widthDp = 411,
    heightDp = 891
)
@Composable
private fun RepositoriesScreenPreviewLoading() {
    GitScopeTheme(dynamicColor = false) {
        RepositoriesScreen(
            repositories = emptyList(),
            languageQuery = "kotlin",
            selectedSortOption = RepositorySortOption.STARS,
            isLoading = true,
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
    name = "Repositories screen (error)",
    widthDp = 411,
    heightDp = 891
)
@Composable
private fun RepositoriesScreenPreviewError() {
    GitScopeTheme(dynamicColor = false) {
        RepositoriesScreen(
            repositories = emptyList(),
            languageQuery = "kotlin",
            selectedSortOption = RepositorySortOption.STARS,
            isLoading = false,
            errorMessage = "Could not reach GitHub. Check your connection and try again.",
            onRetryClick = {},
            onLanguageQueryChanged = {},
            onSortOptionSelected = {},
            onRepositoryClick = {}
        )
    }
}
