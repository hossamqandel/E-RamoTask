package com.hossam.e_ramotask.feature_post_details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hossam.e_ramotask.core.extensions.showSnackbar
import com.hossam.e_ramotask.databinding.FragmentPostDetailBinding
import com.hossam.e_ramotask.feature_post_details.domain.model.Post
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailFragment : Fragment() {


    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!
    private val navArgs: PostDetailFragmentArgs by navArgs()
    private val postId by lazy { navArgs.postId }
    private val viewModel: PostDetailViewModel by viewModels()

    private fun onBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPress()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPostDetail()
        onRefresh()
        collectState()
        clickEvent()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    when (state) {
                        is PostState.Loading -> shouldShowLoading(state.isLoading)
                        is PostState.Success -> {
                            hideErrorImage()
                            showDetailCard()
                            setupPostDetail(state.data)
                        }

                        is PostState.Error -> {
                            hideDetailCard()
                            showErrorImage()
                            showSnackbar(state.errorMessage)
                        }
                    }
                }
            }
        }
    }

    private fun clickEvent() {
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
    }


    private fun setupPostDetail(post: Post) {
        with(binding) {
            tvUserId.text = "USER ID: ".plus("${post.userId}")
            tvId.text = "ID: ".plus("${post.id}")
            tvTitle.text = post.title
            tvBody.text = post.body
        }
    }

    private fun showDetailCard() {
        binding.cardView.visibility = View.VISIBLE
    }

    private fun hideDetailCard() {
        binding.cardView.visibility = View.GONE
    }

    private fun showErrorImage() {
        binding.ivError.visibility = View.VISIBLE
    }

    private fun hideErrorImage() {
        binding.ivError.visibility = View.GONE
    }

    private fun shouldShowLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun onRefresh() {
        binding.root.setOnRefreshListener {
            getPostDetail()
            binding.root.isRefreshing = false
        }
    }

    private fun getPostDetail() {
        viewModel.getPostDetail(postId)
    }

}