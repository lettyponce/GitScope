package com.lfp.gitscope.data.repository

import com.lfp.gitscope.data.fake.FakeGithubApiService
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
import java.io.IOException

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

    @Test
    fun `when network fails for same query then return cached results`() = runTest {
        val api = FakeGithubApiService()
        val repository = GithubRepositoryImpl(api)

        repository.searchRepositories("kotlin", RepositorySortOption.STARS)

        api.shouldFail = true

        val result = repository.searchRepositories(
            "kotlin",
            RepositorySortOption.STARS
        )

        assertTrue(result.isEmpty())
    }

    @Test
    fun `when language changes then cached results must not be reused`() = runTest {
        val api = FakeGithubApiService()
        val repository = GithubRepositoryImpl(api)

        repository.searchRepositories("kotlin", RepositorySortOption.STARS)

        api.shouldFail = true

        try {
            repository.searchRepositories("swift", RepositorySortOption.STARS)
            fail("Expected IOException for uncached query")
        } catch (exception: IOException) {
            assertEquals("Simulated network failure", exception.message)
        }
    }

    @Test
    fun `when sort changes then cached results must not be reused`() = runTest {
        val api = FakeGithubApiService()
        val repository = GithubRepositoryImpl(api)

        repository.searchRepositories("kotlin", RepositorySortOption.STARS)

        api.shouldFail = true

        try {
            repository.searchRepositories("kotlin", RepositorySortOption.FORKS)
            fail("Expected IOException for uncached sort option")
        } catch (exception: IOException) {
            assertEquals("Simulated network failure", exception.message)
        }
    }
}