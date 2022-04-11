package com.danielfalkedal.lunarlight

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.danielfalkedal.lunarlight.MainViews.LobbyTabView
import com.danielfalkedal.lunarlight.MainViews.WorldMessageView

@Composable
fun ContentView() {

    when (AppIndexManager.appIndex.collectAsState().value) {

        AppIndex.startView -> StartView()

        AppIndex.lobbyTabView -> LobbyTabView()

    }

}