package com.hossam.e_ramotask.core.data.remote

import com.hossam.e_ramotask.feature_post_details.domain.model.Post
import com.hossam.e_ramotask.feature_posts.domain.model.Posts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WebService {

    @GET(EndPoints.GET_ALL_POSTS)
    suspend fun fetchPosts(): Response<Posts>

    @GET(EndPoints.GET_POST_DETAILS)
    suspend fun fetchPostDetails(
        @Path("id") postId: Int
    ): Response<Post>
}