package com.danielfalkedal.lunarlight

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.danielfalkedal.lunarlight.MainViews.*

@Composable
fun ContentView() {

    when (AppIndexManager.appIndex.collectAsState().value) {

        AppIndex.startView -> StartView()

        AppIndex.lobbyTabView -> LobbyTabView()

        AppIndex.onlineUsersView -> OnlineUsersView()

        AppIndex.privateChatView -> PrivateChatView()

        AppIndex.welcomeView -> WelcomeView()

        AppIndex.splashView -> SplashView()

    }

}