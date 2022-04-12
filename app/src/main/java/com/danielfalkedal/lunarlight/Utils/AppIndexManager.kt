package com.danielfalkedal.lunarlight

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielfalkedal.lunarlight.Collections.FriendModel
import com.danielfalkedal.lunarlight.Collections.UserOnlineModel
import com.danielfalkedal.lunarlight.Documents.User
import com.danielfalkedal.lunarlight.Realm.RealmUserDao
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object AppIndexManager: ViewModel() {

    private var _appIndex: MutableStateFlow<Int> = MutableStateFlow(AppIndex.startView)
    val appIndex = _appIndex.asStateFlow()

    lateinit var realmUserDao: RealmUserDao
    lateinit var config: RealmConfiguration

    val userOnlineModel = UserOnlineModel()
    var friendModel = FriendModel()

    var profileUser: User? = null

    init {
        userOnlineModel.listenToUsersOnline()
    }

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
        realmUserDao = RealmUserDao()

    }

}

object AppIndex {

    val startView = 0
    val lobbyTabView = 1
    val onlineUsersView = 2

}