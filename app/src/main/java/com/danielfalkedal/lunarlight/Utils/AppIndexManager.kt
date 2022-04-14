package com.danielfalkedal.lunarlight

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
import java.lang.Exception

object AppIndexManager: ViewModel() {

    private var _appIndex: MutableStateFlow<Int> = MutableStateFlow(AppIndex.welcomeView)
    val appIndex = _appIndex.asStateFlow()

    lateinit var realmUserDao: RealmUserDao
    lateinit var config: RealmConfiguration

    val userOnlineModel = UserOnlineModel()
    lateinit var friendModel: FriendModel

    var profileUser: User
    var privateChatUser: User

    var currentUser: User

    val testUser: User = User(
        "02ea7ee1-571d-40fb-9c90-4e12a6b7ba69",
        "android",
        "12345",
        "android@email.se",
        "taurus_1",
        2000,
        5,
        5,
        "My profile info."
    )

    init {
        currentUser = testUser
        profileUser = testUser
        privateChatUser = testUser
        userOnlineModel.listenToUsersOnline()
    }

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
    val privateChatView = 3
    val welcomeView = 4

}