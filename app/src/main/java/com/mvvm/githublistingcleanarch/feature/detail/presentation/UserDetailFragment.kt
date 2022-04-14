package com.mvvm.githublistingcleanarch.feature.detail.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.mvvm.data.common.Constants.USER_DETAIL_DATA
import com.mvvm.data.common.Resource
import com.mvvm.data.local.model.UserModel
import com.mvvm.githublistingcleanarch.R
import com.mvvm.githublistingcleanarch.databinding.FragmentUserDetailBinding
import com.mvvm.githublistingcleanarch.feature.detail.domain.viewmodel.UserDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment() {
    private val viewModel: UserDetailViewModel by viewModels()
    private lateinit var binding: FragmentUserDetailBinding
    private lateinit var userData: UserModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(layoutInflater)

        observeUser()

        binding.ivUserAvatar.setOnClickListener {
            val bundle = bundleOf(USER_DETAIL_DATA to userData)
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_userDetailFragment_to_userAvatarFragment, bundle)
        }

        binding.ivFavorite.setOnClickListener {
            viewModel.setUserFavorite()
        }

        return binding.root
    }

    private fun observeUser() {
        viewModel.apply {
            usersLiveData.observe(viewLifecycleOwner) { response ->
                binding.user = response.data
                response.data?.let { userData = it }

                when (response) {
                    is Resource.Loading -> {
                        response.data?.let {
                            binding.userDetailProgress.visibility = GONE
                        } ?: run {
                            binding.userDetailProgress.visibility = VISIBLE
                        }
                    }
                    is Resource.Success -> {
                        binding.userDetailProgress.visibility = GONE
                    }
                    is Resource.Error -> {
                        Toast.makeText(
                            context,
                            response.message ?: getString(R.string.error_message),
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.userDetailProgress.visibility = GONE
                    }
                }

            }
        }
    }
}