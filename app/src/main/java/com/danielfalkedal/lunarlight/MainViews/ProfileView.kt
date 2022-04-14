package com.danielfalkedal.lunarlight.MainViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Documents.Friend
import com.danielfalkedal.lunarlight.Documents.User
import com.danielfalkedal.lunarlight.MainActivity
import com.danielfalkedal.lunarlight.ui.theme.getUserBackgroundColor
import java.util.*


@Composable
fun ProfileView(user: User) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    Color.White,
                    getUserBackgroundColor(user.month, user.day)
                )
            ))
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (AppIndexManager.currentUser.id != user.id) {
            Button(onClick = {
                val friend = Friend(user.id)
                AppIndexManager.friendModel.addFriend(friend)
            }) {
                Text("Add friend")
            }
        }

        Spacer(modifier = Modifier.weight(0.5f))

        Text(user.username)

        Image(
            painter = painterResource(User.getAvatarResource(user.avatar)),
            contentDescription = "Contact profile picture",
        )

        Spacer(modifier = Modifier.weight(0.5f))

    }

}