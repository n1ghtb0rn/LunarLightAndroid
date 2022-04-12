package com.danielfalkedal.lunarlight.MainViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danielfalkedal.lunarlight.AppIndex
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Collections.UserModel
import com.danielfalkedal.lunarlight.Documents.User
import com.danielfalkedal.lunarlight.Factories.UserViewModelFactory
import com.danielfalkedal.lunarlight.Responses.OnErrorUsers
import com.danielfalkedal.lunarlight.Responses.OnSuccessUsers
import com.danielfalkedal.lunarlight.ViewModels.ONLINE_USERS
import com.danielfalkedal.lunarlight.ViewModels.UsersViewModel
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun OnlineUsersView(
    usersViewModel: UsersViewModel = viewModel(
        factory = UserViewModelFactory(UserModel(), ONLINE_USERS)
    )
) {

    val showProfileViewSheet = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (showProfileViewSheet.value) {

            true -> {
                Column() {
                    Row {
                        Button(onClick = {
                            showProfileViewSheet.value = false
                        }) {
                            Text("Close")
                        }
                    }

                    ProfileView(AppIndexManager.profileUser!!)

                }
            }

            false -> {

                Button(onClick = {
                    AppIndexManager.setIndex(AppIndex.lobbyTabView)
                }) {
                    Text("Back")
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

                                    if (it.id != AppIndexManager.currentUser.id) {
                                        Button(onClick = {
                                            AppIndexManager.profileUser = it
                                            showProfileViewSheet.value = true
                                        }) {
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
        }


    }


}