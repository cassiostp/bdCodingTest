package com.example.myapplication.network

import com.example.myapplication.model.ProjectListModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories")
    fun searchProjects(@Query("q", encoded=true) query: String,
                       @Query("per_page", encoded=true) pageSize: Int): Deferred<Response<ProjectListModel>>
}