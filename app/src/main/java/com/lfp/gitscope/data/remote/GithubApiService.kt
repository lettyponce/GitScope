package com.lfp.gitscope.data.remote

import com.lfp.gitscope.data.model.GithubRepositoryResponse
import com.lfp.gitscope.data.model.SearchRepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    /**
     * Search for public repositories.
     */
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("per_page") perPage: Int = 30,
        @Query("page") page: Int = 1
    ): SearchRepositoriesResponse

    /**
     * Search for the details of a specific repository.
     */
    @GET("repos/{owner}/{repo}")
    suspend fun getRepositoryDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): GithubRepositoryResponse

}