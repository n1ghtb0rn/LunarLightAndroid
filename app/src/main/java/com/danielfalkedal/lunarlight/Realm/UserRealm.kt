package com.danielfalkedal.lunarlight.Realm

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class UserRealm(

    @PrimaryKey
    var id: String = "",
    var username: String = "",
    var password: String = "",
    var email: String = "",
    var avatar: String = "",
    var year: Long = 2010,
    var month: Long = 1,
    var day: Long = 1,
    var profile_info: String = ""

): RealmObject() {}