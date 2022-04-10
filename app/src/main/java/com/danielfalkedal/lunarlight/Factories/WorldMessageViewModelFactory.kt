package com.danielfalkedal.lunarlight.Factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danielfalkedal.lunarlight.Collections.WorldMessageModel
import com.danielfalkedal.lunarlight.ViewModels.WorldMessagesViewModel
import java.lang.IllegalStateException

//Factory class to prevent ViewModel from being re-instanciated every time the View updates its state
class WorldMessageViewModelFactory(private val worldMessageModel: WorldMessageModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorldMessagesViewModel::class.java)) {
            return WorldMessagesViewModel(worldMessageModel) as T
        }
        throw IllegalStateException()
    }

}