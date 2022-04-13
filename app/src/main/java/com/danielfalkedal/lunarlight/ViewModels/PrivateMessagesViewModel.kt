package com.danielfalkedal.lunarlight.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielfalkedal.lunarlight.Collections.PrivateMessageModel
import com.danielfalkedal.lunarlight.Responses.PrivateMessagesResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PrivateMessagesViewModel(val privateMessageModel: PrivateMessageModel): ViewModel() {

    val privateMessagesStateFlow = MutableStateFlow<PrivateMessagesResponse?>(null)

    init {
        viewModelScope.launch {
            privateMessageModel.getPrivateMessageDetails().collect {
                privateMessagesStateFlow.value = it
            }
        }
    }

}