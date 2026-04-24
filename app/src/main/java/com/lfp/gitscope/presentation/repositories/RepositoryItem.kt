package com.lfp.gitscope.presentation.repositories

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lfp.gitscope.data.mock.MockRepositories
import com.lfp.gitscope.domain.model.Repository
import com.lfp.gitscope.presentation.theme.GitScopeTheme

@Composable
fun RepositoryItem(
    repo: Repository,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = repo.ownerAvatarUrl,
                    contentDescription = "Avatar de ${repo.ownerName}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = repo.name,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = repo.ownerName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Text(
                text = repo.description ?: "No description available.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "⭐ ${repo.stars}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true, name = "Repository item")
@Composable
private fun RepositoryItemPreview() {
    GitScopeTheme(dynamicColor = false) {
        RepositoryItem(
            repo = MockRepositories.repositories.first(),
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    showBackground = true,
    name = "Repository item (dark)",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun RepositoryItemPreviewDark() {
    GitScopeTheme(dynamicColor = false) {
        RepositoryItem(
            repo = MockRepositories.repositories[1],
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, name = "Repository item (long text)")
@Composable
private fun RepositoryItemPreviewLongText() {
    GitScopeTheme(dynamicColor = false) {
        RepositoryItem(
            repo = Repository(
                id = 99,
                name = "very-long-repository-name-for-preview-overflow",
                fullName = "very-long-owner-name-for-preview/very-long-repository-name-for-preview-overflow",
                ownerName = "very-long-owner-name-for-preview",
                ownerAvatarUrl = "https://avatars.githubusercontent.com/u/9919?v=4",
                description = "A very long description that should ellipsize after three lines in the repository card preview to verify overflow behavior in the layout.",
                stars = 12_345,
                forks = 999,
                updatedAt = "2026-04-01",
                htmlUrl = "https://github.com/very-long-owner-name-for-preview/very-long-repository-name-for-preview-overflow",
                language = "Kotlin"
            ),
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}