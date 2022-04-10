package com.sample.jetbooks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.sample.jetbooks.MainViews.WorldMessageView

@Composable
fun ContentView() {

    when (AppIndexManager.appIndex.collectAsState().value) {

        AppIndex.startView -> StartView()

        AppIndex.worldMessageView -> WorldMessageView()

    }

}