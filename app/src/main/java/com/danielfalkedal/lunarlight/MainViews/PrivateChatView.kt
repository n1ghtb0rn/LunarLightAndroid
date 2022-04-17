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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danielfalkedal.lunarlight.AppIndex
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Firebase.Repos.PrivateMessageDao
import com.danielfalkedal.lunarlight.Firebase.Repos.Models.PrivateMessage
import com.danielfalkedal.lunarlight.Firebase.Factories.PrivateMessageViewModelFactory
import com.danielfalkedal.lunarlight.Firebase.Repos.Models.User
import com.danielfalkedal.lunarlight.Firebase.Repos.Models.WorldMessage
import com.danielfalkedal.lunarlight.Responses.OnErrorPrivateMsgs
import com.danielfalkedal.lunarlight.Responses.OnSuccessPrivateMsgs
import com.danielfalkedal.lunarlight.SubViews.MessageView
import com.danielfalkedal.lunarlight.Firebase.ViewModels.PrivateMessagesViewModel
import com.danielfalkedal.lunarlight.ui.theme.BlackTransparent
import com.danielfalkedal.lunarlight.ui.theme.getUserBackgroundColor
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

@Composable
fun PrivateChatView(
    privateMessagesViewModel: PrivateMessagesViewModel = viewModel(
        factory = PrivateMessageViewModelFactory(PrivateMessageDao())
    ))
{

    val currentUser = AppIndexManager.loggedInUser
    val friend = AppIndexManager.privateChatUser

    val inputMessage = remember { mutableStateOf(TextFieldValue()) }

    val showProfileViewSheet = remember { mutableStateOf(false) }
    val density = LocalDensity.current

    Box(Modifier.fillMaxSize()) {

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

                Button(onClick = {
                    showProfileViewSheet.value = true
                }) {
                    Text(
                        text = friend.username,
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    )
                }



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

                    val listOfPrivateMessages = privateMessagesList.querySnapshot?.toObjects(PrivateMessage::class.java)
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

                                if (it.sender_id == currentUser.id && it.receiver_id == friend.id) {
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        MessageView(currentUser.username, it.my_message, it.timestamp, currentUser.avatar, currentUser.month, currentUser.day, true)
                                    }
                                }
                                else if (it.sender_id == friend.id && it.receiver_id == currentUser.id) {
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        MessageView(friend.username, it.my_message, it.timestamp, friend.avatar, friend.month, friend.day, true)
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
                        PrivateMessageDao().createPrivateMessage(newPrivateMessage)

                        inputMessage.value = TextFieldValue("")
                    }) {
                        Text("Send")
                    }
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

                    AppIndexManager.profileUser = AppIndexManager.privateChatUser
                    ProfileView(AppIndexManager.profileUser!!)

                }
            }

        }
    }


}