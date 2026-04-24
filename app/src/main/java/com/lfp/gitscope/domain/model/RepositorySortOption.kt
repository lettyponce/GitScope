package com.lfp.gitscope.domain.model

enum class RepositorySortOption(val apiValue: String) {
    STARS("stars"),
    FORKS("forks"),
    UPDATED("updated")
}