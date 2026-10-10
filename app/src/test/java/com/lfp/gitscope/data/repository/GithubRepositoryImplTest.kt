package com.lfp.gitscope.data.repository

import com.lfp.gitscope.data.model.GithubRepositoryResponse
import com.lfp.gitscope.data.model.SearchRepositoriesResponse
import com.lfp.gitscope.data.remote.GithubApiService
import com.lfp.gitscope.domain.model.RepositorySortOption
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class GithubRepositoryImplTest {

    @Test
    fun `when API returns 403 then repository should propagate HttpException`() = runTest {
        // Arrange
        val fakeApi = object : GithubApiService {

            override suspend fun searchRepositories(
                query: String,
                sort: String,
                order: String,
                perPage: Int,
                page: Int
            ): SearchRepositoriesResponse {
                throw HttpException(
                    Response.error<SearchRepositoriesResponse>(
                        403,
                        "{}".toResponseBody()
                    )
                )
            }

            override suspend fun getRepositoryDetails(
                owner: String,
                repo: String
            ): GithubRepositoryResponse {
                error("Not used in this test")
            }
        }

        val repository = GithubRepositoryImpl(fakeApi)

        // Act & Assert
        try {
            repository.searchRepositories(
                language = "kotlin",
                sortOption = RepositorySortOption.STARS
            )

            fail("Expected HttpException, but search completed successfully")
        } catch (exception: HttpException) {
            assertEquals(403, exception.code())
        }
    }

    @Test
    fun `when API returns empty results then repository should return empty list`() = runTest {
        // Arrange
        val fakeApi = object : GithubApiService {

            override suspend fun searchRepositories(
                query: String,
                sort: String,
                order: String,
                perPage: Int,
                page: Int
            ): SearchRepositoriesResponse {
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
                error("Not used in this test")
            }
        }

        val repository = GithubRepositoryImpl(fakeApi)

        // Act
        val result = repository.searchRepositories(
            language = "kotlin",
            sortOption = RepositorySortOption.STARS
        )

        // Assert
        assertTrue(result.isEmpty())
    }
}