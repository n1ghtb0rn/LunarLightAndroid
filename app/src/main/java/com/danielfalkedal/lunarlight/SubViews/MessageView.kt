package com.danielfalkedal.lunarlight.SubViews

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danielfalkedal.lunarlight.Documents.User
import com.danielfalkedal.lunarlight.Documents.WorldMessage
import com.danielfalkedal.lunarlight.Utils.LocalData
import com.danielfalkedal.lunarlight.Utils.TimestampConverter
import com.danielfalkedal.lunarlight.ui.theme.getColorByString

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MessageView(username: String, message: String, timestamp: Long, month: Long, day: Long) {
    var showMessageDescription by remember { mutableStateOf(false) }
    val worldMessageCoverImageSize by animateDpAsState(
        targetValue =
        if (showMessageDescription) 50.dp else 80.dp
    )

    val timestampConverter = TimestampConverter()

    val backgroundColor: Color = MessageExtension().getUserBackgroundColor(month, day)

    Column(modifier = Modifier.background(MessageExtension().getUserBackgroundColor(month, day)).clickable {
        showMessageDescription = showMessageDescription.not()
    }) {
        Row(modifier = Modifier.padding(12.dp)) {


            Column {
                Text(
                    text = username, style = TextStyle(
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp
                    )
                )

                Text(
                    text = message, style = TextStyle(
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp
                    )
                )
            }
        }

        AnimatedVisibility(visible = showMessageDescription) {
            Text(
                text = timestampConverter.getDateTime(timestamp.toString())!!, style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic
                ),
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
            )
        }
    }

}

class MessageExtension {

    fun getUserBackgroundColor(month: Long, day: Long): Color {
        val colorIndex = User.getStoneIndex(month.toInt(), day.toInt())
        val colorName = LocalData.profileBackground[colorIndex]
        val color = getColorByString(colorName)
        return color

    }

}