package com.lfp.gitscope.domain.model

enum class RepositorySortOption(
    val apiValue: String,
    val label: String
) {
    STARS("stars", "Stars"),
    FORKS("forks", "Forks"),
    UPDATED("updated", "Last updated")
}