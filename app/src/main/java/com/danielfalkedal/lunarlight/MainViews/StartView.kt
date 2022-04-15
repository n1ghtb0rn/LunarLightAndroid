package com.danielfalkedal.lunarlight

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.Button
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.danielfalkedal.lunarlight.Documents.User
import com.danielfalkedal.lunarlight.ui.theme.FullTransparent
import com.danielfalkedal.lunarlight.ui.theme.Purple200
import com.danielfalkedal.lunarlight.ui.theme.Purple700
import com.danielfalkedal.lunarlight.ui.theme.getUserBackgroundColor


@Composable
fun StartView() {

    var showLoginView = remember { mutableStateOf(true)}

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

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
            modifier = Modifier
                .matchParentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Button(onClick = {
                    showLoginView.value = true
                }) {
                    Text("Login")
                }

                Text(" | ")

                Button(onClick = {
                    showLoginView.value = false
                }) {
                    Text("Register")
                }
            }

            when (showLoginView.value) {

                true -> LoginView()

                false -> RegisterView()

            }

        }
    }



}