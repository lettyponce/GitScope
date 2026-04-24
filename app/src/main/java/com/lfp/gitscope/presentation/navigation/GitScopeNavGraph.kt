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
                        Destinations.repoDetailsRoute(
                            owner = repository.ownerName,
                            repo = repository.name
                        )
                    )
                }
            )
        }

        composable(
            route = Destinations.REPO_DETAILS_ROUTE_WITH_ARGS,
            arguments = listOf(
                navArgument(Destinations.OWNER_ARG) {
                    type = NavType.StringType
                },
                navArgument(Destinations.REPO_ARG) {
                    type = NavType.StringType
                }
            )
        ) {
            RepositoryDetailsRoute()
        }
    }
}