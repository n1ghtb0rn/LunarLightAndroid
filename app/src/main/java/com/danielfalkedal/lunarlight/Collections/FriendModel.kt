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

                    Log.d("Danne", "document = ${document.data.keys}")

                    /* Auto-mapping: */
                    val friend: Friend = document.toObject(Friend::class.java)
                    
                    Log.d("Danne", "$friend")

                    friends.add(friend)

                }

            }

    }

}