package com.lfp.gitscope.presentation.details

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.lfp.gitscope.presentation.components.EmptyContent
import com.lfp.gitscope.presentation.components.ErrorContent
import com.lfp.gitscope.presentation.components.LoadingContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryDetailsRoute(
    onBackClick: () -> Unit,
    viewModel: RepositoryDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val repository = uiState.repository
    val errorMessage = uiState.errorMessage
    val title = repository?.name ?: uiState.pendingTitle

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)

        when {
            uiState.isLoading -> {
                LoadingContent(
                    modifier = contentModifier,
                    message = "Loading repository…"
                )
            }

            errorMessage != null -> {
                ErrorContent(
                    message = errorMessage,
                    onRetryClick = viewModel::retryLoadRepositoryDetails,
                    modifier = contentModifier
                )
            }

            repository != null -> {
                RepositoryDetailsScreen(
                    repository = repository,
                    modifier = contentModifier
                )
            }

            else -> {
                EmptyContent(
                    message = "Repository details not available.",
                    modifier = contentModifier
                )
            }
        }
    }
}
