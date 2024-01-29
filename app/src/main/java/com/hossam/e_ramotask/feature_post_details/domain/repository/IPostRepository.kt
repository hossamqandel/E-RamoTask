package com.hossam.e_ramotask.feature_post_details.domain.repository

import com.hossam.e_ramotask.core.util.Resource
import com.hossam.e_ramotask.feature_post_details.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface IPostRepository {

    fun getPostDetail(postId: Int): Flow<Resource<Post>>
}