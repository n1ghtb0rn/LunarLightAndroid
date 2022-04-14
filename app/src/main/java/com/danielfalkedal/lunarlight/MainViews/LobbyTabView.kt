package com.danielfalkedal.lunarlight.MainViews

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielfalkedal.lunarlight.AppIndexManager

@Composable
fun LobbyTabView() {

    val tabIndex = remember { mutableStateOf(1) }

    Column(
        modifier = Modifier.padding(horizontal = 8.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.weight(3.0f)
        ) {
            when (tabIndex.value) {

                1 -> WorldChatView()

                2 -> FriendsView()

                3 -> ProfileView(AppIndexManager.currentUser)

            }
        }



        Row(
            modifier = Modifier.weight(0.3f)
        ) {
            Button(onClick = {
                tabIndex.value = 1
            }) {
                Text("Lobby")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                tabIndex.value = 2
            }) {
                Text("Friends")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                tabIndex.value = 3
            }) {
                Text("Profile")
            }
        }

    }

}