package com.sample.jetbooks.MainViews

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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.jetbooks.AppIndex
import com.sample.jetbooks.AppIndexManager
import com.sample.jetbooks.Collections.UserOnlineModel
import com.sample.jetbooks.Documents.WorldMessage
import com.sample.jetbooks.Collections.WorldMessageModel
import com.sample.jetbooks.Documents.UserOnline
import com.sample.jetbooks.Factories.WorldMessageViewModelFactory
import com.sample.jetbooks.Responses.OnError
import com.sample.jetbooks.Responses.OnSuccess
import com.sample.jetbooks.Utils.TimestampConverter
import com.sample.jetbooks.ViewModels.WorldMessagesViewModel
import kotlinx.coroutines.flow.asStateFlow
import java.lang.IllegalStateException
import java.util.*

@Composable
fun WorldMessageView(
    worldMessagesViewModel: WorldMessagesViewModel = viewModel(
        factory = WorldMessageViewModelFactory(WorldMessageModel())
    )
) {

    val inputMessage = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier.padding(horizontal = 8.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier.weight(0.6f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "World Chat",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )

            Button(onClick = {

                val currentUser = AppIndexManager.currentUser
                val userOnline = UserOnline(currentUser.id, false, currentUser.username)
                val userOnlineModel = UserOnlineModel()
                userOnlineModel.updateUserOnline(userOnline)

                AppIndexManager.setIndex(AppIndex.startView)
            }) {
                Text("Logout")
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
        }

    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WorldMessageDetails(worldMessage: WorldMessage) {
    var showWorldMessageDescription by remember { mutableStateOf(false) }
    val worldMessageCoverImageSize by animateDpAsState(
        targetValue =
        if (showWorldMessageDescription) 50.dp else 80.dp
    )

    val timestampConverter = TimestampConverter()

    Column(modifier = Modifier.clickable {
        showWorldMessageDescription = showWorldMessageDescription.not()
    }) {
        Row(modifier = Modifier.padding(12.dp)) {


            Column {
                Text(
                    text = worldMessage.username, style = TextStyle(
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp
                    )
                )

                Text(
                    text = worldMessage.message, style = TextStyle(
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp
                    )
                )
            }
        }

        AnimatedVisibility(visible = showWorldMessageDescription) {
            Text(
                text = timestampConverter.getDateTime(worldMessage.timestamp.toString())!!, style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic
                ),
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
            )
        }
    }

}