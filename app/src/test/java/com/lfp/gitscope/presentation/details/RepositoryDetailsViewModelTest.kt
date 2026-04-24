package com.lfp.gitscope.presentation.details

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.lfp.gitscope.data.mock.MockRepositories
import com.lfp.gitscope.presentation.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when valid repoId is provided then should expose selected repository`() = runTest {
        val expectedRepository = MockRepositories.repositories.first()

        val savedStateHandle = SavedStateHandle(
            mapOf("repoId" to expectedRepository.id)
        )

        val viewModel = RepositoryDetailsViewModel(savedStateHandle)

        viewModel.uiState.test {
            val state = awaitItem()

            assertEquals(expectedRepository.id, state.repository?.id)
            assertEquals(expectedRepository.name, state.repository?.name)
            assertEquals(expectedRepository.ownerName, state.repository?.ownerName)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when invalid repoId is provided then should expose null repository`() = runTest {
        val savedStateHandle = SavedStateHandle(
            mapOf("repoId" to -1L)
        )

        val viewModel = RepositoryDetailsViewModel(savedStateHandle)

        viewModel.uiState.test {
            val state = awaitItem()

            assertNull(state.repository)

            cancelAndIgnoreRemainingEvents()
        }
    }
}