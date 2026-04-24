package com.lfp.gitscope.presentation.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lfp.gitscope.presentation.repositories.RepoInformation
import com.lfp.gitscope.presentation.repositories.mockRepositories
import com.lfp.gitscope.presentation.theme.GitScopeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoDetailsScreen(
    repository: RepoInformation
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = repository.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                AsyncImage(
                    model = repository.ownerAvatarUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = repository.ownerName,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Text(
                text = repository.description ?: "No description available",
                style = MaterialTheme.typography.bodyMedium
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("⭐ ${repository.stars}")
            }
        }
    }
}

@Preview(showBackground = true, name = "Repository details")
@Composable
private fun RepositoryDetailsScreenPreview() {
    GitScopeTheme(dynamicColor = false) {
        RepoDetailsScreen(repository = mockRepositories.first())
    }
}

@Preview(
    showBackground = true,
    name = "Repository details (dark)",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun RepositoryDetailsScreenPreviewDark() {
    GitScopeTheme(dynamicColor = false) {
        RepoDetailsScreen(repository = mockRepositories[1])
    }
}

@Preview(showBackground = true, name = "Repository details (no description)")
@Composable
private fun RepositoryDetailsScreenPreviewNoDescription() {
    GitScopeTheme(dynamicColor = false) {
        RepoDetailsScreen(
            repository = mockRepositories[2].copy(description = null)
        )
    }
}