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
import com.danielfalkedal.lunarlight.Firebase.ViewModels.USER_FRIENDS
import com.danielfalkedal.lunarlight.Firebase.ViewModels.UsersViewModel
import com.danielfalkedal.lunarlight.ui.theme.getColorByUser
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun FriendsView(
    usersViewModel: UsersViewModel = viewModel(
        factory = UserViewModelFactory(UserDao(), USER_FRIENDS)
    )
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Friends",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
        )

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

                            val friends = AppIndexManager.friendDao.friendsIds

                            Log.d("DanneX", "Local val friends count = ${friends.size}")

                            if (it.id != AppIndexManager.loggedInUser.id &&
                                    it.id in AppIndexManager.friendDao.friendsIds) {

                                Button(onClick = {
                                    AppIndexManager.privateChatUser = it
                                    AppIndexManager.setIndex(AppIndex.privateChatView)
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


}