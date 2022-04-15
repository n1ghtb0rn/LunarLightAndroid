package com.danielfalkedal.lunarlight.Firebase.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielfalkedal.lunarlight.Firebase.Repos.WorldMessageDao
import com.danielfalkedal.lunarlight.Responses.WorldMessagesResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WorldMessagesViewModel(val worldMessageDao: WorldMessageDao): ViewModel() {

    val worldMessagesStateFlow = MutableStateFlow<WorldMessagesResponse?>(null)

    init {
        viewModelScope.launch {
            worldMessageDao.getWorldMessageDetails().collect {
                worldMessagesStateFlow.value = it
            }
        }
    }

}