package com.danielfalkedal.lunarlight.Collections

import android.util.Log
import com.danielfalkedal.lunarlight.AppIndexManager
import com.google.firebase.firestore.FirebaseFirestore
import com.danielfalkedal.lunarlight.Documents.User
import com.danielfalkedal.lunarlight.Responses.OnErrorUsers
import com.danielfalkedal.lunarlight.Responses.OnSuccessUsers
import com.danielfalkedal.lunarlight.ViewModels.ONLINE_USERS
import com.danielfalkedal.lunarlight.ViewModels.userCategory
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

        var userIds = ArrayList<String>()

        if (userCategory == ONLINE_USERS) {
            userIds = AppIndexManager.userOnlineModel.usersOnlineIds
            Log.d("Danne", "Users category = ONLINE_USERS")
        }
        else {
            userIds = AppIndexManager.friendModel.friendsIds
            Log.d("Danne", "Users category = USER_FRIENDS")
        }

        //if (userIds.isEmpty()) {
        if (false) {

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

            Log.d("DanneX", "User listener started!")

            val collection = firestore.collection("users")//.whereIn("id", userIds)
            val snapshotListener = collection.addSnapshotListener { value, error ->

                Log.d("DanneX", "Response:")

                val response = if (error == null) {
                    Log.d("DanneX", "value = ${value!!.documents.size}")
                    OnSuccessUsers(value)
                } else {
                    Log.d("DanneX", "error = $error")
                    OnErrorUsers(error)
                }

                Log.d("DanneX", "Sending response.")

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