package com.danielfalkedal.lunarlight.MainViews

import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.danielfalkedal.lunarlight.Documents.User
import com.danielfalkedal.lunarlight.ui.theme.Aquarius
import com.danielfalkedal.lunarlight.ui.theme.Capricorn

@Composable
fun ProfileView(user: User) {

    val backgroundColor =

    Column(
        modifier = Modifier.background(Aquarius)
    ) {

        Text(user.avatar)
        Text(user.username)
        Text("Background color index = ${User.getStoneIndex(user.month.toInt(), user.day.toInt())}")

    }

}