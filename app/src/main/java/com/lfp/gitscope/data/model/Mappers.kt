package com.lfp.gitscope.data.model

import com.lfp.gitscope.domain.model.Repository

/**
 * Convert DTO to API model.
 */
fun GithubRepositoryResponse.toDomain(): Repository {
    return Repository(
        id = id,
        name = name,
        fullName = fullName,
        ownerName = owner.login,
        ownerAvatarUrl = owner.avatarUrl,
        description = description,
        stars = stars,
        forks = forks,
        updatedAt = updatedAt,
        htmlUrl = htmlUrl,
        language = language
    )
}