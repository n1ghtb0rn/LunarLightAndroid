package com.sample.jetbooks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun ContentView() {

    Column {

        Row {
            Button(onClick = {
                AppIndexManager.setIndex(AppIndex.startView)
            }) {
                Text("Start View")
            }

            Button(onClick = {
                AppIndexManager.setIndex(AppIndex.worldMessageView)
            }) {
                Text("World Message View")
            }
        }

        when (AppIndexManager.appIndex.collectAsState().value) {

            AppIndex.startView -> StartView()

            AppIndex.worldMessageView -> WorldMessageView()

        }



    }

}