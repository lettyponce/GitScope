package com.lfp.gitscope.presentation.mocks

import com.lfp.gitscope.data.mock.MockRepositories
import com.lfp.gitscope.domain.model.Repository
import com.lfp.gitscope.domain.model.RepositorySortOption
import com.lfp.gitscope.domain.repository.GithubRepository

class MockGithubRepository : GithubRepository {

    override suspend fun searchRepositories(
        language: String,
        sortOption: RepositorySortOption
    ): List<Repository> {
        return MockRepositories.repositories
    }

    override suspend fun getRepositoryDetails(
        owner: String,
        repoName: String
    ): Repository {
        return MockRepositories.repositories.first()
    }
}