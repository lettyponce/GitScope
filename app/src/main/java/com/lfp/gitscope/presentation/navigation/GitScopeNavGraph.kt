package com.lfp.gitscope.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lfp.gitscope.presentation.details.RepoDetailsScreen
import com.lfp.gitscope.presentation.repositories.RepositoriesScreen
import com.lfp.gitscope.presentation.repositories.mockRepositories

@Composable
fun GitScopeNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.REPOSITORIES_ROUTE
    ) {

        // LIST
        composable(Destinations.REPOSITORIES_ROUTE) {

            RepositoriesScreen(
                repositories = mockRepositories,
                onRepositoryClick = { repository ->
                    navController.navigate(
                        Destinations.repoDetailsRoute(repository.id)
                    )
                }
            )
        }

        // DETAILS
        composable(
            route = Destinations.REPO_DETAILS_ROUTE_WITH_ARGS,
            arguments = listOf(
                navArgument("repoId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->

            val repoId = backStackEntry.arguments?.getLong("repoId")
            val repo = mockRepositories.find { it.id == repoId }

            repo?.let {
                RepoDetailsScreen(repository = it)
            }
        }
    }
}