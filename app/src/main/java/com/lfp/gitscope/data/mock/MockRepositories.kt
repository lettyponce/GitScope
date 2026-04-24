package com.lfp.gitscope.data.mock

import com.lfp.gitscope.domain.model.Repository

object MockRepositories {

    val repositories = listOf(
        Repository(
            id = 1,
            name = "awesome-android-ui",
            fullName = "android-dev/awesome-android-ui",
            ownerName = "android-dev",
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
            description = "A collection of reusable Android UI components built with Jetpack Compose.",
            stars = 1840,
            forks = 230,
            updatedAt = "2026-04-20",
            htmlUrl = "https://github.com/android-dev/awesome-android-ui",
            language = "Kotlin"
        ),
        Repository(
            id = 2,
            name = "kotlin-coroutines-samples",
            fullName = "kotlin/kotlin-coroutines-samples",
            ownerName = "kotlin",
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/1446536?v=4",
            description = "Practical examples using Kotlin coroutines, Flow and structured concurrency.",
            stars = 3200,
            forks = 640,
            updatedAt = "2026-04-18",
            htmlUrl = "https://github.com/kotlin/kotlin-coroutines-samples",
            language = "Kotlin"
        ),
        Repository(
            id = 3,
            name = "compose-navigation-playground",
            fullName = "compose-labs/compose-navigation-playground",
            ownerName = "compose-labs",
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/13142323?v=4",
            description = "Navigation patterns for modern Android applications using Compose.",
            stars = 980,
            forks = 120,
            updatedAt = "2026-04-10",
            htmlUrl = "https://github.com/compose-labs/compose-navigation-playground",
            language = "Kotlin"
        ),
        Repository(
            id = 4,
            name = "github-explorer",
            fullName = "open-source/github-explorer",
            ownerName = "open-source",
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/9919?v=4",
            description = "A sample app to explore public GitHub repositories.",
            stars = 760,
            forks = 90,
            updatedAt = "2026-04-05",
            htmlUrl = "https://github.com/open-source/github-explorer",
            language = null
        ),
        Repository(
            id = 5,
            name = "clean-architecture-android",
            fullName = "mobile-architecture/clean-architecture-android",
            ownerName = "mobile-architecture",
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/69631?v=4",
            description = "Clean Architecture sample for Android apps using Kotlin and Compose.",
            stars = 2500,
            forks = 420,
            updatedAt = "2026-03-30",
            htmlUrl = "https://github.com/mobile-architecture/clean-architecture-android",
            language = "Kotlin"
        ),
        Repository(
            id = 6,
            name = "material3-compose-kit",
            fullName = "design-system/material3-compose-kit",
            ownerName = "design-system",
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/6128107?v=4",
            description = "Material 3 components and design system examples for Android.",
            stars = 1430,
            forks = 180,
            updatedAt = "2026-03-25",
            htmlUrl = "https://github.com/design-system/material3-compose-kit",
            language = "Kotlin"
        )
    )
}
