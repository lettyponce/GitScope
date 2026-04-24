package com.lfp.gitscope.presentation.repositories

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lfp.gitscope.domain.model.RepositorySortOption
import com.lfp.gitscope.presentation.theme.GitScopeTheme

@Composable
fun SortOptionsMenu(
    selectedSortOption: RepositorySortOption,
    onSortOptionSelected: (RepositorySortOption) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Sort,
                contentDescription = "Sort repositories"
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
        }
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        RepositorySortOption.entries.forEach { option ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = if (option == selectedSortOption) {
                            "${option.label} ✓"
                        } else {
                            option.label
                        }
                    )
                },
                onClick = {
                    expanded = false
                    onSortOptionSelected(option)
                }
            )
        }
    }
}

@Preview(showBackground = true, name = "Sort options menu")
@Composable
private fun SortOptionsMenuPreview() {
    GitScopeTheme(dynamicColor = false) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            SortOptionsMenu(
                selectedSortOption = RepositorySortOption.STARS,
                onSortOptionSelected = {}
            )
        }
    }
}