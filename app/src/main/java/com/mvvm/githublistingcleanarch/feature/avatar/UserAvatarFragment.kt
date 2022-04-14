package com.mvvm.githublistingcleanarch.feature.avatar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mvvm.data.common.Constants.USER_DETAIL_DATA
import com.mvvm.data.local.model.UserModel
import com.mvvm.githublistingcleanarch.databinding.FragmentUserAvatarBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserAvatarFragment : Fragment() {

    private lateinit var binding: FragmentUserAvatarBinding
    private lateinit var userDetailData: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (arguments?.getSerializable(USER_DETAIL_DATA) as UserModel).let {
            userDetailData = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserAvatarBinding.inflate(layoutInflater)

        binding.user = userDetailData

        return binding.root
    }
}