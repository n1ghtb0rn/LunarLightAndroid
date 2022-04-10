package com.danielfalkedal.lunarlight.Documents

data class UserOnline(
    val id: String,
    val is_online: Boolean,
    val username: String

){
    constructor() : this("", false, "")
}