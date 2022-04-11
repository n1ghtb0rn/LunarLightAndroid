package com.danielfalkedal.lunarlight.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielfalkedal.lunarlight.Collections.UserModel
import com.danielfalkedal.lunarlight.Collections.UserOnlineModel
import com.danielfalkedal.lunarlight.Collections.WorldMessageModel
import com.danielfalkedal.lunarlight.Responses.UsersResponse
import com.danielfalkedal.lunarlight.Responses.WorldMessagesResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UsersViewModel(val userModel: UserModel): ViewModel() {

    val usersOnlineStateFlow = MutableStateFlow<UsersResponse?>(null)

    init {
        viewModelScope.launch {
            userModel.getUserDetails().collect {
                usersOnlineStateFlow.value = it
            }
        }
    }

    fun getBooksInfo() = userModel.getUserDetails()
}