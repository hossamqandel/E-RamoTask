package com.hossam.e_ramotask.feature_posts.data.repository

import com.hossam.e_ramotask.core.data.remote.WebService
import com.hossam.e_ramotask.core.util.Resource
import com.hossam.e_ramotask.feature_posts.domain.model.Posts
import com.hossam.e_ramotask.feature_posts.domain.repository.IPostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.http.HTTP
import java.io.IOException
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_NO_CONTENT
import java.net.HttpURLConnection.HTTP_OK
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val webService: WebService
): IPostsRepository {

    override fun getPosts(): Flow<Resource<Posts>> = flow {

        emit(Resource.Loading())

        try {
            val result = webService.fetchPosts()
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