package com.danielfalkedal.lunarlight.Collections

import android.util.Log
import com.danielfalkedal.lunarlight.Documents.User
import com.google.firebase.firestore.FirebaseFirestore
import com.danielfalkedal.lunarlight.Documents.UserOnline
import com.danielfalkedal.lunarlight.Responses.OnError
import com.danielfalkedal.lunarlight.Responses.OnSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.util.ArrayList

class UserOnlineModel {

    private val firestore = FirebaseFirestore.getInstance()

    var usersOnline = ArrayList<UserOnline>()

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

        firestore
            .collection("users_online").document(id)
            .set(dataToStore)
            .addOnSuccessListener { log -> Log.d("Danne", "User online updated in firestore with id ${userOnline.id}.") }
            .addOnFailureListener { log -> Log.e("Danne", "Error: Could not update user online in database.") }
    }

    fun listenToUsersOnline() {

        firestore
            .collection("users_online").whereEqualTo("is_online", true)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("Danne", "Database listener error")
                    return@addSnapshotListener
                }

                if (value == null) {
                    Log.e("Danne", "Database listener error")
                    return@addSnapshotListener
                }

                usersOnline.clear()

                for (document in value) {

                    /* Auto-mapping: */
                    val user: UserOnline = document.toObject(UserOnline::class.java)

                    usersOnline.add(user)

                    /* Manual mapping: */

                    /*
                    val id = document.getString("id")
                    val username = document.getString("username")
                    val password = document.getString("password")
                    val email = document.getString("email")
                    val avatar = document.getString("avatar")
                    val year = document.getLong("year")
                    val month = document.getLong("month")
                    val day = document.getLong("day")

                    if (id != null && username != null && password != null && email != null && avatar != null && year != null && month != null && day != null) {
                        val user = User(id, username, password, email, avatar, year, month, day)
                        users.add(user)
                    }

                     */

                }

                Log.d("danne", "Updated users list")

            }

    }

}