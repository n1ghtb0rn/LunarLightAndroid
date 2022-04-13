package com.danielfalkedal.lunarlight.Documents

data class UserOnline(
    val id: String,
    @field:JvmField
    val is_online: Boolean,
    val username: String

){
    constructor() : this("", false, "")
}