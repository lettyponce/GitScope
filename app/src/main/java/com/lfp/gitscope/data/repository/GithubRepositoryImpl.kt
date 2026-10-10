package com.lfp.gitscope.data.repository

import android.util.Log
import com.lfp.gitscope.data.model.toDomain
import com.lfp.gitscope.data.remote.GithubApiService
import com.lfp.gitscope.domain.model.Repository
import com.lfp.gitscope.domain.model.RepositorySortOption
import com.lfp.gitscope.domain.repository.GithubRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val apiService: GithubApiService
) : GithubRepository {

    private var cachedRepositories: List<Repository>? = null

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
            val repositories = apiService.searchRepositories(
                query = query,
                sort = sortOption.apiValue,
                order = "desc",
                perPage = 30,
                page = 1
            ).items.map { response ->
                response.toDomain()
            }
            cachedRepositories = repositories
            repositories
        } catch (e: HttpException) {
            when (e.code()) {
                422 -> {
                    Log.d(
                        "GithubRepository",
                        "Search returned 422 (invalid or unsupported query); treating as no results"
                    )
                    emptyList()
                }

                403 -> {
                    Log.w(
                        "GithubRepository",
                        "Search returned 403 (forbidden or rate limited); treating as no results",
                        e
                    )
                    throw e
                }

                else -> cachedRepositories?.also {
                    Log.d(
                        "GithubRepository",
                        "Search HTTP ${e.code()}; returning ${it.size} cached repositories"
                    )
                } ?: throw e
            }
        } catch (e: IOException) {
            cachedRepositories?.also {
                Log.d(
                    "GithubRepository",
                    "Network error; returning ${it.size} cached repositories",
                    e
                )
            } ?: throw e
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