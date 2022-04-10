package com.sample.jetbooks.Responses

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

sealed class WorldMessagesResponse
data class OnSuccess(val querySnapshot: QuerySnapshot?): WorldMessagesResponse()
data class OnError(val exception: FirebaseFirestoreException?): WorldMessagesResponse()