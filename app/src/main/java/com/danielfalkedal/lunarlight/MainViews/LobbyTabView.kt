package com.danielfalkedal.lunarlight.MainViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Firebase.Repos.Models.User
import com.danielfalkedal.lunarlight.ui.theme.FullTransparent
import com.danielfalkedal.lunarlight.ui.theme.Purple700

@Composable
fun LobbyTabView() {

    val tabIndex = remember { mutableStateOf(1) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            FullTransparent,
                            Purple700
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {}

        Image(
            painter = painterResource(User.getAvatarResource("star_heaven")),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
        )

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

                    3 -> ProfileView(AppIndexManager.loggedInUser)

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

}