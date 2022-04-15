package com.danielfalkedal.lunarlight.Firebase.Factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danielfalkedal.lunarlight.Firebase.Repos.PrivateMessageDao
import com.danielfalkedal.lunarlight.Firebase.ViewModels.PrivateMessagesViewModel
import java.lang.IllegalStateException

//Factory class to prevent ViewModel from being re-instantiated every time the View updates its state
class PrivateMessageViewModelFactory(private val privateMessageDao: PrivateMessageDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PrivateMessagesViewModel::class.java)) {
            return PrivateMessagesViewModel(privateMessageDao) as T
        }
        throw IllegalStateException()
    }

}