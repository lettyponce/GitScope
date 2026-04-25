package com.lfp.gitscope.presentation.details

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.lfp.gitscope.core.common.GithubDateFormatter
import com.lfp.gitscope.data.mock.MockRepositories
import com.lfp.gitscope.presentation.theme.GitScopeTheme
import org.junit.Rule
import org.junit.Test

class RepositoryDetailsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun shouldDisplayRepositoryDetails() {
        val repository = MockRepositories.repositories.first()

        composeRule.setContent {
            GitScopeTheme {
                RepositoryDetailsScreen(repository = repository)
            }
        }

        composeRule.onNodeWithText("android-dev/awesome-android-ui").assertIsDisplayed()
        composeRule.onNodeWithText("android-dev").assertIsDisplayed()
        composeRule.onNodeWithText(
            "A collection of reusable Android UI components built with Jetpack Compose."
        ).assertIsDisplayed()
        composeRule.onNodeWithText("Kotlin").assertIsDisplayed()
        composeRule.onNodeWithText("⭐ 1840").assertIsDisplayed()
        composeRule.onNodeWithText("230").assertIsDisplayed()
        composeRule.onNodeWithText(GithubDateFormatter.format(repository.updatedAt)).assertIsDisplayed()
        composeRule.onNodeWithText("https://github.com/android-dev/awesome-android-ui").assertIsDisplayed()
    }
}
