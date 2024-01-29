package com.hossam.e_ramotask.feature_post_details.presentation

import com.hossam.e_ramotask.feature_post_details.domain.model.Post

sealed interface PostState {

    data class Loading(val isLoading: Boolean): PostState
    data class Success(val data: Post): PostState
    data class Error(val errorMessage: String): PostState
}