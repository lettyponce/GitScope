package com.lfp.gitscope.domain.repository

import com.lfp.gitscope.domain.model.Repository
import com.lfp.gitscope.domain.model.RepositorySortOption

interface GithubRepository {

    suspend fun searchRepositories(
        language: String,
        sortOption: RepositorySortOption = RepositorySortOption.STARS
    ): List<Repository>

    suspend fun getRepositoryDetails(
        owner: String,
        repoName: String
    ): Repository
}