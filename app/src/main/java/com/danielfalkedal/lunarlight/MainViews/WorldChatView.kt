package com.danielfalkedal.lunarlight.MainViews

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
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
import com.danielfalkedal.lunarlight.Responses.OnErrorWorldMsgs
import com.danielfalkedal.lunarlight.Responses.OnSuccessWorldMsgs
import com.danielfalkedal.lunarlight.SubViews.MessageView
import com.danielfalkedal.lunarlight.ViewModels.WorldMessagesViewModel
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

@Composable
fun WorldChatView(
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

            is OnErrorWorldMsgs -> {
                Text(text = "Please try after sometime")
            }

            is OnSuccessWorldMsgs -> {

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
                                MessageView(it.username, it.message, it.timestamp, it.month, it.day)
                            }


                        }
                    }

                }

            }
            else -> {
                Text(text = "Please try after sometime")
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
                val timestamp: Long = System.currentTimeMillis().toLong()
                val avatar = currentUser.avatar
                val month: Long = currentUser.month
                val day: Long = currentUser.day
                val message = inputMessage.value.text


                val newWorldMessage = WorldMessage(id, userId, username, timestamp, avatar,month,day, message)
                val worldMessagesRepo = WorldMessageModel()
                worldMessagesRepo.createWorldMessage(newWorldMessage)

                inputMessage.value = TextFieldValue("")
            }) {
                Text("Send")
            }
        }

    }
}