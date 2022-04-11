package com.danielfalkedal.lunarlight

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielfalkedal.lunarlight.Documents.User
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object AppIndexManager: ViewModel() {

    private var _appIndex: MutableStateFlow<Int> = MutableStateFlow(AppIndex.startView)

    val appIndex = _appIndex.asStateFlow()

    var currentUser: User = User(
        "7D59D875-E3F4-4396-91DC-20309FD68195",
        "test",
        "12345",
        "test@email.se",
        "capricorn_6",
        2010,
        1,
        1,
    )

    fun setIndex(newIndex: Int) {
        viewModelScope.launch {
            _appIndex.value = newIndex
        }
    }

    fun initRealm(context: MainActivity) {

        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .name("users.db")
            .allowWritesOnUiThread(true)
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(config)

    }

}

object AppIndex {

    val startView = 0
    val lobbyTabView = 1

}