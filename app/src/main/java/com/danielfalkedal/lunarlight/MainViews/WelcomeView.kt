package com.danielfalkedal.lunarlight.MainViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.danielfalkedal.lunarlight.AppIndex
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Collections.FriendModel
import com.danielfalkedal.lunarlight.Collections.UserModel
import com.danielfalkedal.lunarlight.Documents.User
import com.danielfalkedal.lunarlight.ui.theme.BlackTransparent
import com.danielfalkedal.lunarlight.ui.theme.WhiteTransparent
import com.danielfalkedal.lunarlight.ui.theme.getUserBackgroundColor

@Composable
fun WelcomeView() {

    val stoneImages: List<String> = User.getStoneImages(AppIndexManager.loggedInUser).toList()

    var selectedImage = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    Color.White,
                    getUserBackgroundColor(AppIndexManager.loggedInUser.month, AppIndexManager.loggedInUser.day)
                )
            ))
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Column(
            modifier = Modifier.weight(3.0f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome, ${AppIndexManager.loggedInUser.username}!")

            Text(text = "Choose your avatar")

            LazyVerticalGrid(
                GridCells.Fixed(4),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(stoneImages) { imageString ->
                    if (selectedImage.value.isEmpty()) {
                        selectedImage.value = imageString
                    }
                    Button(onClick = {
                        selectedImage.value = imageString
                        AppIndexManager.loggedInUser.avatar = imageString
                        val userModel = UserModel()
                        userModel.createOrUpdateUser(AppIndexManager.loggedInUser)
                    }, Modifier
                        .padding(8.dp)
                        .size(75.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (selectedImage.value == imageString) BlackTransparent else WhiteTransparent
                    )) {
                        Image(
                            painter = painterResource(User.getAvatarResource(imageString)),
                            contentDescription = "Profile avatar"
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier.weight(0.3f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {

                //TODO: REMOVE THE FOLLOWING THREE LINES (FOR TESTING ONLY)
                AppIndexManager.friendModel = FriendModel()
                AppIndexManager.friendModel.listenToUserFriends()

                AppIndexManager.setIndex(AppIndex.lobbyTabView)
            }) {
                Text("Enter the world of Lunar Light")
            }
        }



    }

}