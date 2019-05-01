package com.example.myapplication.repository

import com.example.myapplication.model.ProjectListModel
import com.example.myapplication.network.GithubApi

class GithubRepository(private val api: GithubApi): BaseRepository() {

    suspend fun searchProjects(topic: String, language: String): Result<ProjectListModel> {
        val query = "language:$language+topic:$topic"
        return safeApiResult(
            call = { api.searchProjects(query, 10).await() }, errorMessage = "Error fetching project list"
        )
    }
}