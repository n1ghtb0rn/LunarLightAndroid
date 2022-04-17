package com.danielfalkedal.lunarlight

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.danielfalkedal.lunarlight.Firebase.Repos.UserDao
import com.danielfalkedal.lunarlight.Firebase.Repos.Models.User
import com.danielfalkedal.lunarlight.Realm.Repos.RealmUserDao
import com.danielfalkedal.lunarlight.Realm.Repos.Models.UserRealm
import com.danielfalkedal.lunarlight.Utils.LocalData
import com.danielfalkedal.lunarlight.ui.theme.BlackTransparent
import com.danielfalkedal.lunarlight.ui.theme.WhiteTransparent
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

@Composable
fun RegisterView() {

    val username = remember { mutableStateOf("") }
    val year = remember { mutableStateOf("") }
    val month = remember { mutableStateOf("") }
    val day = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordSecured = remember { mutableStateOf(true) }

    Column(
        //modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



        Text("Username")
        TextField(
            value = username.value,
            onValueChange = {
                username.value = it
            },
            Modifier.background(WhiteTransparent)
        )

        Text("Year")
        TextField(
            value = year.value,
            onValueChange = {
                year.value = it
            },
            Modifier.background(WhiteTransparent)
        )

        Text("Month")
        TextField(
            value = month.value,
            onValueChange = {
                month.value = it
            },
            Modifier.background(WhiteTransparent)
        )

        Text("Day")
        TextField(
            value = day.value,
            onValueChange = {
                day.value = it
            },
            Modifier.background(WhiteTransparent)
        )

        Text("Email")
        TextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            Modifier.background(WhiteTransparent)
        )

        Text("Password")
        Row() {
            TextField(
                value = password.value,
                onValueChange = {
                    password.value = it
                },
                visualTransformation = if (passwordSecured.value) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.background(WhiteTransparent).widthIn(200.dp)
            )

            Button(onClick = {
                passwordSecured.value = !passwordSecured.value

            }, modifier = Modifier.widthIn(50.dp)) {
                Image(
                    painter = if (passwordSecured.value) painterResource(R.drawable.eye_slash) else painterResource(R.drawable.eye),
                    contentDescription = "Contact profile picture",
                )
            }
        }

        Button(onClick = {

            val newUser = User(
                UUID.randomUUID().toString(),
                username.value,
                password.value,
                email.value,
                "leo_1",
                0,
                0,
                0,
                "My profile info."
            )

            var yearInt = 0
            var monthInt = 0
            var dayInt = 0

            try {
                yearInt = year.value.toInt()
                monthInt = month.value.toInt()
                dayInt = day.value.toInt()
            } catch (e: Exception) {}

            val stoneIndex = User.getStoneIndex(monthInt, dayInt)
            val stoneCategory = LocalData.profileBackground[stoneIndex]
            val avatars = LocalData.stoneImages[stoneCategory]
            val avatar = avatars!![0]

            newUser.year = yearInt.toLong()
            newUser.month = monthInt.toLong()
            newUser.day = dayInt.toLong()
            newUser.avatar = avatar

            RegisterViewExtension().checkInput(newUser)


        }) {
            Text("Sign up")
        }

    }

}

class RegisterViewExtension {

    fun checkInput(newUser: User){

        if (newUser.password.length < 5) {
            Log.d("Danne", "Password invalid!")
            return
        }

        val p: Pattern = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE)
        val m: Matcher = p.matcher(newUser.username)
        val usernameInvalid: Boolean = m.find()

        if (usernameInvalid || newUser.username.length < 5 || newUser.username.length > 12) {
            Log.d("Danne", "Username invalid!")
            return
        }

        Log.d("Danne", "${android.util.Patterns.EMAIL_ADDRESS.matcher(newUser.email).matches()}")

        if (newUser.email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(newUser.email).matches()) {
            Log.d("Danne", "Email invalid!")
            return
        }

        RegisterViewExtension().createUser(newUser)
        LoginViewExtention().login(newUser)

        AppIndexManager.setIndex(AppIndex.welcomeView)

    }

    fun createUser(newUser: User) {

        val stoneIndex = User.getStoneIndex(newUser.month.toInt(), newUser.day.toInt())
        val stoneType = LocalData.profileBackground[stoneIndex]
        val avatar = stoneType.lowercase() + "_1"

        newUser.avatar = avatar

        val userModel = UserDao()
        userModel.createOrUpdateUser(newUser)

        val realmUserDao = RealmUserDao()
        val userRealm = UserRealm(
            newUser.id,
            newUser.username,
            newUser.password,
            newUser.email,
            newUser.avatar,
            newUser.year,
            newUser.month,
            newUser.day,
            newUser.profile_info

        )
        realmUserDao.createUser(userRealm)

    }

}

