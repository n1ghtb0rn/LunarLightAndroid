package com.danielfalkedal.lunarlight

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.danielfalkedal.lunarlight.Firebase.Repos.FriendDao
import com.danielfalkedal.lunarlight.Firebase.Repos.UserDao
import com.danielfalkedal.lunarlight.Firebase.Repos.UserOnlineDao
import com.danielfalkedal.lunarlight.Firebase.Repos.Models.User
import com.danielfalkedal.lunarlight.Firebase.Repos.Models.UserOnline
import com.danielfalkedal.lunarlight.Realm.Repos.Models.UserRealm
import com.danielfalkedal.lunarlight.Utils.Encryption
import com.danielfalkedal.lunarlight.ui.theme.WhiteTransparent

@Composable
fun LoginView() {

    LoginViewExtention().checkAutoLogin()

    val userModel = UserDao()
    userModel.listenToUsers()

    val username = remember { mutableStateOf(TextFieldValue("")) }
    val password = remember { mutableStateOf(TextFieldValue("")) }
    val passwordSecured = remember { mutableStateOf(true) }

    Column(
        //modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Username / Email")
        TextField(
            value = username.value,
            onValueChange = {
                username.value = it
            },
            Modifier.background(WhiteTransparent).widthIn(250.dp)
        )

        Text("Password")
        Row() {
            TextField(
                value = password.value,
                onValueChange = {
                    password.value = it
                },
                visualTransformation = if (passwordSecured.value) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.background(WhiteTransparent).widthIn(200.dp)
            )

            Button(onClick = {
                passwordSecured.value = !passwordSecured.value

            }, modifier = Modifier.widthIn(50.dp)) {
                Image(
                    painter = if (passwordSecured.value) painterResource(R.drawable.eye_slash) else painterResource(R.drawable.eye),
                    contentDescription = "Contact profile picture",
                )
            }
        }

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

                val token = Encryption().getTokenByHttpRequest(password.value.text)

                for (user in users) {
                    if ( (user.username == username.value.text || user.email == username.value.text)
                        && user.password == token
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
            user.profile_info
        )
        AppIndexManager.realmUserDao.deleteAllUsers()
        AppIndexManager.realmUserDao.createUser(userRealm)

        val userOnline = UserOnline(user.id, true, user.username)
        val userOnlineModel = UserOnlineDao()
        userOnlineModel.updateUserOnline(userOnline)

        AppIndexManager.loggedInUser = user

        AppIndexManager.friendDao = FriendDao()
        AppIndexManager.friendDao.listenToUserFriends()

        AppIndexManager.setIndex(AppIndex.lobbyTabView)

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
            userRealm.profile_info
        )

        login(user)
    }

}