package com.danielfalkedal.lunarlight

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.danielfalkedal.lunarlight.Collections.FriendModel
import com.danielfalkedal.lunarlight.Collections.UserModel
import com.danielfalkedal.lunarlight.Collections.UserOnlineModel
import com.danielfalkedal.lunarlight.Documents.User
import com.danielfalkedal.lunarlight.Documents.UserOnline
import com.danielfalkedal.lunarlight.Realm.UserRealm

@Composable
fun LoginView() {

    LoginViewExtention().checkAutoLogin()

    val userModel = UserModel()
    userModel.listenToUsers()

    val username = remember { mutableStateOf(TextFieldValue("danne")) }
    val password = remember { mutableStateOf(TextFieldValue("12345")) }

    Column(
        //modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = username.value,
            onValueChange = {
                username.value = it
            }
        )

        TextField(
            value = password.value,
            onValueChange = {
                password.value = it
            }
        )

       Column(
           modifier = Modifier
               .fillMaxSize()
               .wrapContentSize(align = Alignment.BottomCenter)
               .padding(8.dp),

        ) {

            Button(onClick = {
                Log.d("DanneA", "1")
                val users = userModel.users

                var loginUser: User? = null

                for (user in users) {
                    if ( (user.username == username.value.text || user.email == username.value.text)
                        && user.password == password.value.text
                    ) {
                        loginUser = user
                        break
                    }
                }
                if (loginUser != null) {
                    Log.d("DanneA", "2")
                    LoginViewExtention().login(loginUser)
                    AppIndexManager.setIndex(AppIndex.lobbyTabView)
                }

            }) {
                Text("Login")
            }
        }

    }

}

class LoginViewExtention {

    fun login(user: User) {

        //Update Realm database
        val userRealm = UserRealm(
            user.id,
            user.username,
            user.password,
            user.email,
            user.avatar,
            user.year,
            user.month,
            user.day,
        )
        AppIndexManager.realmUserDao.addUser(userRealm)

        val userOnline = UserOnline(user.id, true, user.username)
        val userOnlineModel = UserOnlineModel()
        userOnlineModel.updateUserOnline(userOnline)

        AppIndexManager.currentUser = user

        AppIndexManager.friendModel = FriendModel()
        AppIndexManager.friendModel.listenToUserFriends()



    }

    fun checkAutoLogin() {

        val users = AppIndexManager.realmUserDao.getUsers()
        if (users.isEmpty()) {
            return
        }

        val userRealm = users[0]

        val user = User(
            userRealm.id,
            userRealm.username,
            userRealm.password,
            userRealm.email,
            userRealm.avatar,
            userRealm.year,
            userRealm.month,
            userRealm.day,
        )

        login(user)
    }

}