package com.lfp.gitscope.data.model

import com.google.gson.annotations.SerializedName

data class GithubRepositoryResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("owner")
    val owner: GithubOwnerResponse,

    @SerializedName("description")
    val description: String?,

    @SerializedName("stargazers_count")
    val stars: Int,

    @SerializedName("forks_count")
    val forks: Int,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("html_url")
    val htmlUrl: String,

    @SerializedName("language")
    val language: String?
)
