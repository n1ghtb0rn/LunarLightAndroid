package com.danielfalkedal.lunarlight.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Documents.PrivateMessage
import kotlinx.coroutines.launch

object SharedViewModel: ViewModel() {

    var privateMessages by mutableStateOf<MutableList<PrivateMessage>?>(null)
        private set

    fun updatePrivateMessages(newPrivateMessages: ArrayList<PrivateMessage>) {
        viewModelScope.launch {
            privateMessages = newPrivateMessages
        }

    }

}