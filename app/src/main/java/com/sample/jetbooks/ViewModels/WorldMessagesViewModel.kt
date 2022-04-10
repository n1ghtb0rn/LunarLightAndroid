package com.sample.jetbooks.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.jetbooks.Collections.WorldMessageModel
import com.sample.jetbooks.Responses.WorldMessagesResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
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

    fun getBooksInfo() = worldMessageModel.getWorldMessageDetails()
}