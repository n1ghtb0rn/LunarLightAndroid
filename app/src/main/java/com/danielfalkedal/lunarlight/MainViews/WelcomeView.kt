package com.danielfalkedal.lunarlight.MainViews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielfalkedal.lunarlight.AppIndex
import com.danielfalkedal.lunarlight.AppIndexManager

@Composable
fun WelcomeView() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Column(
            modifier = Modifier.weight(3.0f)
        ) {
            Text(text = "Welcome, ${AppIndexManager.currentUser.username}!")
        }

        Column(
            modifier = Modifier.weight(0.3f)
        ) {
            Button(onClick = {
                AppIndexManager.setIndex(AppIndex.lobbyTabView)
            }) {
                Text("Enter the world of Lunar Light")
            }
        }



    }

}