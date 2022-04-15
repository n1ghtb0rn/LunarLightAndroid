package com.danielfalkedal.lunarlight.Firebase.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielfalkedal.lunarlight.Firebase.Repos.UserDao
import com.danielfalkedal.lunarlight.Responses.UsersResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

val ONLINE_USERS = 0
val USER_FRIENDS = 1

var userCategory = ONLINE_USERS

class UsersViewModel(val userDao: UserDao): ViewModel() {

    val usersStateFlow = MutableStateFlow<UsersResponse?>(null)

    init {

        viewModelScope.launch {
            userDao.getUsersDetails().collect {
                usersStateFlow.value = it
            }
        }
    }

}