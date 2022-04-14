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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danielfalkedal.lunarlight.AppIndex
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Collections.PrivateMessageModel
import com.danielfalkedal.lunarlight.Documents.PrivateMessage
import com.danielfalkedal.lunarlight.Factories.PrivateMessageViewModelFactory
import com.danielfalkedal.lunarlight.Responses.OnErrorPrivateMsgs
import com.danielfalkedal.lunarlight.Responses.OnSuccessPrivateMsgs
import com.danielfalkedal.lunarlight.SubViews.MessageView
import com.danielfalkedal.lunarlight.ViewModels.PrivateMessagesViewModel
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

@Composable
fun PrivateChatView(
    privateMessagesViewModel: PrivateMessagesViewModel = viewModel(
        factory = PrivateMessageViewModelFactory(PrivateMessageModel())
    )
) {

    //val privateMessages: MutableList<PrivateMessage>? = SharedPrivateMessagesViewModel.privateMessages

    //Log.d("DanneB", "privateMessages = ${privateMessages?.size}")

    val currentUser = AppIndexManager.currentUser
    val friend = AppIndexManager.privateChatUser

    val inputMessage = remember { mutableStateOf(TextFieldValue()) }

    Column() {

        Column(
            modifier = Modifier.weight(0.6f).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = friend.username,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )

            Button(onClick = {
                AppIndexManager.setIndex(AppIndex.lobbyTabView)
            }) {
                Text("Back")
            }

        }

        when (val privateMessagesList = privateMessagesViewModel.privateMessagesStateFlow.asStateFlow().collectAsState().value) {

            is OnErrorPrivateMsgs -> {
                Text(text = "Please try after sometime")
            }

            is OnSuccessPrivateMsgs -> {

                val listOfPrivateMessages = privateMessagesList.privateMessages
                listOfPrivateMessages?.let {

                    LazyColumn(
                        modifier = Modifier.clip(RoundedCornerShape(12.dp))
                            .background(Color.LightGray)
                            .weight(3f)
                            .padding(vertical = 8.dp)
                        //modifier = Modifier
                        //    .fillMaxHeight()
                    ) {
                        items(listOfPrivateMessages) {


                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                if (it.sender_id == currentUser.id) {
                                    MessageView(currentUser.username, it.my_message, it.timestamp, currentUser.month, currentUser.day)
                                }
                                else {
                                    MessageView(friend.username, it.my_message, it.timestamp, friend.month, friend.day)
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

                val id = UUID.randomUUID().toString()
                val senderId = currentUser.id
                val receiverId = friend.id
                val message = inputMessage.value.text
                val timestamp: Long = System.currentTimeMillis().toLong()

                val newPrivateMessage = PrivateMessage(id, senderId, receiverId, message, timestamp)
                PrivateMessageModel().createPrivateMessage(newPrivateMessage)

                inputMessage.value = TextFieldValue("")
            }) {
                Text("Send")
            }
        }

    }
}