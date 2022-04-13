package com.danielfalkedal.lunarlight.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielfalkedal.lunarlight.Collections.WorldMessageModel
import com.danielfalkedal.lunarlight.Responses.WorldMessagesResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WorldMessagesViewModel(val worldMessageModel: WorldMessageModel): ViewModel() {

    val worldMessagesStateFlow = MutableStateFlow<WorldMessagesResponse?>(null)

    init {
        viewModelScope.launch {
            worldMessageModel.getWorldMessageDetails().collect {
                worldMessagesStateFlow.value = it
            }
        }
    }

}