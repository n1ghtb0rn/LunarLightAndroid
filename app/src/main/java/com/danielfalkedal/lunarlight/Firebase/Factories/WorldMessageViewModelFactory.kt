package com.danielfalkedal.lunarlight.Firebase.Factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danielfalkedal.lunarlight.Firebase.Repos.WorldMessageDao
import com.danielfalkedal.lunarlight.Firebase.ViewModels.WorldMessagesViewModel
import java.lang.IllegalStateException

//Factory class to prevent ViewModel from being re-instantiated every time the View updates its state
class WorldMessageViewModelFactory(private val worldMessageDao: WorldMessageDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorldMessagesViewModel::class.java)) {
            return WorldMessagesViewModel(worldMessageDao) as T
        }
        throw IllegalStateException()
    }

}