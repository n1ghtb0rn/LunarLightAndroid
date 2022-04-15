package com.danielfalkedal.lunarlight.Firebase.Repos

import android.util.Log
import com.danielfalkedal.lunarlight.AppIndex
import com.danielfalkedal.lunarlight.AppIndexManager
import com.google.firebase.firestore.FirebaseFirestore
import com.danielfalkedal.lunarlight.Firebase.Repos.Models.UserOnline
import com.danielfalkedal.lunarlight.Utils.LocalData
import java.util.*

class UserOnlineDao {

    private val firestore = FirebaseFirestore.getInstance()

    var usersOnlineIds = ArrayList<String>()

    fun updateUserOnline(userOnline: UserOnline) {

        firestore
            .collection(LocalData.USERS_ONLINE_COLLECTION_KEY).document(userOnline.id)
            .set(userOnline)
            .addOnSuccessListener { log -> Log.d("Danne", "User online updated in firestore with id ${userOnline.id}.") }
            .addOnFailureListener { log -> Log.e("Danne", "Error: Could not update user online in database.") }
    }

    fun listenToUsersOnline() {

        Log.d("DanneX", "Activating listener...")

        firestore
            .collection(LocalData.USERS_ONLINE_COLLECTION_KEY)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("Danne", "Database listener error")
                    return@addSnapshotListener
                }

                if (value == null) {
                    Log.e("Danne", "Database listener error")
                    return@addSnapshotListener
                }

                Log.d("DanneX", "Online users was changed in firestore!")

                usersOnlineIds.clear()

                for (document in value) {

                    /* Auto-mapping: */
                    val user: UserOnline = document.toObject(UserOnline::class.java)

                    if (user.is_online) {
                        usersOnlineIds.add(user.id)
                    }

                }

                if (AppIndexManager.appIndex.value == AppIndex.onlineUsersView) {
                    AppIndexManager.setIndex(AppIndex.lobbyTabView)
                    Timer().schedule(object : TimerTask() {
                        override fun run() {
                            AppIndexManager.setIndex(AppIndex.onlineUsersView)
                        }
                    }, 10)

                }

            }

    }

}