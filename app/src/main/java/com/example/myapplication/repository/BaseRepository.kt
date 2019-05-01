package com.example.myapplication.repository

import retrofit2.Response
import java.io.IOException

open class BaseRepository{

    suspend fun <T: Any> safeApiResult(call: suspend () -> Response<T>, errorMessage: String) : Result<T>{
        val response = call()
        if(response.isSuccessful) return Result.Success(response.body()!!)

        return Result.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
    }
}