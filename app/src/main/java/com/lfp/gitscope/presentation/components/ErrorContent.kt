package com.lfp.gitscope.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lfp.gitscope.presentation.theme.GitScopeTheme

@Composable
fun ErrorContent(
    message: String,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Something went wrong",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Button(
            onClick = onRetryClick,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Try again")
        }
    }
}

@Preview(showBackground = true, name = "Error content")
@Composable
private fun ErrorContentPreview() {
    GitScopeTheme(dynamicColor = false) {
        ErrorContent(
            message = "Could not reach GitHub. Check your connection and try again.",
            onRetryClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "Error content (dark)",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ErrorContentPreviewDark() {
    GitScopeTheme(dynamicColor = false) {
        ErrorContent(
            message = "HTTP 503: Service temporarily unavailable.",
            onRetryClick = {}
        )
    }
}