package com.danielfalkedal.lunarlight.MainViews

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danielfalkedal.lunarlight.AppIndex
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Firebase.Repos.UserDao
import com.danielfalkedal.lunarlight.Firebase.Repos.Models.User
import com.danielfalkedal.lunarlight.Firebase.Factories.UserViewModelFactory
import com.danielfalkedal.lunarlight.Responses.OnErrorUsers
import com.danielfalkedal.lunarlight.Responses.OnSuccessUsers
import com.danielfalkedal.lunarlight.Firebase.ViewModels.ONLINE_USERS
import com.danielfalkedal.lunarlight.Firebase.ViewModels.UsersViewModel
import com.danielfalkedal.lunarlight.ui.theme.getColorByUser
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun OnlineUsersView(
    usersViewModel: UsersViewModel = viewModel(
        factory = UserViewModelFactory(UserDao(), ONLINE_USERS)
    )
) {

    Log.d("DanneX", "Users online count = ${AppIndexManager.userOnlineModel.usersOnlineIds.size}")

    val showProfileViewSheet = remember { mutableStateOf(false) }
    val density = LocalDensity.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Box() {

            Column() {

                Text(
                    text = "Online users",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(16.dp)
                )

                Row() {

                    Button(onClick = {
                        AppIndexManager.setIndex(AppIndex.lobbyTabView)
                    }, Modifier.weight(0.2f)) {
                        Text("Back")
                    }

                    Spacer(Modifier.weight(0.8f))
                }

                when (val usersList = usersViewModel.usersStateFlow.asStateFlow().collectAsState().value) {

                    is OnErrorUsers -> {
                        Text(text = "Please try after sometime")
                    }

                    is OnSuccessUsers -> {

                        val listOfUsers = usersList.querySnapshot?.toObjects(User::class.java)
                        listOfUsers?.let {

                            LazyColumn(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.LightGray)
                                    .weight(3f)
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                                //modifier = Modifier
                                //    .fillMaxHeight()
                            ) {
                                items(listOfUsers) {
                                    if (it.id != AppIndexManager.loggedInUser.id &&
                                        AppIndexManager.userOnlineModel.usersOnlineIds.contains(it.id)) {

                                        Button(onClick = {
                                            AppIndexManager.profileUser = it
                                            showProfileViewSheet.value = true
                                        }, colors = ButtonDefaults.buttonColors(
                                            backgroundColor = getColorByUser(it)
                                        )) {
                                            Text(it.username)
                                        }
                                    }

                                }
                            }

                        }

                    }
                    else -> {
                        Text(text = "Please try after sometime")
                    }
                }

            }

            Column() {
                AnimatedVisibility(
                    visible = showProfileViewSheet.value,
                    enter = slideInVertically {
                        // Slide in from 40 dp from the top.
                        with(density) { 500.dp.roundToPx() }
                    },
                    exit = slideOutVertically {
                        with(density) { 500.dp.roundToPx() }
                    }

                ) {
                    Column(
                        Modifier.background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            showProfileViewSheet.value = false
                        }) {
                            Text("Close")
                        }

                        ProfileView(AppIndexManager.profileUser!!)

                    }
                }
            }
        }

    }


}