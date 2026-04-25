package com.lfp.gitscope.presentation.navigation

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object Destinations {
    const val REPOSITORIES_ROUTE = "repositories"
    const val REPO_DETAILS_ROUTE = "repo_details"
    const val OWNER_ARG = "owner"
    const val REPO_ARG = "repo"
    const val REPO_DETAILS_ROUTE_WITH_ARGS = "repo_details/{owner}/{repo}"

    fun repoDetailsRoute(
        owner: String,
        repo: String
    ): String {
        val encodedOwner = URLEncoder.encode(owner, StandardCharsets.UTF_8.toString())
        val encodedRepo = URLEncoder.encode(repo, StandardCharsets.UTF_8.toString())

        return "$REPO_DETAILS_ROUTE/$encodedOwner/$encodedRepo"
    }
}