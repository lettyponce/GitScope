package com.lfp.gitscope.presentation.repositories

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lfp.gitscope.presentation.theme.GitScopeTheme

@Composable
fun LanguageSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        label = {
            Text(text = "Programming language")
        },
        placeholder = {
            Text(text = "kotlin, swift, rust...")
        }
    )
}

@Preview(showBackground = true, name = "Language search (empty)")
@Composable
private fun LanguageSearchFieldPreview() {
    GitScopeTheme(dynamicColor = false) {
        LanguageSearchField(
            value = "",
            onValueChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, name = "Language search (filled)")
@Composable
private fun LanguageSearchFieldPreviewFilled() {
    GitScopeTheme(dynamicColor = false) {
        LanguageSearchField(
            value = "Kotlin, Rust",
            onValueChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    showBackground = true,
    name = "Language search (dark)",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun LanguageSearchFieldPreviewDark() {
    GitScopeTheme(dynamicColor = false) {
        LanguageSearchField(
            value = "swift",
            onValueChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}