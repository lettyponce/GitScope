package com.lfp.gitscope.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lfp.gitscope.presentation.theme.GitScopeTheme

@Composable
fun EmptyContent(
    message: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No results",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true, name = "Empty content")
@Composable
private fun EmptyContentPreview() {
    GitScopeTheme(dynamicColor = false) {
        EmptyContent(
            message = "No repositories found for \"zig\"."
        )
    }
}

@Preview(
    showBackground = true,
    name = "Empty content (dark)",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun EmptyContentPreviewDark() {
    GitScopeTheme(dynamicColor = false) {
        EmptyContent(
            message = "No repositories found for \"python, ruby\"."
        )
    }
}
