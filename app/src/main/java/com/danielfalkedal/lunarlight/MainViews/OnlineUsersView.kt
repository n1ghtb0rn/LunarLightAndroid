package com.danielfalkedal.lunarlight.MainViews

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.danielfalkedal.lunarlight.AppIndex
import com.danielfalkedal.lunarlight.AppIndexManager

@Composable
fun OnlineUsersView() {

    Button(onClick = {
        AppIndexManager.setIndex(AppIndex.lobbyTabView)
    }) {
        Text("Back")
    }

}