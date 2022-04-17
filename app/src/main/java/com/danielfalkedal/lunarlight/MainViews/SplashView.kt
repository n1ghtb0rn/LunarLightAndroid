package com.danielfalkedal.lunarlight.MainViews

import android.os.Handler
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import java.util.*
import kotlin.concurrent.timerTask

@Composable
fun SplashView(

) {

    val isComplete = remember { mutableStateOf(true)} //TODO: Set to false

    val progress = remember { mutableStateOf (0.0f) }
    val animatedProgress = animateFloatAsState(
        targetValue = progress.value,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    if (!isComplete.value) {
        Timer().schedule( timerTask{
            progress.value = 0.5f
        }, 1000)

        Timer().schedule( timerTask{
            progress.value = 1.0f
        }, 2000)

        Timer().schedule( timerTask{
            isComplete.value = true
            AppIndexManager.setIndex(AppIndex.startView)
        }, 2100)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black),
        contentAlignment = Alignment.Center
    ){
        Column(){
            Image(
                painter = painterResource(R.drawable.lunarlight_splashscreen),
                contentDescription = "Contact profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(150.dp)
            )

            Row(modifier = Modifier
                .width(150.dp)
                .background(Color.Blue)) {
                LinearProgressIndicator(progress = animatedProgress)
            }
        }
    }

}