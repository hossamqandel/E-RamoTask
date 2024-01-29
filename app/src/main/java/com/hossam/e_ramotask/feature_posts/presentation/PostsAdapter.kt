package com.hossam.e_ramotask.feature_posts.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hossam.e_ramotask.databinding.ItemPostsBinding
import com.hossam.e_ramotask.feature_post_details.domain.model.Post
import com.hossam.e_ramotask.feature_posts.domain.model.Posts
import java.util.Collections

class PostsAdapter  : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {


    private var posts: List<Post> = Collections.emptyList()

    fun setPosts(posts: Posts){
        this.posts = posts
    }

    fun updateData(newPosts: List<Post>) {
        val diffResult = DiffUtil.calculateDiff(PostDiffCallback(posts, newPosts))
        posts = newPosts
        diffResult.dispatchUpdatesTo(this)
    }

    inner class PostViewHolder(private val binding: ItemPostsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int){
            val currentPost = posts[position]
            with(binding){
                tvUserId.text = "User ID: ".plus(currentPost.userId)
                tvId.text = "ID: ".plus(currentPost.id)
                tvTitle.text = currentPost.title
                tvBody.text = currentPost.body
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int = posts.size


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(position)
    }
}