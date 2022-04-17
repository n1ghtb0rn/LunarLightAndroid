package com.danielfalkedal.lunarlight

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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