package com.danielfalkedal.lunarlight.Collections

import android.util.Log
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Documents.Friend
import com.danielfalkedal.lunarlight.Documents.UserOnline
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList

class FriendModel {

    private val firestore = FirebaseFirestore.getInstance()

    var friends = ArrayList<Friend>()

    private val currentUserId = AppIndexManager.currentUser.id

    fun listenToUserFriends() {

        firestore
            .collection("users").document(currentUserId).collection("friends")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("Danne", "Database listener error")
                    return@addSnapshotListener
                }

                if (value == null) {
                    Log.e("Danne", "Database listener error")
                    return@addSnapshotListener
                }

                friends.clear()

                for (document in value) {

                    /* Auto-mapping: */
                    val friend: Friend = document.toObject(Friend::class.java)

                    friends.add(friend)

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