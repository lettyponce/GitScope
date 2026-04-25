package com.lfp.gitscope.presentation.repositories


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.lfp.gitscope.data.mock.MockRepositories
import com.lfp.gitscope.domain.model.Repository
import com.lfp.gitscope.domain.model.RepositorySortOption
import com.lfp.gitscope.presentation.theme.GitScopeTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class RepositoriesScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun shouldDisplayScreenTitleAndSearchField() {
        composeRule.setContent {
            GitScopeTheme {
                RepositoriesScreen(
                    repositories = MockRepositories.repositories,
                    languageQuery = "kotlin",
                    selectedSortOption = RepositorySortOption.STARS,
                    isLoading = false,
                    errorMessage = null,
                    onRetryClick = {},
                    onLanguageQueryChanged = {},
                    onSortOptionSelected = {},
                    onRepositoryClick = {}
                )
            }
        }

        composeRule.onNodeWithText("Github Repositories").assertIsDisplayed()
        composeRule.onNodeWithText("Programming language").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayRepositoryItems() {
        composeRule.setContent {
            GitScopeTheme {
                RepositoriesScreen(
                    repositories = MockRepositories.repositories,
                    languageQuery = "kotlin",
                    selectedSortOption = RepositorySortOption.STARS,
                    isLoading = false,
                    errorMessage = null,
                    onRetryClick = {},
                    onLanguageQueryChanged = {},
                    onSortOptionSelected = {},
                    onRepositoryClick = {}
                )
            }
        }

        composeRule.onNodeWithText("awesome-android-ui").assertIsDisplayed()
        composeRule.onNodeWithText("kotlin-coroutines-samples").assertIsDisplayed()
        composeRule.onNodeWithText("android-dev").assertIsDisplayed()
        composeRule.onNodeWithText("⭐ 3200").assertIsDisplayed()
    }

    @Test
    fun shouldCallRepositoryClickWhenItemIsClicked() {
        var clickedRepository: Repository? = null

        composeRule.setContent {
            GitScopeTheme {
                RepositoriesScreen(
                    repositories = MockRepositories.repositories,
                    languageQuery = "kotlin",
                    selectedSortOption = RepositorySortOption.STARS,
                    isLoading = false,
                    errorMessage = null,
                    onRetryClick = {},
                    onLanguageQueryChanged = {},
                    onSortOptionSelected = {},
                    onRepositoryClick = { repository ->
                        clickedRepository = repository
                    }
                )
            }
        }

        composeRule.onNodeWithText("awesome-android-ui").performClick()

        assertEquals("awesome-android-ui", clickedRepository?.name)
    }

    @Test
    fun shouldDisplayLoadingState() {
        composeRule.setContent {
            GitScopeTheme {
                RepositoriesScreen(
                    repositories = emptyList(),
                    languageQuery = "kotlin",
                    selectedSortOption = RepositorySortOption.STARS,
                    isLoading = true,
                    errorMessage = null,
                    onRetryClick = {},
                    onLanguageQueryChanged = {},
                    onSortOptionSelected = {},
                    onRepositoryClick = {}
                )
            }
        }

        composeRule.onNodeWithText("Loading repositories...").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayErrorStateAndRetryButton() {
        var retryClicked = false

        composeRule.setContent {
            GitScopeTheme {
                RepositoriesScreen(
                    repositories = emptyList(),
                    languageQuery = "kotlin",
                    selectedSortOption = RepositorySortOption.STARS,
                    isLoading = false,
                    errorMessage = "Network error",
                    onRetryClick = {
                        retryClicked = true
                    },
                    onLanguageQueryChanged = {},
                    onSortOptionSelected = {},
                    onRepositoryClick = {}
                )
            }
        }

        composeRule.onNodeWithText("Something went wrong").assertIsDisplayed()
        composeRule.onNodeWithText("Network error").assertIsDisplayed()

        composeRule.onNodeWithText("Try again").performClick()

        assertEquals(true, retryClicked)
    }

    @Test
    fun shouldDisplayEmptyState() {
        composeRule.setContent {
            GitScopeTheme {
                RepositoriesScreen(
                    repositories = emptyList(),
                    languageQuery = "elixir",
                    selectedSortOption = RepositorySortOption.STARS,
                    isLoading = false,
                    errorMessage = null,
                    onRetryClick = {},
                    onLanguageQueryChanged = {},
                    onSortOptionSelected = {},
                    onRepositoryClick = {}
                )
            }
        }

        composeRule.onNodeWithText("No results").assertIsDisplayed()
        composeRule.onNodeWithText("No repositories found for \"elixir\".").assertIsDisplayed()
    }
}