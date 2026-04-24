package com.lfp.gitscope.domain.model

/**
 * Data class representing a repository.
 *
 * Example:
 * Repository(
 *     id = 1,
 *     name = "awesome-android-ui",
 *     fullName = "android-dev/awesome-android-ui",
 *     ownerName = "android-dev",
 *     ownerAvatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
 *     description = "A collection of reusable Android UI components built with Jetpack Compose.",
 *     stars = 1840,
 *     forks = 230,
 *     updatedAt = "2026-04-20",
 *     htmlUrl = "https://github.com/android-dev/awesome-android-ui",
 *     language = "Kotlin"
 * )
 */

data class Repository(
    val id: Long,
    val name: String,
    val fullName: String,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val description: String?,
    val stars: Int,
    val forks: Int,
    val updatedAt: String,
    val htmlUrl: String,
    val language: String?
)