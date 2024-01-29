package com.hossam.e_ramotask.feature_posts.presentation

import androidx.recyclerview.widget.DiffUtil
import com.hossam.e_ramotask.feature_post_details.domain.model.Post

class PostDiffCallback(
private val oldList: List<Post>,
private val newList: List<Post>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }


}