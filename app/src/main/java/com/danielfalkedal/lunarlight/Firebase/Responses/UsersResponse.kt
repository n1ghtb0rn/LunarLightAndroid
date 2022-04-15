package com.danielfalkedal.lunarlight.Responses

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

sealed class UsersResponse
data class OnSuccessUsers(val querySnapshot: QuerySnapshot?): UsersResponse()
data class OnErrorUsers(val exception: FirebaseFirestoreException?): UsersResponse()