package com.danielfalkedal.lunarlight.MainViews

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.danielfalkedal.lunarlight.AppIndex
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Documents.User

@Composable
fun WelcomeView() {

    val stoneImages: List<String> = User.getStoneImages(AppIndexManager.currentUser).toList()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Column(
            modifier = Modifier.weight(3.0f)
        ) {
            Text(text = "Welcome, ${AppIndexManager.currentUser.username}!")

            Text(text = "Choose your avatar")

            LazyVerticalGrid(
                GridCells.Fixed(4),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(stoneImages) { imageString ->
                    Button(onClick = {
                        Log.d("DanneAndroid", "Clicked on image $imageString")
                    }) {
                        Image(
                            painter = painterResource(User.getAvatarResource(imageString)),
                            contentDescription = "Profile avatar"
                        )
                    }
                }
            }
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