package com.lfp.gitscope.presentation.repositories

import app.cash.turbine.test
import com.lfp.gitscope.data.mock.MockRepositories
import com.lfp.gitscope.domain.model.Repository
import com.lfp.gitscope.domain.model.RepositorySortOption
import com.lfp.gitscope.domain.repository.GithubRepository
import com.lfp.gitscope.presentation.MainDispatcherRule
import com.lfp.gitscope.presentation.mocks.MockGithubRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class RepositoriesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when ViewModel is created then should expose list with same size as mock catalog on init`() =
        runTest {
            val viewModel = RepositoriesViewModel(repository = MockGithubRepository())

            viewModel.uiState.test {
                val state = awaitItem()

                assertFalse(state.repositories.isEmpty())
                assertEquals(
                    MockRepositories.repositories.size,
                    state.repositories.size
                )
                assertEquals(
                    MockRepositories.repositories.first().id,
                    state.repositories.first().id
                )

                // Cancel or ignore any events which have already been received
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when ViewModel is created then should expose first repository with id name and owner matching mock catalog head on init`() =
        runTest {
            val viewModel = RepositoriesViewModel(repository = MockGithubRepository())

            viewModel.uiState.test {
                val state = awaitItem()

                val expectedRepository = MockRepositories.repositories.first()
                val actualRepository = state.repositories.first()

                assertEquals(expectedRepository.id, actualRepository.id)
                assertEquals(expectedRepository.name, actualRepository.name)
                assertEquals(expectedRepository.ownerName, actualRepository.ownerName)

                // Cancel or ignore any events which have already been received
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when repository returns 403 then ViewModel should expose error state`() = runTest {
        // Arrange
        val fakeRepository = object : GithubRepository {

            override suspend fun searchRepositories(
                language: String,
                sortOption: RepositorySortOption
            ): List<Repository> {
                throw HttpException(
                    Response.error<Any>(
                        403,
                        "{}".toResponseBody()
                    )
                )
            }

            override suspend fun getRepositoryDetails(
                owner: String,
                repoName: String
            ): Repository {
                error("Not used in this test")
            }
        }

        // Act
        val viewModel = RepositoriesViewModel(fakeRepository)

        advanceUntilIdle()

        val state = viewModel.uiState.value

        // Assert
        assertFalse(state.isLoading)
        assertEquals(emptyList<Repository>(), state.repositories)
        assertEquals(
            "GitHub request unavailable or access restricted. Please try again later.",
            state.errorMessage
        )
    }
}