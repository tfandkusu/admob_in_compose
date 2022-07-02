package com.tfandkusu.aic.api

import com.tfandkusu.aic.api.response.GithubRepoListResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {
    @GET("/users/tfandkusu/repos")
    suspend fun listRepos(@Query("page") page: Int): List<GithubRepoListResponseItem>
}
