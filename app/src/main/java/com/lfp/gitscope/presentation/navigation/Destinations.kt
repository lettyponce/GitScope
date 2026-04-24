package com.lfp.gitscope.presentation.navigation

object Destinations {
    const val REPOSITORIES_ROUTE = "repositories"
    const val REPO_DETAILS_ROUTE = "repo_details"
    const val REPO_DETAILS_ROUTE_WITH_ARGS = "repo_details/{repoId}"

    fun repoDetailsRoute(repoId: Long): String {
        return "$REPO_DETAILS_ROUTE/$repoId"
    }
}