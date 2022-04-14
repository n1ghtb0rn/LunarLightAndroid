package com.danielfalkedal.lunarlight.Responses

import com.danielfalkedal.lunarlight.Documents.PrivateMessage
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

sealed class PrivateMessagesResponse
data class OnSuccessPrivateMsgs(val privateMessages: MutableList<PrivateMessage>?): PrivateMessagesResponse()
data class OnErrorPrivateMsgs(val exception: FirebaseFirestoreException?): PrivateMessagesResponse()