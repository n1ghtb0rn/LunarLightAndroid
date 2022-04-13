package com.danielfalkedal.lunarlight.Collections

import android.util.Log
import com.danielfalkedal.lunarlight.AppIndexManager
import com.google.firebase.firestore.FirebaseFirestore
import com.danielfalkedal.lunarlight.Documents.User
import com.danielfalkedal.lunarlight.Responses.OnError
import com.danielfalkedal.lunarlight.Responses.OnErrorUsers
import com.danielfalkedal.lunarlight.Responses.OnSuccess
import com.danielfalkedal.lunarlight.Responses.OnSuccessUsers
import com.danielfalkedal.lunarlight.ViewModels.ONLINE_USERS
import com.danielfalkedal.lunarlight.ViewModels.USER_FRIENDS
import com.danielfalkedal.lunarlight.ViewModels.userCategory
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.util.ArrayList

class UserModel {

    private val firestore = FirebaseFirestore.getInstance()

    //Only for "background" data (not a state-var)
    var users = ArrayList<User>()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getUsersDetails() = callbackFlow {

        val userIds = ArrayList<String>()

        if (userCategory == ONLINE_USERS) {
            val usersOnline = AppIndexManager.userOnlineModel.usersOnline
            for (userOnline in usersOnline) {
                userIds.add(userOnline.id)
            }
            Log.d("Danne", "Users category = ONLINE_USERS")
        }
        else {
            val userFriends = AppIndexManager.friendModel.friends
            for (userFriend in userFriends) {
                userIds.add(userFriend.id)
            }
            Log.d("Danne", "Users category = USER_FRIENDS")
        }

        Log.d("Danne", "Searching for friends: $userIds")

        if (userIds.isEmpty()) {

            val collection = firestore.collection("unkown")
            val snapshotListener = collection.addSnapshotListener { value, error ->
                val response = if (error == null) {
                    OnSuccessUsers(value)
                } else {
                    OnErrorUsers(error)
                }

                this.trySend(response).isSuccess
            }

            awaitClose {
                snapshotListener.remove()
            }
        }

        else {

            val collection = firestore.collection("users").whereIn("id", userIds)
            val snapshotListener = collection.addSnapshotListener { value, error ->
                val response = if (error == null) {
                    Log.d("Danne", "value = ${value!!.documents.size}")
                    OnSuccessUsers(value)
                } else {
                    Log.d("Danne", "error = $error")
                    OnErrorUsers(error)
                }

                this.trySend(response).isSuccess
            }

            awaitClose {
                snapshotListener.remove()
            }

        }
    }

    fun createUser(newUser: User) {

        firestore.collection("users").document(newUser.id).set(newUser)
            .addOnSuccessListener { Log.d("Danne", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("Danne", "Error writing document", e) }

    }

    fun listenToUsers() {

        firestore
            .collection("users")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("Danne", "Database listener error")
                    return@addSnapshotListener
                }

                if (value == null) {
                    Log.e("Danne", "Database listener error")
                    return@addSnapshotListener
                }

                users.clear()

                for (document in value) {

                    /* Auto-mapping: */
                    val user: User = document.toObject(User::class.java)

                    users.add(user)

                    /* Manual mapping: */

                    /*
                    val id = document.getString("id")
                    val username = document.getString("username")
                    val password = document.getString("password")
                    val email = document.getString("email")
                    val avatar = document.getString("avatar")
                    val year = document.getLong("year")
                    val month = document.getLong("month")
                    val day = document.getLong("day")

                    if (id != null && username != null && password != null && email != null && avatar != null && year != null && month != null && day != null) {
                        val user = User(id, username, password, email, avatar, year, month, day)
                        users.add(user)
                    }

                     */

                }

            }

    }

}