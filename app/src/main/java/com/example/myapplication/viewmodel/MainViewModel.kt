package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.ProjectListModel
import com.example.myapplication.network.ApiFactory
import com.example.myapplication.repository.GithubRepository
import com.example.myapplication.repository.Result
import kotlinx.coroutines.*
import java.lang.IllegalStateException
import kotlin.coroutines.CoroutineContext

class MainViewModel: ViewModel() {
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository = GithubRepository(ApiFactory.githubApi)

    private val _projectsList by lazy { MutableLiveData<ProjectListModel>() }
    val projectsList: LiveData<ProjectListModel> = _projectsList


    fun searchAndroidProjects() {
        scope.launch {
            val githubResult = repository.searchProjects("android", "kotlin")

            withContext(Dispatchers.Main) {
                when(githubResult) {
                    is Result.Success -> _projectsList.value = githubResult.data
                    is Result.Error -> throw IllegalStateException(githubResult.exception)
                }
            }
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}