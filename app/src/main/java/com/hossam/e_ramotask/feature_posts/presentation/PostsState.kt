package com.hossam.e_ramotask.feature_posts.presentation

import com.hossam.e_ramotask.feature_posts.domain.model.Posts

sealed interface PostsState {

    data class Loading(val isLoading: Boolean): PostsState
    data class Success(val data: Posts): PostsState
    data class Error(val errorMessage: String): PostsState
}