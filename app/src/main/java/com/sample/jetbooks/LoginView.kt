package com.sample.jetbooks

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import com.sample.jetbooks.repo.BooksRepo
import com.sample.jetbooks.repo.UsersRepo

@Composable
fun LoginView() {

    val usersRepo = UsersRepo()
    usersRepo.listenToUsers()

    Column {

        Row {
            Button(onClick = { /*TODO*/ }) {
                Text("Login")
            }

            Text(" | ")

            Button(onClick = { /*TODO*/ }) {
                Text("Register")
            }
        }

        val username = remember { mutableStateOf(TextFieldValue()) }
        TextField(
            value = username.value,
            onValueChange = {
                username.value = it
                LocalData.message = username.value.text
            }
        )

        val password = remember { mutableStateOf(TextFieldValue()) }
        TextField(
            value = password.value,
            onValueChange = {
                password.value = it
            }
        )

        Button(onClick = {

            Log.d("Danne", "username: ${username.value}, password: ${password.value}")

            Log.d("Danne", "Users count = ${usersRepo.users.size}")

            val success = true

            if (success) {
                AppIndexManager.setIndex(AppIndex.worldMessageView)
            }

        }) {
            Text("Login")
        }

    }

}