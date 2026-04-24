package com.lfp.gitscope.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lfp.gitscope.presentation.repositories.RepositoryScreen
import com.lfp.gitscope.presentation.repositories.mockRepositories

@Composable
fun GitScopeNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.REPOSITORIES_ROUTE
    ) {
        composable(Destinations.REPOSITORIES_ROUTE) {
            RepositoryScreen(
                repositories = mockRepositories,
                onRepositoryClick = { /* TODO: navigation to detail */ }
            )
        }
    }
}