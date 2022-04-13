package com.danielfalkedal.lunarlight.MainViews

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danielfalkedal.lunarlight.AppIndex
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Collections.UserModel
import com.danielfalkedal.lunarlight.Documents.Friend
import com.danielfalkedal.lunarlight.Documents.User
import com.danielfalkedal.lunarlight.Factories.UserViewModelFactory
import com.danielfalkedal.lunarlight.Responses.OnErrorUsers
import com.danielfalkedal.lunarlight.Responses.OnSuccessUsers
import com.danielfalkedal.lunarlight.ViewModels.USER_FRIENDS
import com.danielfalkedal.lunarlight.ViewModels.UsersViewModel
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun FriendsView(
    usersViewModel: UsersViewModel = viewModel(
        factory = UserViewModelFactory(UserModel(), USER_FRIENDS)
    )
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Friends")

        when (val userFriendsList = usersViewModel.usersStateFlow.asStateFlow().collectAsState().value) {

            is OnErrorUsers -> {
                Text(text = "Please try after sometime")
            }

            is OnSuccessUsers -> {

                val listOfUserFriends = userFriendsList.querySnapshot?.toObjects(User::class.java)

                listOfUserFriends?.let {

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
                        items(listOfUserFriends) {

                            val friends = AppIndexManager.friendModel.friendsIds

                            Log.d("DanneX", "Local val friends count = ${friends.size}")

                            if (it.id != AppIndexManager.currentUser.id &&
                                    it.id in AppIndexManager.friendModel.friendsIds) {

                                Button(onClick = {
                                    AppIndexManager.privateChatUser = it
                                    AppIndexManager.setIndex(AppIndex.privateChatView)
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