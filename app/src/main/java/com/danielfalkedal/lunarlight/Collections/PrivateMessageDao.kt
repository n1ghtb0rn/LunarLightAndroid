package com.danielfalkedal.lunarlight.Collections

import android.util.Log
import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Collections.Documents.PrivateMessage
import com.danielfalkedal.lunarlight.Responses.OnErrorPrivateMsgs
import com.danielfalkedal.lunarlight.Responses.OnSuccessPrivateMsgs
import com.danielfalkedal.lunarlight.Utils.LocalData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class PrivateMessageDao {

    private val firestore = FirebaseFirestore.getInstance()

    fun createPrivateMessage(newPrivateMessage: PrivateMessage) {

        val userId = AppIndexManager.loggedInUser.id
        val friendId = AppIndexManager.privateChatUser.id

        if (userId == friendId) {
            Log.d("Danne", "Error: Cannot create private message. user id and friend id are the same.")
            return
        }

        firestore
            .collection(LocalData.USERS_COLLECTION_KEY).document(userId)
            .collection(LocalData.FRIENDS_COLLECTION_KEY).document(friendId)
            .collection(LocalData.PRIVATE_MESSAGES_COLLECTION_KEY).document(newPrivateMessage.id)
            .set(newPrivateMessage)
            .addOnSuccessListener { log -> Log.d("Danne", "Private message added to firestore with id ${newPrivateMessage.id}.") }
            .addOnFailureListener { log -> Log.e("Danne", "Error: Could not add new user to database.") }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPrivateMessageDetails() = callbackFlow {


        val collection = firestore
            .collectionGroup(LocalData.PRIVATE_MESSAGES_COLLECTION_KEY).orderBy("timestamp", Query.Direction.ASCENDING)
        val snapshotListener = collection.addSnapshotListener { value, error ->

            val response = if (error == null) {

                Log.d("DanneB", "1. Value = $value")

                //Filter to get only relevant chat dialogue (between current user and current friend)
                val filteredMessages = PrivateMessageDao.getFilteredMessages(value!!)

                OnSuccessPrivateMsgs(filteredMessages)
            } else {
                Log.d("DanneB", "2. Error = $error")
                OnErrorPrivateMsgs(error)
            }

            Log.d("DanneB", "3. Response = $response")

            this.trySend(response).isSuccess

        }

        awaitClose {
            Log.d("DanneB", "4. awaitClose")
            snapshotListener.remove()
        }
    }

    companion object {

        fun getFilteredMessages(value: QuerySnapshot): MutableList<PrivateMessage> {

            val filteredMessages = ArrayList<PrivateMessage>()

            val userId = AppIndexManager.loggedInUser.id
            val friendId = AppIndexManager.privateChatUser.id

            for (document in value) {

                /* Auto-mapping: */
                val message: PrivateMessage = document.toObject(PrivateMessage::class.java)

                if (message.sender_id == userId && message.receiver_id == friendId) {
                    filteredMessages.add(message)
                }
                else if (message.sender_id == friendId && message.receiver_id == userId) {
                    filteredMessages.add(message)
                }
            }

            return filteredMessages

        }

    }

}