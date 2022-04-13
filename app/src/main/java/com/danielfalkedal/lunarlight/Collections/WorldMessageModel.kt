package com.danielfalkedal.lunarlight.Collections

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.danielfalkedal.lunarlight.Documents.WorldMessage
import com.danielfalkedal.lunarlight.Responses.OnErrorWorldMsgs
import com.danielfalkedal.lunarlight.Responses.OnSuccessWorldMsgs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class WorldMessageModel {

    private val firestore = FirebaseFirestore.getInstance()

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

    fun createWorldMessage(newWorldMessage: WorldMessage) {

        val id = newWorldMessage.id

        FirebaseFirestore
            .getInstance()
            .collection("world_messages").document(id)
            .set(newWorldMessage)
            .addOnSuccessListener { log -> Log.d("Danne", "World message added to firestore with id ${newWorldMessage.id}.") }
            .addOnFailureListener { log -> Log.e("Danne", "Error: Could not add new user to database.") }
    }



}