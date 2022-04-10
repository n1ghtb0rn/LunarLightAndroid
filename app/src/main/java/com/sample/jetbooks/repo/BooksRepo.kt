package com.sample.jetbooks.repo

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.sample.jetbooks.OnError
import com.sample.jetbooks.OnSuccess
import com.sample.jetbooks.data.Book
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class BooksRepo {

    private val firestore = FirebaseFirestore.getInstance()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getBookDetails() = callbackFlow {

        val collection = firestore.collection("world_messages").orderBy("timestamp")
        val snapshotListener = collection.addSnapshotListener { value, error ->
            val response = if (error == null) {
                OnSuccess(value)
            } else {
                OnError(error)
            }

            offer(response)
        }

        awaitClose {
            snapshotListener.remove()
        }
    }

    fun addBook(newBook: Book) {
        val dataToStore = HashMap<String, Any>()

        dataToStore.put("avatar", newBook.avatar as Any)
        dataToStore.put("day", newBook.day as Any)
        dataToStore.put("id", newBook.id as Any)
        dataToStore.put("message", newBook.message as Any)
        dataToStore.put("month", newBook.month as Any)
        dataToStore.put("timestamp", newBook.timestamp as Any)
        dataToStore.put("user_id", newBook.user_id as Any)
        dataToStore.put("username", newBook.username as Any)

        //Example 1:
        //FirebaseFirestore.getInstance().collection("users").document("danne").collection("pets").document("katt")
        //Example 2:
        //FirebaseFirestore.getInstance().document("users/danne/pets/katt")

        val id = newBook.id

        FirebaseFirestore
            .getInstance()
            .collection("world_messages").document(id)
            .set(dataToStore)
            .addOnSuccessListener { log -> Log.d("Danne", "User added to firestore with id ${newBook.id}.") }
            .addOnFailureListener { log -> Log.e("Danne", "Error: Could not add new user to database.") }
    }

}