package com.danielfalkedal.lunarlight.MainViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Documents.Friend
import com.danielfalkedal.lunarlight.Documents.User
import com.danielfalkedal.lunarlight.Utils.LocalData
import java.util.*

@Composable
fun ProfileView(user: User) {

    Column(
        modifier = Modifier
            .background(User.getColor(user))
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
        Text(user.avatar)
        Text(user.username)
        Text("Background color index = ${User.getStoneIndex(user.month.toInt(), user.day.toInt())}")

    }

}