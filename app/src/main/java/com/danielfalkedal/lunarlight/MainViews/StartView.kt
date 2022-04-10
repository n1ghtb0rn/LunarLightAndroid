package com.danielfalkedal.lunarlight

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.Button
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp


@Composable
fun StartView() {

    var showLoginView = remember { mutableStateOf(true)}

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Button(onClick = {
                showLoginView.value = true
            }) {
                Text("Login")
            }

            Text(" | ")

            Button(onClick = {
                showLoginView.value = false
            }) {
                Text("Register")
            }
        }

        when (showLoginView.value) {

            true -> LoginView()

            false -> RegisterView()

        }

    }

}