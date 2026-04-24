package com.lfp.gitscope.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun GitScopeNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.REPOSITORIES_ROUTE
    ) {
        composable(Destinations.REPOSITORIES_ROUTE) {
            FoundationScreen()
        }
    }
}

@Composable
private fun FoundationScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Git Scope Explorer",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Foundation ready",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}