package com.danielfalkedal.lunarlight.MainViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Firebase.Repos.Models.Friend
import com.danielfalkedal.lunarlight.Firebase.Repos.Models.User
import com.danielfalkedal.lunarlight.ui.theme.getUserBackgroundColor


@Composable
fun ProfileView(user: User) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        getUserBackgroundColor(user.month, user.day)
                    )
                )
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = user.username,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
        )

        if (AppIndexManager.loggedInUser.id != user.id) {
            Button(onClick = {
                val friend = Friend(user.id)
                AppIndexManager.friendDao.addFriend(friend)
            }) {
                Text("Add friend")
            }
        }

        Column(Modifier.fillMaxSize(0.3f),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(User.getAvatarResource(user.avatar)),
                contentDescription = "Contact profile picture",
            )
        }

        Row() {
            Button(onClick = { /*TODO*/ }) {
                Text("About me")
            }

            Button(onClick = { /*TODO*/ }) {
                Text("Stone name")
            }
        }

        Spacer(Modifier.fillMaxHeight())

    }

}