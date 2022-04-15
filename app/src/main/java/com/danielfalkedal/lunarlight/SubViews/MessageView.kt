package com.danielfalkedal.lunarlight.SubViews

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danielfalkedal.lunarlight.Collections.Documents.User
import com.danielfalkedal.lunarlight.Utils.TimestampConverter
import com.danielfalkedal.lunarlight.ui.theme.getUserBackgroundColor

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MessageView(username: String, message: String, timestamp: Long, avatar: String, month: Long, day: Long, isPrivate: Boolean) {
    var showMessageDescription by remember { mutableStateOf(false) }
    val worldMessageCoverImageSize by animateDpAsState(
        targetValue =
        if (showMessageDescription) 50.dp else 80.dp
    )

    val timestampConverter = TimestampConverter()

    Card(
        modifier = Modifier
            .padding(16.dp)
            .widthIn(0.dp, 400.dp),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(modifier = Modifier
            .background(getUserBackgroundColor(month, day))
            .clickable {
                showMessageDescription = showMessageDescription.not()
            }) {
            Row(modifier = Modifier.padding(12.dp)) {

                Column(
                    modifier = Modifier
                        .fillMaxSize(0.16f)
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(User.getAvatarResource(avatar)),
                        contentDescription = "Profile avatar"
                    )
                }

                Column {
                    if (!isPrivate) {
                        Text(
                            modifier = Modifier
                                .padding(0.dp, 0.dp, 16.dp),
                            text = "$username:", style = TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(0.dp, 8.dp, 16.dp, 8.dp),
                        text = message, style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    )
                }
            }

            AnimatedVisibility(visible = showMessageDescription) {
                Text(
                    text = timestampConverter.getDateTime(timestamp.toString())!!, style = TextStyle(
                        fontWeight = FontWeight.Light,
                        fontStyle = FontStyle.Italic,
                        fontSize = 12.sp
                    ),
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
                )
            }
        }

    }

}