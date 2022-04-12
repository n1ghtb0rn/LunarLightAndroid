package com.danielfalkedal.lunarlight.MainViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.danielfalkedal.lunarlight.Documents.User
import com.danielfalkedal.lunarlight.Utils.LocalData
import com.danielfalkedal.lunarlight.ui.theme.getColorByString

@Composable
fun ProfileView(user: User) {

    Column(
        modifier = Modifier.background(user.getColor())
    ) {

        Text(user.avatar)
        Text(user.username)
        Text("Background color index = ${User.getStoneIndex(user.month.toInt(), user.day.toInt())}")

    }

}