package com.danielfalkedal.lunarlight.Realm

import io.realm.Realm

class RealmUserDao {

    val database = Realm.getDefaultInstance()

    fun addUser(newUser: UserRealm) {

        deleteAllUsers()

        database.executeTransaction{
            it.insert(newUser)
        }

    }

    fun deleteAllUsers() {
        val users = getUsers()

        for (user in users) {
            user.deleteFromRealm()
        }
    }

    fun getUsers(): MutableList<UserRealm> {

        val users = mutableListOf<UserRealm>()

        users.addAll(database.where(UserRealm::class.java).findAll())

        return users

    }

}