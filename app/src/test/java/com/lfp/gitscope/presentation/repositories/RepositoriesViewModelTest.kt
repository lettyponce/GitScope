package com.lfp.gitscope.presentation.repositories

import app.cash.turbine.test
import com.lfp.gitscope.data.mock.MockRepositories
import com.lfp.gitscope.domain.model.Repository
import com.lfp.gitscope.domain.model.RepositorySortOption
import com.lfp.gitscope.domain.repository.GithubRepository
import com.lfp.gitscope.presentation.MainDispatcherRule
import com.lfp.gitscope.presentation.mocks.MockGithubRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response


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

    @Test
    fun `when retry succeeds after 403 then ViewModel should recover`() = runTest {
        // Arrange
        var requestCount = 0

        val fakeRepository = object : GithubRepository {

            override suspend fun searchRepositories(
                language: String,
                sortOption: RepositorySortOption
            ): List<Repository> {
                requestCount++

                if (requestCount == 1) {
                    throw HttpException(
                        Response.error<Any>(
                            403,
                            "{}".toResponseBody()
                        )
                    )
                }

                return MockRepositories.repositories
            }

            override suspend fun getRepositoryDetails(
                owner: String,
                repoName: String
            ): Repository {
                error("Not used in this test")
            }
        }

        val viewModel = RepositoriesViewModel(fakeRepository)

        // First request fails
        advanceUntilIdle()

        val errorState = viewModel.uiState.value

        assertFalse(errorState.isLoading)
        assertTrue(errorState.errorMessage != null)
        assertTrue(errorState.repositories.isEmpty())

        // Act: simulate retry
        viewModel.loadRepositories(
            language = errorState.languageQuery,
            sortOption = errorState.selectedSortOption
        )

        advanceUntilIdle()

        // Assert
        val recoveredState = viewModel.uiState.value

        assertEquals(2, requestCount)
        assertFalse(recoveredState.isLoading)
        assertEquals(null, recoveredState.errorMessage)
        assertEquals(
            MockRepositories.repositories.size,
            recoveredState.repositories.size
        )
    }

    @Test
    fun `when previous search is cancelled then it should not emit error`() = runTest {
        // Arrange: the first search remains running.
        val pendingFirstSearch = CompletableDeferred<List<Repository>>()
        val observedStates = mutableListOf<RepositoriesUiState>()

        var requestCount = 0

        val fakeRepository = object : GithubRepository {

            override suspend fun searchRepositories(
                language: String,
                sortOption: RepositorySortOption
            ): List<Repository> {
                requestCount++

                return if (requestCount == 1) {
                    pendingFirstSearch.await()
                } else {
                    MockRepositories.repositories
                }
            }

            override suspend fun getRepositoryDetails(
                owner: String,
                repoName: String
            ): Repository {
                error("Not used in this test")
            }
        }

        val viewModel = RepositoriesViewModel(fakeRepository)

        // Observes all transitions, including intermediate ones.
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect { state ->
                observedStates.add(state)
            }
        }

        // Action: starting another search cancels the first one.
        viewModel.loadRepositories(
            language = "swift",
            sortOption = RepositorySortOption.STARS
        )

        advanceUntilIdle()

        // Assert: the new search has been completed.
        assertEquals(2, requestCount)
        assertEquals(
            MockRepositories.repositories,
            viewModel.uiState.value.repositories
        )
        assertFalse(viewModel.uiState.value.isLoading)

        // Cancellation must not result in an error state
        assertTrue(
            "Cancelled requests must not emit UI errors",
            observedStates.none { it.errorMessage != null }
        )
    }
}