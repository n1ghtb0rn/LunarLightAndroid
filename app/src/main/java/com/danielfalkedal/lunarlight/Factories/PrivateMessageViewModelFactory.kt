package com.danielfalkedal.lunarlight.Factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danielfalkedal.lunarlight.Collections.PrivateMessageModel
import com.danielfalkedal.lunarlight.Collections.WorldMessageModel
import com.danielfalkedal.lunarlight.ViewModels.PrivateMessagesViewModel
import com.danielfalkedal.lunarlight.ViewModels.WorldMessagesViewModel
import java.lang.IllegalStateException

//Factory class to prevent ViewModel from being re-instantiated every time the View updates its state
class PrivateMessageViewModelFactory(private val privateMessageModel: PrivateMessageModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PrivateMessagesViewModel::class.java)) {
            return PrivateMessagesViewModel(privateMessageModel) as T
        }
        throw IllegalStateException()
    }

}