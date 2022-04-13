package com.danielfalkedal.lunarlight.Collections

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.danielfalkedal.lunarlight.Documents.WorldMessage
import com.danielfalkedal.lunarlight.Responses.OnError
import com.danielfalkedal.lunarlight.Responses.OnSuccess
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
                OnSuccess(value)
            } else {
                OnError(error)
            }

            this.trySend(response).isSuccess
        }

        awaitClose {
            snapshotListener.remove()
        }
    }

    fun createWorldMessage(newWorldMessage: WorldMessage) {

        /* Manual Mapping:

        val dataToStore = HashMap<String, Any>()

        dataToStore.put("avatar", newWorldMessage.avatar as Any)
        dataToStore.put("day", newWorldMessage.day as Any)
        dataToStore.put("id", newWorldMessage.id as Any)
        dataToStore.put("message", newWorldMessage.message as Any)
        dataToStore.put("month", newWorldMessage.month as Any)
        dataToStore.put("timestamp", newWorldMessage.timestamp as Any)
        dataToStore.put("user_id", newWorldMessage.user_id as Any)
        dataToStore.put("username", newWorldMessage.username as Any)

         */

        val id = newWorldMessage.id

        FirebaseFirestore
            .getInstance()
            .collection("world_messages").document(id)
            .set(newWorldMessage)
            .addOnSuccessListener { log -> Log.d("Danne", "User added to firestore with id ${newWorldMessage.id}.") }
            .addOnFailureListener { log -> Log.e("Danne", "Error: Could not add new user to database.") }
    }



}