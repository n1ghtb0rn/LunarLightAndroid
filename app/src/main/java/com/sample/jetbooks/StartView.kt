package com.sample.jetbooks

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun StartView() {

    Column {
        Text("Start View")

        LoginView()
    }

}