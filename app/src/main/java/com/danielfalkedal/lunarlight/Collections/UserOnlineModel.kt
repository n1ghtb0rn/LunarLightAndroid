package com.danielfalkedal.lunarlight.Collections

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.danielfalkedal.lunarlight.Documents.UserOnline

class UserOnlineModel {

    private val firestore = FirebaseFirestore.getInstance()

    fun updateUserOnline(userOnline: UserOnline) {
        val dataToStore = HashMap<String, Any>()

        dataToStore.put("id", userOnline.id as Any)
        dataToStore.put("is_online", userOnline.is_online as Any)
        dataToStore.put("username", userOnline.username as Any)

        //Example 1:
        //FirebaseFirestore.getInstance().collection("users").document("danne").collection("pets").document("katt")
        //Example 2:
        //FirebaseFirestore.getInstance().document("users/danne/pets/katt")

        val id = userOnline.id

        FirebaseFirestore
            .getInstance()
            .collection("users_online").document(id)
            .set(dataToStore)
            .addOnSuccessListener { log -> Log.d("Danne", "User online updated in firestore with id ${userOnline.id}.") }
            .addOnFailureListener { log -> Log.e("Danne", "Error: Could not update user online in database.") }
    }

}