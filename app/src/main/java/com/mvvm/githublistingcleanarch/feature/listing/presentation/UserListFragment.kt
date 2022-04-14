package com.mvvm.githublistingcleanarch.feature.listing.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.mvvm.data.common.Constants.USERNAME
import com.mvvm.data.common.Resource
import com.mvvm.data.local.model.UserModel
import com.mvvm.githublistingcleanarch.R
import com.mvvm.githublistingcleanarch.databinding.FragmentUserListBinding
import com.mvvm.githublistingcleanarch.feature.listing.domain.viewmodel.UserListViewModel
import com.mvvm.githublistingcleanarch.feature.listing.presentation.adapter.UserListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : Fragment() {
    private lateinit var binding: FragmentUserListBinding
    private val viewModel: UserListViewModel by viewModels()
    private var userPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(layoutInflater)

        observeUsers()
        viewModel.getUsers()
        setSearchUserEtListener()

        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.etSearchUser.text.clear()
    }

    private fun observeUsers() {
        viewModel.apply {
            usersLiveData.observe(viewLifecycleOwner) { response ->

                setUserList(response)

                when (response) {
                    is Resource.Loading -> {
                        response.data?.let {
                            binding.listingProgress.visibility = GONE
                        } ?: run {
                            binding.listingProgress.visibility = VISIBLE
                        }
                    }
                    is Resource.Success -> {
                        binding.listingProgress.visibility = GONE
                    }
                    is Resource.Error -> {
                        Toast.makeText(
                            context,
                            response.message ?: getString(R.string.error_message),
                            Toast.LENGTH_SHORT
                        ).show()

                        response.data?.let {
                            binding.listingProgress.visibility = GONE
                        }
                    }
                }

            }
        }
    }

    private fun setUserList(response: Resource<List<UserModel>>) {
        response.data?.let { userList ->
            binding.rvUserList.apply {
                adapter =
                    UserListAdapter(
                        users = userList,
                        listener = object : UserListAdapter.ClickListener {
                            override fun onItemClick(user: UserModel) {
                                val bundle = bundleOf(USERNAME to user.username)
                                Navigation.findNavController(binding.root)
                                    .navigate(
                                        R.id.action_userListFragment_to_userDetailFragment,
                                        bundle
                                    )
                            }

                            override fun onUpdateFavoriteClick(username: String, position: Int) {
                                viewModel.setUserFavorite(username)
                                userPosition = position
                            }
                        })
                scrollToPosition(userPosition)
            }
        }
    }

    private fun setSearchUserEtListener() {
        binding.etSearchUser.addTextChangedListener {
            if (it.toString().length >= 3) {
                lifecycleScope.launch {
                    viewModel.searchUser(it.toString())
                }
            }
        }
    }

}