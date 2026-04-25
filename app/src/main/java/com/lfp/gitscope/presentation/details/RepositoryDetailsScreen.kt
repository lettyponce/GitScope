package com.lfp.gitscope.presentation.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lfp.gitscope.core.common.GithubDateFormatter
import com.lfp.gitscope.data.mock.MockRepositories
import com.lfp.gitscope.domain.model.Repository
import com.lfp.gitscope.presentation.theme.GitScopeTheme

@Composable
fun RepositoryDetailsScreen(
    repository: Repository,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = repository.ownerAvatarUrl,
                contentDescription = "Avatar de ${repository.ownerName}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = repository.fullName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = repository.ownerName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

        Text(
            text = repository.description ?: "No description available.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        InfoRow(label = "Language", value = repository.language ?: "N/A")
        InfoRow(
            label = "Stars",
            value = "⭐ ${repository.stars}",
            valueStyle = MaterialTheme.typography.labelMedium,
            valueColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
        InfoRow(label = "Forks", value = repository.forks.toString())
        InfoRow(
            label = "Updated",
            value = GithubDateFormatter.format(repository.updatedAt)
        )
        InfoRow(label = "Open in browser", value = repository.htmlUrl)
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    valueStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    valueColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(128.dp)
        )
        Text(
            text = value,
            style = valueStyle,
            color = valueColor,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true, name = "Repository details")
@Composable
private fun RepositoryDetailsScreenPreview() {
    GitScopeTheme(dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            RepositoryDetailsScreen(repository = MockRepositories.repositories.first())
        }
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
        Surface(color = MaterialTheme.colorScheme.background) {
            RepositoryDetailsScreen(repository = MockRepositories.repositories[1])
        }
    }
}

@Preview(showBackground = true, name = "Repository details (no description)")
@Composable
private fun RepositoryDetailsScreenPreviewNoDescription() {
    GitScopeTheme(dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            RepositoryDetailsScreen(
                repository = MockRepositories.repositories[2].copy(description = null)
            )
        }
    }
}

@Preview(showBackground = true, name = "Info row")
@Composable
private fun InfoRowPreview() {
    GitScopeTheme(dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(Modifier.padding(16.dp)) {
                InfoRow(label = "Language", value = "Kotlin")
                Spacer(modifier = Modifier.height(8.dp))
                InfoRow(
                    label = "Open in browser",
                    value = "https://github.com/android/nowinandroid"
                )
            }
        }
    }
}
