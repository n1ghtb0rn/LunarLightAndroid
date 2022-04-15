package com.danielfalkedal.lunarlight.Firebase.Repos

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.danielfalkedal.lunarlight.Firebase.Repos.Models.WorldMessage
import com.danielfalkedal.lunarlight.Responses.OnErrorWorldMsgs
import com.danielfalkedal.lunarlight.Responses.OnSuccessWorldMsgs
import com.danielfalkedal.lunarlight.Utils.LocalData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class WorldMessageDao {

    private val firestore = FirebaseFirestore.getInstance()

    fun createWorldMessage(newWorldMessage: WorldMessage) {

        firestore
            .collection(LocalData.WORLD_MESSAGES_COLLECTION_KEY).document(newWorldMessage.id)
            .set(newWorldMessage)
            .addOnSuccessListener { log -> Log.d("Danne", "World message added to firestore with id ${newWorldMessage.id}.") }
            .addOnFailureListener { log -> Log.e("Danne", "Error: Could not add new user to database.") }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getWorldMessageDetails() = callbackFlow {

        val collection = firestore.collection("world_messages").orderBy("timestamp")
        val snapshotListener = collection.addSnapshotListener { value, error ->
            val response = if (error == null) {
                OnSuccessWorldMsgs(value)
            } else {
                OnErrorWorldMsgs(error)
            }

            this.trySend(response).isSuccess
        }

        awaitClose {
            snapshotListener.remove()
        }
    }

}