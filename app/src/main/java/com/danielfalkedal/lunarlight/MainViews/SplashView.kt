package com.danielfalkedal.lunarlight.MainViews

import android.os.Handler
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.danielfalkedal.lunarlight.AppIndex
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Firebase.Repos.Models.User
import com.danielfalkedal.lunarlight.R
import kotlinx.coroutines.delay
import java.util.*
import kotlin.concurrent.timerTask

@Composable
fun SplashView(

) {

    val splashName = "llsplashscreen_"

    val timerRunning = remember { mutableStateOf(true)}

    val currentFrame = remember { mutableStateOf(0) }


    LaunchedEffect(key1 = currentFrame.value, key2 = timerRunning.value) {
        if(currentFrame.value < 59 && timerRunning.value) {
            delay(25L)
            currentFrame.value += 1
            if (currentFrame.value >= 59 && timerRunning.value) {
                timerRunning.value = false
                AppIndexManager.setIndex(AppIndex.startView)
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black),
        contentAlignment = Alignment.Center
    ){
        Column(){
            Image(
                painter = painterResource(User.getAvatarResource(splashName+currentFrame.value)),
                contentDescription = "Contact profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp)
            )
        }
    }

}