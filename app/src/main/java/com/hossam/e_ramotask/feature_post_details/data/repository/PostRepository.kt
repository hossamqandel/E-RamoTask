package com.hossam.e_ramotask.feature_post_details.data.repository

import com.hossam.e_ramotask.core.data.remote.WebService
import com.hossam.e_ramotask.core.util.Resource
import com.hossam.e_ramotask.feature_post_details.domain.model.Post
import com.hossam.e_ramotask.feature_post_details.domain.repository.IPostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val webService: WebService
): IPostRepository {


    override fun getPostDetail(postId: Int): Flow<Resource<Post>> = flow {
        emit(Resource.Loading())

        try {
            val result = webService.fetchPostDetails(postId = postId)

            when(result.code()){
                HttpURLConnection.HTTP_OK -> { result.body()?.let { posts -> emit(Resource.Success(posts)) } }
                HttpURLConnection.HTTP_NOT_FOUND -> emit(Resource.Error("Sorry there is no content to show"))
                HttpURLConnection.HTTP_NO_CONTENT -> emit(Resource.Error("Sorry there is no content to show"))
            }

        }catch (e: HttpException){
            e.printStackTrace()
            emit(Resource.Error("Please check your Internet connection and try again"))
        }
    }
}