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
import androidx.compose.ui.graphics.Brush
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
import com.danielfalkedal.lunarlight.ui.theme.BlackTransparent
import com.danielfalkedal.lunarlight.ui.theme.getUserBackgroundColor
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

    val currentUser = AppIndexManager.loggedInUser
    val friend = AppIndexManager.privateChatUser

    val inputMessage = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        getUserBackgroundColor(friend.month, friend.day),
                        getUserBackgroundColor(currentUser.month, currentUser.day)
                    )
                )
            )
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = friend.username,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )

            Row() {
                Button(onClick = {
                    AppIndexManager.setIndex(AppIndex.lobbyTabView)
                }, Modifier.weight(0.3f)) {
                    Text("Back")
                }

                Spacer(Modifier.weight(0.7f))
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
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(BlackTransparent)
                            .weight(3f)
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                        //modifier = Modifier
                        //    .fillMaxHeight()
                    ) {
                        items(listOfPrivateMessages) {

                            if (it.sender_id == currentUser.id) {
                                MessageView(currentUser.username, it.my_message, it.timestamp, currentUser.avatar, currentUser.month, currentUser.day, true)
                            }
                            else {
                                MessageView(friend.username, it.my_message, it.timestamp, friend.avatar, friend.month, friend.day, true)
                            }

                        }
                    }

                }

            }
            else -> {
                Text(text = "Please try after sometime")
            }

        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .padding(8.dp)
                .weight(0.5f)
                .clip(RoundedCornerShape(12.dp))
                .background(BlackTransparent),
            verticalAlignment = Alignment.CenterVertically

            ) {

            Column(Modifier.weight(0.8f)) {
                TextField(
                    value = inputMessage.value,
                    onValueChange = {
                        inputMessage.value = it
                    },
                )
            }

            Column(Modifier.weight(0.2f)) {
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
}