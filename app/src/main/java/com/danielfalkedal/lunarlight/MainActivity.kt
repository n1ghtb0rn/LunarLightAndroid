package com.danielfalkedal.lunarlight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import com.danielfalkedal.lunarlight.ui.theme.LunarLightTheme
import io.realm.Realm
import io.realm.RealmConfiguration

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppIndexManager.initRealm(this)

        setContent {
            LunarLightTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ContentView()
                }
            }
        }
    }


}




