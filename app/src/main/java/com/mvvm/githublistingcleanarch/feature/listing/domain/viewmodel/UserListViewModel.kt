package com.mvvm.githublistingcleanarch.feature.listing.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.mvvm.data.common.Constants.SEARCH_QUERY
import com.mvvm.data.common.Constants.USERS_COLLECTION
import com.mvvm.data.common.Resource
import com.mvvm.data.local.model.UserModel
import com.mvvm.data.remote.dto.UserDto
import com.mvvm.githublistingcleanarch.feature.listing.domain.usecase.local.GetUsersDbUseCase
import com.mvvm.githublistingcleanarch.feature.listing.domain.usecase.local.InsertUsersDbUseCase
import com.mvvm.githublistingcleanarch.feature.listing.domain.usecase.remote.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUsersDbUseCase: GetUsersDbUseCase,
    private val insertUsersDbUseCase: InsertUsersDbUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val fireStore: FirebaseFirestore,
) : ViewModel() {

    private val _usersLiveData = MutableLiveData<Resource<List<UserModel>>>()
    val usersLiveData: LiveData<Resource<List<UserModel>>> get() = _usersLiveData

    fun getUsers() = viewModelScope.launch {

        getUsersDbUseCase.getUsers().map { it.toUserModel() }.let { dbUserList ->
            _usersLiveData.postValue(Resource.Loading(dbUserList))


            try {
                getUsersUseCase.getUsers(SEARCH_QUERY).let { response ->
                    response.body()?.users?.let { users ->

                        sendToFirebase(users)

                        if (dbUserList.isNullOrEmpty()) {
                            _usersLiveData.postValue(Resource.Success(users.map { it.toUserModel() }))
                            insertUsersDbUseCase.insertUsers(users.map { it.toUserEntity() })
                        } else {
                            _usersLiveData.postValue(Resource.Success(dbUserList))
                            insertUsersDbUseCase.insertUsers(dbUserList.map { it.toUserEntity() })
                        }

                    }

                }
            } catch (e: HttpException) {
                _usersLiveData.postValue(
                    Resource.Error(
                        e.localizedMessage ?: "An unexpected error occurred",
                        dbUserList
                    )
                )
            } catch (e: IOException) {
                _usersLiveData.postValue(
                    Resource.Error(
                        "Couldn't reach server. Check your internet connection.",
                        dbUserList
                    )
                )
            }
        }

    }

    private fun sendToFirebase(users: List<UserDto>) {
        users.map {
            fireStore.collection(USERS_COLLECTION).document(it.id.toString()).set(it)
        }
    }

    fun setUserFavorite(username: String) = viewModelScope.launch {
        getUsersDbUseCase.getUsers().map { it.toUserModel() }.let { dbUserList ->
            dbUserList.find { it.username == username }?.let { userModel ->

                val isFav = userModel.isFav
                userModel.isFav = !isFav
                insertUsersDbUseCase.insertUsers(dbUserList.map { it.toUserEntity() })

                _usersLiveData.postValue(Resource.Success(dbUserList))
            }
        }
    }

    fun searchUser(query: String) = viewModelScope.launch {
        try {
            getUsersUseCase.getUsers(query).let { response ->
                response.body()?.users?.let { users ->
                    insertUsersDbUseCase.insertUsers(users.map { it.toUserEntity() })

                    _usersLiveData.postValue(Resource.Loading())

                    if (response.isSuccessful)
                        _usersLiveData.postValue(Resource.Success(users.map { it.toUserModel() }))
                    else
                        _usersLiveData.postValue(
                            Resource.Error("Permission denied.")
                        )
                }

            }
        } catch (e: HttpException) {
            _usersLiveData.postValue(
                Resource.Error(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            _usersLiveData.postValue(
                Resource.Error(
                    "Couldn't reach server. Check your internet connection."
                )
            )
        }
    }
}