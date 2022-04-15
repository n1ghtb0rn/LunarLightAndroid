package com.danielfalkedal.lunarlight.Responses

import com.danielfalkedal.lunarlight.Firebase.Repos.Models.PrivateMessage
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

sealed class PrivateMessagesResponse
data class OnSuccessPrivateMsgs(val querySnapshot: QuerySnapshot?): PrivateMessagesResponse()
data class OnErrorPrivateMsgs(val exception: FirebaseFirestoreException?): PrivateMessagesResponse()