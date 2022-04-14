package com.mvvm.githublistingcleanarch.feature.detail.domain.viewmodel

import androidx.lifecycle.*
import com.mvvm.data.common.Constants.USERNAME
import com.mvvm.data.common.Resource
import com.mvvm.data.local.model.UserModel
import com.mvvm.githublistingcleanarch.feature.detail.domain.usecase.local.GetUserDbUseCase
import com.mvvm.githublistingcleanarch.feature.detail.domain.usecase.local.UpdateUserDbUseCase
import com.mvvm.githublistingcleanarch.feature.detail.domain.usecase.remote.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDbUseCase: GetUserDbUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserDbUseCase: UpdateUserDbUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private lateinit var username: String
    private var isFav: Boolean = false

    init {
        savedStateHandle.get<String>(USERNAME)?.let {
            username = it
            getUser(it)
        }
    }

    private val _usersLiveData = MutableLiveData<Resource<UserModel>>()
    val usersLiveData: LiveData<Resource<UserModel>> get() = _usersLiveData

    private fun getUser(username: String) = viewModelScope.launch {

        getUserDbUseCase.getUser(username).isFav?.let {
            isFav = it
        }

        getUserDbUseCase.getUser(username).toUserModel().let { dbUser ->
            _usersLiveData.postValue(Resource.Loading(dbUser))

            try {
                getUserUseCase.getUser(username).let { response ->
                    response.body()?.let {
                        updateUserDbUseCase.updateUser(it.toUserEntity(isFav))
                    }

                }
            } catch (e: HttpException) {//Invalid Response
                _usersLiveData.postValue(
                    Resource.Error(
                        e.localizedMessage ?: "An unexpected error occurred"

                    )
                )
            } catch (e: IOException) {//Server,connection problems
                _usersLiveData.postValue(
                    Resource.Error(
                        "Couldn't reach server. Check your internet connection."

                    )
                )
            }
        }

        getUserDbUseCase.getUser(username).toUserModel().let { dbUser ->
            _usersLiveData.postValue(Resource.Success(dbUser))
        }
    }

    fun setUserFavorite() = viewModelScope.launch {
        getUserDbUseCase.getUser(username).toUserModel().let { dbUser ->
            isFav = !isFav
            dbUser.isFav = isFav
            updateUserDbUseCase.updateUser(dbUser.toUserEntity())
            _usersLiveData.postValue(Resource.Success(dbUser))
        }
    }

}