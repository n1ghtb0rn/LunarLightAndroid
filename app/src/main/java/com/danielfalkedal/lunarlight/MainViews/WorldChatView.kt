package com.danielfalkedal.lunarlight.MainViews

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danielfalkedal.lunarlight.AppIndex
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Firebase.Repos.UserOnlineDao
import com.danielfalkedal.lunarlight.Firebase.Repos.Models.WorldMessage
import com.danielfalkedal.lunarlight.Firebase.Repos.WorldMessageDao
import com.danielfalkedal.lunarlight.Firebase.Repos.Models.UserOnline
import com.danielfalkedal.lunarlight.Firebase.Factories.WorldMessageViewModelFactory
import com.danielfalkedal.lunarlight.Responses.OnErrorWorldMsgs
import com.danielfalkedal.lunarlight.Responses.OnSuccessWorldMsgs
import com.danielfalkedal.lunarlight.SubViews.MessageView
import com.danielfalkedal.lunarlight.Firebase.ViewModels.WorldMessagesViewModel
import com.danielfalkedal.lunarlight.ui.theme.BlackTransparent
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

@Composable
fun WorldChatView(
    worldMessagesViewModel: WorldMessagesViewModel = viewModel(
        factory = WorldMessageViewModelFactory(WorldMessageDao())
    )
) {

    val inputMessage = remember { mutableStateOf(TextFieldValue()) }

    Column() {

        Column(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxSize(),
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

                    val currentUser = AppIndexManager.loggedInUser
                    val userOnline = UserOnline(currentUser.id, false, currentUser.username)
                    val userOnlineModel = UserOnlineDao()
                    userOnlineModel.updateUserOnline(userOnline)

                    AppIndexManager.setIndex(AppIndex.startView)
                }) {
                    Text("Logout")
                }
            }

        }



        when (val worldMessagesList = worldMessagesViewModel.worldMessagesStateFlow.asStateFlow().collectAsState().value) {

            is OnErrorWorldMsgs -> {
                Text(text = "Please try after sometime (OnErrorWorldMsgs)")
            }

            is OnSuccessWorldMsgs -> {

                val listOfWorldMessages = worldMessagesList.querySnapshot?.toObjects(WorldMessage::class.java)
                listOfWorldMessages?.let {

                    LazyColumn(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(BlackTransparent)
                            .weight(3f)
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                        //modifier = Modifier
                        //    .fillMaxHeight()
                    ) {
                        items(listOfWorldMessages) {

                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = if (it.user_id == AppIndexManager.loggedInUser.id)
                                    Arrangement.End else
                                    Arrangement.Start
                            ) {
                                MessageView(it.username, it.message, it.timestamp, it.avatar, it.month, it.day, false)
                            }

                        }
                    }

                }

            }
            else -> {
                Text(text = "PrivateChatView: Unknown response type")
            }

        }

        Row(
            modifier = Modifier
                .weight(0.5f)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {

            Column(Modifier.weight(0.8f)) {
                TextField(
                    value = inputMessage.value,
                    onValueChange = {
                        inputMessage.value = it
                    }
                )
            }

            Column(Modifier.weight(0.2f)) {
                Button(onClick = {

                    val currentUser = AppIndexManager.loggedInUser

                    val id = UUID.randomUUID().toString()
                    val userId = currentUser.id
                    val username = currentUser.username
                    val timestamp: Long = System.currentTimeMillis().toLong()
                    val avatar = currentUser.avatar
                    val month: Long = currentUser.month
                    val day: Long = currentUser.day
                    val message = inputMessage.value.text


                    val newWorldMessage = WorldMessage(id, userId, username, timestamp, avatar,month,day, message)
                    val worldMessagesRepo = WorldMessageDao()
                    worldMessagesRepo.createWorldMessage(newWorldMessage)

                    inputMessage.value = TextFieldValue("")
                }) {
                    Text("Send")
                }
            }
        }
    }
}