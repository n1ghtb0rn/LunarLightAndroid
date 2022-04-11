package com.danielfalkedal.lunarlight

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
import com.danielfalkedal.lunarlight.Collections.UserModel
import com.danielfalkedal.lunarlight.Collections.UserOnlineModel
import com.danielfalkedal.lunarlight.Documents.UserOnline

@Composable
fun LoginView() {

    val usersRepo = UserModel()
    usersRepo.listenToUsers()

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

                val users = usersRepo.users

                for (user in users) {
                    if ( (user.username == username.value.text || user.email == username.value.text)
                        && user.password == password.value.text
                    ) {

                        val userOnline = UserOnline(user.id, true, user.username)
                        val userOnlineModel = UserOnlineModel()
                        userOnlineModel.updateUserOnline(userOnline)

                        AppIndexManager.currentUser = user
                        AppIndexManager.setIndex(AppIndex.lobbyTabView)
                        break
                    }
                }

            }) {
                Text("Login")
            }
        }

    }



}