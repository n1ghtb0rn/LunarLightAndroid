package com.danielfalkedal.lunarlight.Responses

import com.danielfalkedal.lunarlight.Firebase.Repos.Models.PrivateMessage
import com.google.firebase.firestore.FirebaseFirestoreException

sealed class PrivateMessagesResponse
data class OnSuccessPrivateMsgs(val privateMessages: MutableList<PrivateMessage>?): PrivateMessagesResponse()
data class OnErrorPrivateMsgs(val exception: FirebaseFirestoreException?): PrivateMessagesResponse()