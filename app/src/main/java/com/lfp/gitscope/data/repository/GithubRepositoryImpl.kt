package com.lfp.gitscope.data.repository

import com.lfp.gitscope.data.model.toDomain
import com.lfp.gitscope.data.remote.GithubApiService
import com.lfp.gitscope.domain.model.Repository
import com.lfp.gitscope.domain.model.RepositorySortOption
import com.lfp.gitscope.domain.repository.GithubRepository
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val apiService: GithubApiService
) : GithubRepository {

    override suspend fun searchRepositories(
        language: String,
        sortOption: RepositorySortOption
    ): List<Repository> {
        val normalizedLanguage = language.trim().ifBlank { "kotlin" }

        val query = "language:$normalizedLanguage"

        return apiService.searchRepositories(
            query = query,
            sort = sortOption.apiValue,
            order = "desc",
            perPage = 30,
            page = 1
        ).items.map { response ->
            response.toDomain()
        }
    }

    override suspend fun getRepositoryDetails(
        owner: String,
        repoName: String
    ): Repository {
        return apiService
            .getRepositoryDetails(
                owner = owner,
                repo = repoName
            )
            .toDomain()
    }
}