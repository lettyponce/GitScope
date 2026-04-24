package com.lfp.gitscope.data.model

import com.google.gson.annotations.SerializedName

data class GithubOwnerResponse(
    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatarUrl: String
)