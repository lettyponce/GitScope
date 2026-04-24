package com.lfp.gitscope.presentation.repository

import app.cash.turbine.test
import com.lfp.gitscope.data.mock.MockRepositories
import com.lfp.gitscope.presentation.MainDispatcherRule
import com.lfp.gitscope.presentation.mocks.MockGithubRepository
import com.lfp.gitscope.presentation.repositories.RepositoriesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class RepositoriesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when ViewModel is created then should expose list with same size as mock catalog on init`() = runTest {
        val viewModel = RepositoriesViewModel( repository = MockGithubRepository() )

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
    fun `when ViewModel is created then should expose first repository with id name and owner matching mock catalog head on init`() = runTest {
        val viewModel = RepositoriesViewModel( repository = MockGithubRepository())

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
}