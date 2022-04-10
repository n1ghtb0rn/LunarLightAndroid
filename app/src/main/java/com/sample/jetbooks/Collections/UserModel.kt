package com.sample.jetbooks.Collections

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.sample.jetbooks.Documents.User
import java.util.ArrayList

class UserModel {

    private val firestore = FirebaseFirestore.getInstance()

    var users = ArrayList<User>()

    fun listenToUsers() {

        FirebaseFirestore
            .getInstance()
            .collection("users")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("Danne", "Database listener error")
                    return@addSnapshotListener
                }

                if (value == null) {
                    Log.e("Danne", "Database listener error")
                    return@addSnapshotListener
                }

                users.clear()

                for (document in value) {

                    /* Auto-mapping: */
                    val user: User = document.toObject(User::class.java)

                    users.add(user)

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