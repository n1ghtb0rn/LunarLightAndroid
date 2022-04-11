package com.danielfalkedal.lunarlight.Factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danielfalkedal.lunarlight.Collections.UserModel
import com.danielfalkedal.lunarlight.ViewModels.UsersViewModel
import java.lang.IllegalStateException

//Factory class to prevent ViewModel from being re-instantiated every time the View updates its state
class UserViewModelFactory(private val userModel: UserModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            return UsersViewModel(userModel) as T
        }
        throw IllegalStateException()
    }

}