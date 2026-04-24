package com.lfp.gitscope.presentation.repositories

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
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
import com.lfp.gitscope.presentation.theme.GitScopeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryScreen(
    repositories: List<RepoInformation>,
    onRepositoryClick: (RepoInformation) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Github Repositories")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = repositories,
                key = { repo -> repo.id }
            ) { repo ->
                RepositoryItem(
                    repo = repo,
                    onClick = {
                        onRepositoryClick(repo)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Repository screen")
@Composable
private fun RepositoryScreenPreview() {
    GitScopeTheme(dynamicColor = false) {
        RepositoryScreen(
            repositories = mockRepositories,
            onRepositoryClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "Repository screen (dark)",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun RepositoryScreenPreviewDark() {
    GitScopeTheme(dynamicColor = false) {
        RepositoryScreen(
            repositories = mockRepositories.take(3),
            onRepositoryClick = {}
        )
    }
}
