package com.hossam.e_ramotask.di

import com.hossam.e_ramotask.feature_post_details.data.repository.PostRepository
import com.hossam.e_ramotask.feature_post_details.domain.repository.IPostRepository
import com.hossam.e_ramotask.feature_posts.data.repository.PostsRepository
import com.hossam.e_ramotask.feature_posts.domain.repository.IPostsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindPostsRepository(postsRepository: PostsRepository): IPostsRepository

    @Binds
    @ViewModelScoped
    abstract fun bindPostRepository(postRepository: PostRepository): IPostRepository

}