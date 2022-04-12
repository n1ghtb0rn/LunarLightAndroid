package com.danielfalkedal.lunarlight.MainViews

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.danielfalkedal.lunarlight.AppIndexManager

@Composable
fun FriendsView() {

    Log.d("Danne", "Friends count = ${AppIndexManager.friendModel.friends.size}")

    Column() {
        Text("Friends")
    }


}