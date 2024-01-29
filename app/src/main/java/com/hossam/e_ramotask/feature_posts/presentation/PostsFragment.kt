package com.hossam.e_ramotask.feature_posts.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hossam.e_ramotask.core.extensions.showSnackbar
import com.hossam.e_ramotask.core.util.ClickListener
import com.hossam.e_ramotask.databinding.FragmentPostsBinding
import com.hossam.e_ramotask.feature_post_details.domain.model.Post
import com.hossam.e_ramotask.feature_posts.domain.model.Posts
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsFragment : Fragment() {

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PostsViewModel by viewModels()

    private lateinit var postsAdapter: PostsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        bindPostsAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onRefresh()
        collectState()
        clickEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clickEvents(){
        postsAdapter.setOnItemClickListener(object : ClickListener {
            override fun <T> onItemClick(value: T, position: Int) {
                if (value is Post){
                    val action = PostsFragmentDirections.actionPostsFragmentToPostDetailFragment(value.id)
                    findNavController().navigate(action)
                }
            }
        })
    }

    private fun bindPostsAdapter() {
        postsAdapter = PostsAdapter()
        binding.rvPosts.adapter = postsAdapter
    }

    private fun showErrorImage(){
        binding.ivError.visibility = View.VISIBLE
    }

    private fun hideErrorImage(){
        binding.ivError.visibility = View.GONE
    }

    private fun showRecycler(){
        binding.rvPosts.visibility = View.VISIBLE
    }

    private fun hideRecycler(){
        binding.rvPosts.visibility = View.GONE
    }
    
    private fun setupRecycler(data: Posts){
        with(binding){
            postsAdapter.setPosts(data)
            postsAdapter.notifyDataSetChanged()
        }
    }
    
    private fun collectState(){
        viewModel.getPosts()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main){
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collectLatest { state -> 
                    when(state){
                        is PostsState.Loading -> shouldShowLoading(state.isLoading)
                        is PostsState.Success -> {
                            setupRecycler(state.data)
                            hideErrorImage()
                            showRecycler()
                        }
                        is PostsState.Error -> {
                            hideRecycler()
                            showErrorImage()
                            showSnackbar(state.errorMessage)
                        }
                    }
                }
            }
        }
    }

    private fun shouldShowLoading(isLoading: Boolean){
        binding.progressBar.isVisible = isLoading
    }

    private fun onRefresh(){
        binding.root.setOnRefreshListener {
            viewModel.getPosts()
            binding.root.isRefreshing = false
        }
    }

}