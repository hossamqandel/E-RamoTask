package com.hossam.e_ramotask.feature_post_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hossam.e_ramotask.core.util.Resource
import com.hossam.e_ramotask.feature_post_details.domain.repository.IPostRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostDetailViewModel @Inject constructor(
    private val repository: IPostRepository
) : ViewModel() {

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            throwable.printStackTrace()
        }

    private var getPostDetailJob: Job? = null

    private val _state = MutableSharedFlow<PostState>()
    val state = _state.asSharedFlow()

    fun getPostDetail(postId: Int) {
        getPostDetailJob?.cancel()
        getPostDetailJob = viewModelScope.launch(coroutineExceptionHandler + Dispatchers.Main) {
            repository.getPostDetail(postId = postId).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> produce(PostState.Loading(isLoading = true))

                    is Resource.Success -> {
                        produce(PostState.Loading(isLoading = false))
                        result.data?.let { posts -> produce(PostState.Success(data = posts)) }
                    }

                    is Resource.Error -> {
                        produce(PostState.Loading(isLoading = false))
                        produce(PostState.Error(errorMessage = result.message ?: ""))
                    }
                }
            }
        }
    }

    private suspend fun produce(value: PostState) {
        _state.emit(value)
    }

    override fun onCleared() {
        getPostDetailJob?.cancel()
        super.onCleared()
    }
}