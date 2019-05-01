package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class ProjectModel(
    val name: String?,

    @SerializedName("html_url")
    val htmlUrl: String?
)