package com.lfp.gitscope.presentation.details

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.lfp.gitscope.data.mock.MockRepositories
import com.lfp.gitscope.domain.model.Repository
import com.lfp.gitscope.domain.model.RepositorySortOption
import com.lfp.gitscope.domain.repository.GithubRepository
import com.lfp.gitscope.presentation.MainDispatcherRule
import com.lfp.gitscope.presentation.mocks.MockGithubRepository
import com.lfp.gitscope.presentation.navigation.Destinations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import kotlin.coroutines.cancellation.CancellationException

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when valid owner and repo are provided then should expose selected repository`() = runTest {
        val expectedRepository = MockRepositories.repositories.first()

        val savedStateHandle = SavedStateHandle(
            mapOf(
                Destinations.OWNER_ARG to expectedRepository.ownerName,
                Destinations.REPO_ARG to expectedRepository.name
            )
        )

        val viewModel = RepositoryDetailsViewModel(MockGithubRepository(), savedStateHandle)

        viewModel.uiState.test {
            val state = awaitItem()

            assertEquals(expectedRepository.id, state.repository?.id)
            assertEquals(expectedRepository.name, state.repository?.name)
            assertEquals(expectedRepository.ownerName, state.repository?.ownerName)
            assertNull(state.errorMessage)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when owner or repo is missing then should expose error and null repository`() = runTest {
        val savedStateHandle = SavedStateHandle(emptyMap())

        val viewModel = RepositoryDetailsViewModel(MockGithubRepository(), savedStateHandle)

        viewModel.uiState.test {
            val state = awaitItem()

            assertNull(state.repository)
            assertEquals("Invalid repository information", state.errorMessage)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when details request is cancelled then no error should be displayed`() = runTest {
        val expectedRepository = MockRepositories.repositories.first()

        val fakeRepository = object : GithubRepository {

            override suspend fun searchRepositories(
                language: String,
                sortOption: RepositorySortOption
            ): List<Repository> {
                error("Not used in this test")
            }

            override suspend fun getRepositoryDetails(
                owner: String,
                repoName: String
            ): Repository {
                throw CancellationException("Request cancelled")
            }
        }

        val savedStateHandle = SavedStateHandle(
            mapOf(
                Destinations.OWNER_ARG to expectedRepository.ownerName,
                Destinations.REPO_ARG to expectedRepository.name
            )
        )

        val viewModel = RepositoryDetailsViewModel(
            fakeRepository,
            savedStateHandle
        )

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertNull(state.errorMessage)
        assertNull(state.repository)
    }
}
