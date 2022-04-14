package com.danielfalkedal.lunarlight.Realm

import android.util.Log
import com.danielfalkedal.lunarlight.AppIndexManager
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmUserDao {

    val database: Realm

    init {

        AppIndexManager.config = RealmConfiguration.Builder()
            .name("users.db")
            .allowWritesOnUiThread(true)
            .schemaVersion(1)
            .build()

        Realm.setDefaultConfiguration(AppIndexManager.config)

        database = Realm.getDefaultInstance()
    }

    fun createUser(newUser: UserRealm) {

        database.executeTransaction{
            it.insert(newUser)
        }

    }

    fun updateUser(user: UserRealm) {
        //TODO:
        database.executeTransaction{
            val userRealm = it.where(UserRealm::class.java)
                .equalTo("id", user.id)
                .findFirst()
            userRealm!!.avatar = user.avatar
            userRealm.profile_info = user.profile_info
        }
    }

    fun deleteAllUsers() {
        val users = getUsers()

        for (user in users) {
            database.executeTransaction{
                user.deleteFromRealm()
            }
        }
    }

    fun getUsers(): MutableList<UserRealm> {

        val users = mutableListOf<UserRealm>()

        users.addAll(database.where(UserRealm::class.java).findAll())

        return users

    }

}