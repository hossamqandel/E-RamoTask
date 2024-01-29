package com.hossam.e_ramotask.feature_post_details.data.repository

import com.hossam.e_ramotask.core.data.remote.WebService
import com.hossam.e_ramotask.core.util.Resource
import com.hossam.e_ramotask.feature_post_details.domain.model.Post
import com.hossam.e_ramotask.feature_post_details.domain.repository.IPostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_NO_CONTENT
import java.net.HttpURLConnection.HTTP_OK
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val webService: WebService
): IPostRepository {


    override fun getPostDetail(postId: Int): Flow<Resource<Post>> = flow {
        emit(Resource.Loading())

        try {
            val result = webService.fetchPostDetails(postId = postId)

            when(result.code()){
                HTTP_OK -> { result.body()?.let { posts -> emit(Resource.Success(posts)) } }
                HTTP_NOT_FOUND -> emit(Resource.Error("The source that you are looking for not founded"))
                HTTP_NO_CONTENT -> emit(Resource.Error("Sorry there is no content to show"))
            }
        } catch (e: IOException){
            e.printStackTrace()
            emit(Resource.Error("Please check your Internet connection and try again"))
        }
    }
}