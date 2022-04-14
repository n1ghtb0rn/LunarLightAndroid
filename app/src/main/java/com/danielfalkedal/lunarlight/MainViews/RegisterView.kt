package com.danielfalkedal.lunarlight

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.danielfalkedal.lunarlight.Collections.UserModel
import com.danielfalkedal.lunarlight.Documents.User
import com.danielfalkedal.lunarlight.Utils.LocalData
import java.util.*
import javax.security.auth.login.LoginException

@Composable
fun RegisterView() {

    var username = remember { mutableStateOf("") }
    var year = remember { mutableStateOf("") }
    var month = remember { mutableStateOf("") }
    var day = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    Column(
        //modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



        Text("Username")
        TextField(
            value = username.value,
            onValueChange = {
                username.value = it
            }
        )

        Text("Year")
        TextField(
            value = year.value,
            onValueChange = {
                year.value = it
            }
        )

        Text("Month")
        TextField(
            value = month.value,
            onValueChange = {
                month.value = it
            }
        )

        Text("Day")
        TextField(
            value = day.value,
            onValueChange = {
                day.value = it
            }
        )

        Text("Email")
        TextField(
            value = email.value,
            onValueChange = {
                email.value = it
            }
        )

        Text("Password")
        TextField(
            value = password.value,
            onValueChange = {
                password.value = it
            }
        )

        Button(onClick = {
            val newUser: User = User(
                UUID.randomUUID().toString(),
                username.value,
                password.value,
                email.value,
                "leo_1",
                year.value.toLong(),
                month.value.toLong(),
                day.value.toLong()
            )
            RegisterViewExtension().createUser(newUser)
            LoginViewExtention().login(newUser)
            AppIndexManager.setIndex(AppIndex.welcomeView)
        }) {
            Text("Sign up")
        }

    }

}

class RegisterViewExtension {

    fun createUser(newUser: User) {

        val stoneIndex = User.getStoneIndex(newUser.month.toInt(), newUser.day.toInt())
        var stoneType = LocalData.profileBackground[stoneIndex]
        val avatar = stoneType.lowercase() + "_1"

        newUser.avatar = avatar

        val userModel = UserModel()
        userModel.createUser(newUser)

    }

}

