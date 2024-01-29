package com.hossam.e_ramotask.feature_posts.domain.repository

import com.hossam.e_ramotask.core.util.Resource
import com.hossam.e_ramotask.feature_posts.domain.model.Posts
import kotlinx.coroutines.flow.Flow

interface IPostsRepository {

    fun getPosts(): Flow<Resource<Posts>>
}