package com.sample.jetbooks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.sample.jetbooks.data.LocalData
import com.sample.jetbooks.repo.UsersRepo

@Composable
fun LoginView() {

    val usersRepo = UsersRepo()
    usersRepo.listenToUsers()

    val username = remember { mutableStateOf(TextFieldValue("danne")) }
    val password = remember { mutableStateOf(TextFieldValue("12345")) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row {
            Button(onClick = { /*TODO*/ }) {
                Text("Login")
            }

            Text(" | ")

            Button(onClick = { /*TODO*/ }) {
                Text("Register")
            }
        }


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

        Button(onClick = {

            val users = usersRepo.users

            for (user in users) {
                if ( (user.username == username.value.text || user.email == username.value.text)
                        && user.password == password.value.text
                ) {
                    AppIndexManager.currentUser = user
                    AppIndexManager.setIndex(AppIndex.worldMessageView)
                    break
                }
            }

        }) {
            Text("Login")
        }

    }

}