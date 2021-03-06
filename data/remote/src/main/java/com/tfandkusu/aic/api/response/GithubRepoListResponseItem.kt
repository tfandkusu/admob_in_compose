package com.tfandkusu.aic.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class GithubRepoListResponseItem(
    val id: Long,
    val name: String,
    val description: String?,
    @field:Json(name = "updated_at")
    val updatedAt: Date,
    val language: String?,
    @field:Json(name = "html_url")
    val htmlUrl: String,
    val fork: Boolean
)
