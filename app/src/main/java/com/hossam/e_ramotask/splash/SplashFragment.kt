package com.hossam.e_ramotask.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hossam.e_ramotask.databinding.FragmentSplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        navigate()
    }

    private fun navigate() {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(2500)
            val destination = com.hossam.e_ramotask.R.id.postsFragment
            findNavController().navigate(destination)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}