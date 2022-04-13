package com.danielfalkedal.lunarlight.Collections

import com.danielfalkedal.lunarlight.AppIndexManager
import com.danielfalkedal.lunarlight.Responses.OnErrorPrivateMsgs
import com.danielfalkedal.lunarlight.Responses.OnSuccessPrivateMsgs
import com.danielfalkedal.lunarlight.Utils.LocalData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class PrivateMessageModel {

    private val firestore = FirebaseFirestore.getInstance()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPrivateMessageDetails() = callbackFlow {

        val friendId = AppIndexManager.privateChatUser.id
        val userId = AppIndexManager.currentUser.id

        val collection = firestore.collection(LocalData.USERS_COLLECTION_KEY).document(friendId)
            .collection(LocalData.FRIENDS_COLLECTION_KEY).document(userId)
            .collection(LocalData.PRIVATE_MESSAGES_COLLECTION_KEY).orderBy("timestamp", Query.Direction.ASCENDING)
        val snapshotListener = collection.addSnapshotListener { value, error ->
            val response = if (error == null) {
                OnSuccessPrivateMsgs(value)
            } else {
                OnErrorPrivateMsgs(error)
            }

            this.trySend(response).isSuccess
        }

        awaitClose {
            snapshotListener.remove()
        }
    }

}