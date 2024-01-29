package com.hossam.e_ramotask.feature_posts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hossam.e_ramotask.core.util.Resource
import com.hossam.e_ramotask.feature_posts.domain.repository.IPostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val repository: IPostsRepository
) : ViewModel() {

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            throwable.printStackTrace()
        }

    private var getPostsJob: Job? = null

    private val _state = MutableSharedFlow<PostsState>()
    val state = _state.asSharedFlow()

    fun getPosts() {
        getPostsJob?.cancel()
        getPostsJob = viewModelScope.launch(coroutineExceptionHandler + Dispatchers.Main) {
            repository.getPosts().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> produce(PostsState.Loading(isLoading = true))

                    is Resource.Success -> {
                        produce(PostsState.Loading(isLoading = false))
                        result.data?.let { posts -> produce(PostsState.Success(data = posts)) }
                    }

                    is Resource.Error -> {
                        produce(PostsState.Loading(isLoading = false))
                        produce(PostsState.Error(errorMessage = result.message ?: ""))
                    }
                }
            }
        }
    }

    private suspend fun produce(value: PostsState) {
        _state.emit(value)
    }

    override fun onCleared() {
        getPostsJob?.cancel()
        super.onCleared()
    }

}