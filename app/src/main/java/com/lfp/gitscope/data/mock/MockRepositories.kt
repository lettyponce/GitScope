package com.lfp.gitscope.data.mock

import com.lfp.gitscope.domain.model.Repository

object MockRepositories {

    val repositories = listOf(
        Repository(
            id = 1,
            name = "awesome-android-ui",
            ownerName = "android-dev",
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
            description = "A collection of reusable Android UI components built with Jetpack Compose.",
            stars = 1840,
            forks = 230,
            updatedAt = "2026-04-20"
        ),
        Repository(
            id = 2,
            name = "kotlin-coroutines-samples",
            ownerName = "kotlin",
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/1446536?v=4",
            description = "Practical examples using Kotlin coroutines, Flow and structured concurrency.",
            stars = 3200,
            forks = 640,
            updatedAt = "2026-04-18"
        ),
        Repository(
            id = 3,
            name = "compose-navigation-playground",
            ownerName = "compose-labs",
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/13142323?v=4",
            description = "Navigation patterns for modern Android applications using Compose.",
            stars = 980,
            forks = 120,
            updatedAt = "2026-04-10"
        ),
        Repository(
            id = 4,
            name = "github-explorer",
            ownerName = "open-source",
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/9919?v=4",
            description = "A sample app to explore public GitHub repositories.",
            stars = 760,
            forks = 90,
            updatedAt = "2026-04-05"
        ),
        Repository(
            id = 5,
            name = "clean-architecture-android",
            ownerName = "mobile-architecture",
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/69631?v=4",
            description = "Clean Architecture sample for Android apps using Kotlin and Compose.",
            stars = 2500,
            forks = 420,
            updatedAt = "2026-03-30"
        ),
        Repository(
            id = 6,
            name = "material3-compose-kit",
            ownerName = "design-system",
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/6128107?v=4",
            description = "Material 3 components and design system examples for Android.",
            stars = 1430,
            forks = 180,
            updatedAt = "2026-03-25"
        )
    )
}