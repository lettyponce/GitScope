package com.lfp.gitscope.domain.model

data class Repository(
    val id: Long,
    val name: String,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val description: String?,
    val stars: Int,
    val forks: Int,
    val updatedAt: String
)