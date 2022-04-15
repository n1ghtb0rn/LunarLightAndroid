package com.danielfalkedal.lunarlight.MainViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Firebase.Repos.Models.Friend
import com.danielfalkedal.lunarlight.Firebase.Repos.Models.User
import com.danielfalkedal.lunarlight.Firebase.Repos.UserDao
import com.danielfalkedal.lunarlight.Realm.Repos.RealmUserDao
import com.danielfalkedal.lunarlight.Utils.LocalData
import com.danielfalkedal.lunarlight.ui.theme.getUserBackgroundColor


@Composable
fun ProfileView(user: User) {

    val stoneIndex = User.getStoneIndex(user.month.toInt(), user.day.toInt())
    val stoneName = LocalData.stoneArray[stoneIndex]

    val infoIndex = remember { mutableStateOf(0) }

    val stoneInfo = "A great stone!"
    val profileInfo = remember { mutableStateOf(user.profile_info)}

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
            .fillMaxSize(),
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
            Button(onClick = {
                infoIndex.value = 0
            }) {
                Text("About me")
            }

            Button(onClick = {
                infoIndex.value = 1
            }) {
                Text(stoneName)
            }
        }

        if (infoIndex.value == 0) {
            if (user.id == AppIndexManager.loggedInUser.id) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = profileInfo.value,
                        onValueChange = {
                            profileInfo.value = it
                        },
                    )
                    Button(onClick = {
                        user.profile_info = profileInfo.value

                        AppIndexManager.loggedInUser.profile_info = profileInfo.value
                        UserDao().createOrUpdateUser(AppIndexManager.loggedInUser)

                        AppIndexManager.userRealm.profile_info = profileInfo.value
                        RealmUserDao().updateUser(AppIndexManager.userRealm)
                    }) {
                        Text("Save")
                    }
                }
            }
            else {
                Text(profileInfo.value)
            }
        }
        else {
            Text(stoneInfo)
        }


    }

}