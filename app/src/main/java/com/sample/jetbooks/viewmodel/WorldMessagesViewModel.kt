package com.sample.jetbooks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.jetbooks.repo.WorldMessagesRepo
import com.sample.jetbooks.response.WorldMessagesResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WorldMessagesViewModel(val worldMessagesRepo: WorldMessagesRepo): ViewModel() {

    val worldMessagesStateFlow = MutableStateFlow<WorldMessagesResponse?>(null)

    init {
        viewModelScope.launch {
            worldMessagesRepo.getWorldMessageDetails().collect {
                worldMessagesStateFlow.value = it
            }
        }
    }

    fun getBooksInfo() = worldMessagesRepo.getWorldMessageDetails()
}