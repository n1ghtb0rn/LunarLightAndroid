package com.danielfalkedal.lunarlight.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielfalkedal.lunarlight.Collections.UserModel
import com.danielfalkedal.lunarlight.Responses.UsersResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

val ONLINE_USERS = 0
val USER_FRIENDS = 1

var userCategory = ONLINE_USERS

class UsersViewModel(val userModel: UserModel): ViewModel() {

    val usersStateFlow = MutableStateFlow<UsersResponse?>(null)

    init {

        viewModelScope.launch {
            userModel.getUsersDetails().collect {
                usersStateFlow.value = it
            }
        }
    }

}