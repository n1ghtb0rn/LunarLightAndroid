package com.danielfalkedal.lunarlight.MainViews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Collections.UserModel
import com.danielfalkedal.lunarlight.Documents.User
import com.danielfalkedal.lunarlight.Utils.LocalData
import java.util.*

@Composable
fun LobbyTabView() {

    val tabIndex = remember { mutableStateOf(1) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row {
            Button(onClick = {
                tabIndex.value = 1
            }) {
                Text("Lobby")
            }

            Button(onClick = {
                tabIndex.value = 2
            }) {
                Text("Friends")
            }

            Button(onClick = {
                tabIndex.value = 3
            }) {
                Text("Profile")
            }
        }

        when (tabIndex.value) {

            1 -> WorldMessageView()

            2 -> FriendsView()

            3 -> ProfileView(AppIndexManager.currentUser)

        }

    }

}