package com.lfp.gitscope.data.fake

import com.lfp.gitscope.data.model.GithubRepositoryResponse
import com.lfp.gitscope.data.model.SearchRepositoriesResponse
import com.lfp.gitscope.data.remote.GithubApiService
import java.io.IOException

class FakeGithubApiService : GithubApiService {

    var shouldFail = false

    override suspend fun searchRepositories(
        query: String,
        sort: String,
        order: String,
        perPage: Int,
        page: Int
    ): SearchRepositoriesResponse {
        if (shouldFail) {
            throw IOException("Simulated network failure")
        }

        return SearchRepositoriesResponse(
            totalCount = 0,
            incompleteResults = false,
            items = emptyList()
        )
    }

    override suspend fun getRepositoryDetails(
        owner: String,
        repo: String
    ): GithubRepositoryResponse {
        error("Not used in this fake")
    }
}