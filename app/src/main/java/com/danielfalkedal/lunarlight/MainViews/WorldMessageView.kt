package com.danielfalkedal.lunarlight.MainViews

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danielfalkedal.lunarlight.AppIndex
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Collections.UserOnlineModel
import com.danielfalkedal.lunarlight.Documents.WorldMessage
import com.danielfalkedal.lunarlight.Collections.WorldMessageModel
import com.danielfalkedal.lunarlight.Documents.UserOnline
import com.danielfalkedal.lunarlight.Factories.WorldMessageViewModelFactory
import com.danielfalkedal.lunarlight.Realm.RealmUserDao
import com.danielfalkedal.lunarlight.Responses.OnError
import com.danielfalkedal.lunarlight.Responses.OnSuccess
import com.danielfalkedal.lunarlight.SubViews.WorldMessageDetails
import com.danielfalkedal.lunarlight.Utils.TimestampConverter
import com.danielfalkedal.lunarlight.ViewModels.WorldMessagesViewModel
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

@Composable
fun WorldMessageView(
    worldMessagesViewModel: WorldMessagesViewModel = viewModel(
        factory = WorldMessageViewModelFactory(WorldMessageModel())
    )
) {

    val inputMessage = remember { mutableStateOf(TextFieldValue()) }

    Column() {

        Column(
            modifier = Modifier.weight(0.6f).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "World Chat",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )

            Row() {

                Button(onClick = {
                    AppIndexManager.setIndex(AppIndex.onlineUsersView)
                }) {
                    Text("Online Users")
                }

                Spacer(modifier = Modifier.width(100.dp))

                Button(onClick = {

                    AppIndexManager.realmUserDao.deleteAllUsers()
                    val users = AppIndexManager.realmUserDao.getUsers()
                    Log.d("Danne", "realm users count = ${users.size}")

                    val currentUser = AppIndexManager.currentUser
                    val userOnline = UserOnline(currentUser.id, false, currentUser.username)
                    val userOnlineModel = UserOnlineModel()
                    userOnlineModel.updateUserOnline(userOnline)

                    AppIndexManager.setIndex(AppIndex.startView)
                }) {
                    Text("Logout")
                }
            }

        }



        when (val worldMessagesList = worldMessagesViewModel.worldMessagesStateFlow.asStateFlow().collectAsState().value) {

            is OnError -> {
                Text(text = "Please try after sometime")
            }

            is OnSuccess -> {

                val listOfWorldMessages = worldMessagesList.querySnapshot?.toObjects(WorldMessage::class.java)
                listOfWorldMessages?.let {

                    LazyColumn(
                        modifier = Modifier.clip(RoundedCornerShape(12.dp))
                            .background(Color.LightGray)
                            .weight(3f)
                            .padding(vertical = 8.dp)
                        //modifier = Modifier
                        //    .fillMaxHeight()
                    ) {
                        items(listOfWorldMessages) {

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                WorldMessageDetails(it)
                            }
                        }
                    }

                }

                Row(
                    modifier = Modifier.weight(0.5f),
                    verticalAlignment = Alignment.CenterVertically,

                ) {
                    TextField(
                        value = inputMessage.value,
                        onValueChange = {
                            inputMessage.value = it
                        }
                    )

                    Button(onClick = {

                        val currentUser = AppIndexManager.currentUser

                        val id = UUID.randomUUID().toString()
                        val userId = currentUser.id
                        val username = currentUser.username
                        val timestamp: Long = System.currentTimeMillis()
                        val avatar = currentUser.avatar
                        val month: Long = currentUser.month
                        val day: Long = currentUser.day
                        val message = inputMessage.value.text


                        val newWorldMessage = WorldMessage(id, userId, username, timestamp, avatar,month,day, message)
                        val worldMessagesRepo = WorldMessageModel()
                        worldMessagesRepo.createWorldMessage(newWorldMessage)
                    }) {
                        Text("Send")
                    }
                }

            }
            else -> {
                Text(text = "Please try after sometime")
            }
        }

    }
}