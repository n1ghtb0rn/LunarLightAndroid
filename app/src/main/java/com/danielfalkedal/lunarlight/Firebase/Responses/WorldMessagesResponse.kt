package com.danielfalkedal.lunarlight.Responses

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

sealed class WorldMessagesResponse
data class OnSuccessWorldMsgs(val querySnapshot: QuerySnapshot?): WorldMessagesResponse()
data class OnErrorWorldMsgs(val exception: FirebaseFirestoreException?): WorldMessagesResponse()