package com.danielfalkedal.lunarlight.Firebase.Repos

import android.util.Log
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Firebase.Repos.Models.Friend
import com.danielfalkedal.lunarlight.Utils.LocalData
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList

class FriendDao {

    private val firestore = FirebaseFirestore.getInstance()

    var friendsIds = ArrayList<String>()

    fun addFriend(newFriend: Friend){

        val currentUserId = AppIndexManager.loggedInUser.id

        firestore
            .collection(LocalData.USERS_COLLECTION_KEY).document(currentUserId)
            .collection(LocalData.FRIENDS_COLLECTION_KEY).document(newFriend.id).set(newFriend)
            .addOnSuccessListener { Log.d("Danne", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("Danne", "Error writing document", e) }

    }

    fun listenToUserFriends() {

        val currentUserId = AppIndexManager.loggedInUser.id

        firestore
            .collection(LocalData.USERS_COLLECTION_KEY).document(currentUserId).collection(LocalData.FRIENDS_COLLECTION_KEY)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("Danne", "Database listener error")
                    return@addSnapshotListener
                }

                if (value == null) {
                    Log.e("Danne", "Database listener error")
                    return@addSnapshotListener
                }

                friendsIds.clear()

                for (document in value) {

                    Log.d("Danne", "document = ${document.data.keys}")

                    /* Auto-mapping: */
                    val friend: Friend = document.toObject(Friend::class.java)
                    
                    Log.d("Danne", "$friend")

                    friendsIds.add(friend.id)

                }

            }

    }

}