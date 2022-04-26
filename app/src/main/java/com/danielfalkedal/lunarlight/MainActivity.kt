package com.danielfalkedal.lunarlight

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.danielfalkedal.lunarlight.ui.theme.LunarLightTheme
import com.google.rpc.context.AttributeContext
import io.realm.Realm
import io.realm.RealmConfiguration

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initContentReferences(this)

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

    companion object {

        lateinit var resources: Resources
        lateinit var packageName: String

        fun initContentReferences(context: MainActivity) {
            resources = context.resources
            packageName = context.packageName
        }
    }
}




