package com.lfp.gitscope.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lfp.gitscope.presentation.details.RepositoryDetailsRoute
import com.lfp.gitscope.presentation.repositories.RepositoriesRoute

@Composable
fun GitScopeNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.REPOSITORIES_ROUTE
    ) {
        composable(Destinations.REPOSITORIES_ROUTE) {
            RepositoriesRoute(
                onClick = { repository ->
                    navController.navigate(
                        Destinations.repoDetailsRoute(repository.id)
                    )
                }
            )
        }

        composable(
            route = Destinations.REPO_DETAILS_ROUTE_WITH_ARGS,
            arguments = listOf(
                navArgument("repoId") {
                    type = NavType.LongType
                }
            )
        ) {
            RepositoryDetailsRoute()
        }
    }
}