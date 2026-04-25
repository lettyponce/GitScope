package com.lfp.gitscope.data.repository

import android.util.Log
import com.lfp.gitscope.data.model.toDomain
import com.lfp.gitscope.data.remote.GithubApiService
import com.lfp.gitscope.domain.model.Repository
import com.lfp.gitscope.domain.model.RepositorySortOption
import com.lfp.gitscope.domain.repository.GithubRepository
import retrofit2.HttpException
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

        Log.d(
            "GithubRepository",
            "Searching query: $query sort: ${sortOption.apiValue}"
        )

        return try {
            apiService.searchRepositories(
                query = query,
                sort = sortOption.apiValue,
                order = "desc",
                perPage = 30,
                page = 1
            ).items.map { response ->
                response.toDomain()
            }
        } catch (e: HttpException) {
            if (e.code() == 422) {
                Log.d(
                    "GithubRepository",
                    "Search returned 422 (invalid or unsupported query); treating as no results"
                )
                emptyList()
            } else {
                throw e
            }
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