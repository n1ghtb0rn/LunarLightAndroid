package com.sample.jetbooks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.jetbooks.data.WorldMessage
import com.sample.jetbooks.repo.WorldMessagesRepo
import com.sample.jetbooks.response.OnError
import com.sample.jetbooks.response.OnSuccess
import com.sample.jetbooks.utils.TimestampConverter
import com.sample.jetbooks.viewmodel.WorldMessagesViewModel
import kotlinx.coroutines.flow.asStateFlow
import java.lang.IllegalStateException
import java.sql.Timestamp
import java.util.*

@Composable
fun WorldMessageView(
    worldMessagesViewModel: WorldMessagesViewModel = viewModel(
        factory = WorldMessageViewModelFactory(WorldMessagesRepo())
    )
) {

    val inputMessage = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Lunar Light",
            style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.ExtraBold),
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "World Chat",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
        )

        Button(onClick = {
            AppIndexManager.setIndex(AppIndex.startView)
        }) {
            Text("Logout")
        }

        when (val worldMessagesList = worldMessagesViewModel.worldMessagesStateFlow.asStateFlow().collectAsState().value) {

            is OnError -> {
                Text(text = "Please try after sometime")
            }

            is OnSuccess -> {

                Row {
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
                        val worldMessagesRepo = WorldMessagesRepo()
                        worldMessagesRepo.addWorldMessage(newWorldMessage)
                    }) {
                        Text("Send")
                    }
                }

                val listOfWorldMessages = worldMessagesList.querySnapshot?.toObjects(WorldMessage::class.java)
                listOfWorldMessages?.let {
                    Column {
                        LazyColumn(modifier = Modifier.fillMaxHeight()) {
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

class WorldMessageViewModelFactory(private val worldMessagesRepo: WorldMessagesRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorldMessagesViewModel::class.java)) {
            return WorldMessagesViewModel(worldMessagesRepo) as T
        }

        throw IllegalStateException()
    }

}