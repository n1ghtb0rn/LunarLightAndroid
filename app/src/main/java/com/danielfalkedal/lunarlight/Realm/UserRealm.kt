package com.danielfalkedal.lunarlight.Realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class UserRealm: RealmObject() {

    @PrimaryKey
    val id: String = ""
    val username: String = ""
    val password: String = ""
    val email: String = ""
    var avatar: String = ""
    val year: Long = 2010
    val month: Long = 1
    val day: Long = 1

}