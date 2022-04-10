package com.sample.jetbooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object AppIndexManager: ViewModel() {

    private var _appIndex: MutableStateFlow<Int> = MutableStateFlow(AppIndex.startView)

    val appIndex = _appIndex.asStateFlow()

    fun setIndex(newIndex: Int) {
        viewModelScope.launch {
            _appIndex.value = newIndex
        }
    }

}

object AppIndex {

    val startView = 0
    val worldMessageView = 1

}