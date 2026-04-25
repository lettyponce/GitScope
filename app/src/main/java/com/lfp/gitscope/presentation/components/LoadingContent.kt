package com.lfp.gitscope.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lfp.gitscope.presentation.theme.GitScopeTheme

@Composable
fun LoadingContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        CircularProgressIndicator()

        Text(
            text = "Loading repositories...",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Preview(showBackground = true, name = "Loading content")
@Composable
private fun LoadingContentPreview() {
    GitScopeTheme(dynamicColor = false) {
        LoadingContent()
    }
}

@Preview(
    showBackground = true,
    name = "Loading content (dark)",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun LoadingContentPreviewDark() {
    GitScopeTheme(dynamicColor = false) {
        LoadingContent()
    }
}