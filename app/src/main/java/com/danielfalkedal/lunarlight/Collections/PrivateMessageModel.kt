package com.danielfalkedal.lunarlight.Collections

import android.util.Log
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Documents.PrivateMessage
import com.danielfalkedal.lunarlight.Documents.User
import com.danielfalkedal.lunarlight.Documents.WorldMessage
import com.danielfalkedal.lunarlight.Responses.OnErrorPrivateMsgs
import com.danielfalkedal.lunarlight.Responses.OnSuccessPrivateMsgs
import com.danielfalkedal.lunarlight.Utils.LocalData
import com.danielfalkedal.lunarlight.ViewModels.SharedViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class PrivateMessageModel {

    private val firestore = FirebaseFirestore.getInstance()

    val totalPrivateMessages = ArrayList<PrivateMessage>()
    val userPrivateMessages = ArrayList<PrivateMessage>()
    val friendPrivateMessages = ArrayList<PrivateMessage>()

    fun createPrivateMessage(newPrivateMessage: PrivateMessage) {

        val userId = AppIndexManager.currentUser.id
        val friendId = AppIndexManager.privateChatUser.id

        if (userId == friendId) {
            Log.d("Danne", "Error: Cannot create private message. user id and friend id are the same.")
            return
        }

        FirebaseFirestore
            .getInstance()
            .collection(LocalData.USERS_COLLECTION_KEY).document(userId)
            .collection(LocalData.FRIENDS_COLLECTION_KEY).document(friendId)
            .collection(LocalData.PRIVATE_MESSAGES_COLLECTION_KEY).document(newPrivateMessage.id)
            .set(newPrivateMessage)
            .addOnSuccessListener { log -> Log.d("Danne", "Private message added to firestore with id ${newPrivateMessage.id}.") }
            .addOnFailureListener { log -> Log.e("Danne", "Error: Could not add new user to database.") }
    }

    fun listenToUserPrivateMsgs() {

        val userId = AppIndexManager.currentUser.id
        val friendId = AppIndexManager.privateChatUser.id

        firestore
            .collection(LocalData.USERS_COLLECTION_KEY).document(userId)
            .collection(LocalData.FRIENDS_COLLECTION_KEY).document(friendId)
            .collection(LocalData.PRIVATE_MESSAGES_COLLECTION_KEY).orderBy("timestamp" )
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("Danne", "Database listener error")
                    return@addSnapshotListener
                }

                if (value == null) {
                    Log.e("Danne", "Database listener error")
                    return@addSnapshotListener
                }

                totalPrivateMessages.clear()
                userPrivateMessages.clear()

                for (document in value) {

                    /* Auto-mapping: */
                    val privateMessage: PrivateMessage = document.toObject(PrivateMessage::class.java)

                    userPrivateMessages.add(privateMessage)

                }

                totalPrivateMessages.addAll(userPrivateMessages)
                totalPrivateMessages.addAll(friendPrivateMessages)

                SharedViewModel.updatePrivateMessages(totalPrivateMessages)

            }

    }

    fun listenToFriendPrivateMsgs() {

        val userId = AppIndexManager.currentUser.id
        val friendId = AppIndexManager.privateChatUser.id

        firestore
            .collection(LocalData.USERS_COLLECTION_KEY).document(friendId)
            .collection(LocalData.FRIENDS_COLLECTION_KEY).document(userId)
            .collection(LocalData.PRIVATE_MESSAGES_COLLECTION_KEY).orderBy("timestamp" )
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("Danne", "Database listener error")
                    return@addSnapshotListener
                }

                if (value == null) {
                    Log.e("Danne", "Database listener error")
                    return@addSnapshotListener
                }

                totalPrivateMessages.clear()
                friendPrivateMessages.clear()

                for (document in value) {

                    /* Auto-mapping: */
                    val privateMessage: PrivateMessage = document.toObject(PrivateMessage::class.java)

                    friendPrivateMessages.add(privateMessage)

                }

                totalPrivateMessages.addAll(friendPrivateMessages)
                totalPrivateMessages.addAll(userPrivateMessages)

                SharedViewModel.updatePrivateMessages(totalPrivateMessages)

            }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPrivateMessageDetails() = callbackFlow {

        val friendId = AppIndexManager.privateChatUser.id
        val userId = AppIndexManager.currentUser.id

        val documents1 = ""
        val documents2 = ""

        val collection1 = firestore.collection(LocalData.USERS_COLLECTION_KEY).document(friendId)
            .collection(LocalData.FRIENDS_COLLECTION_KEY).document(userId)
            .collection(LocalData.PRIVATE_MESSAGES_COLLECTION_KEY).orderBy("timestamp", Query.Direction.ASCENDING)
        val snapshotListener1 = collection1.addSnapshotListener { value1, error1 ->

            val response = if (error1 == null) {
                OnSuccessPrivateMsgs(value1)
            } else {
                OnErrorPrivateMsgs(error1)
            }

            this.trySend(response).isSuccess

        }

        awaitClose {
            snapshotListener1.remove()
        }
    }

}