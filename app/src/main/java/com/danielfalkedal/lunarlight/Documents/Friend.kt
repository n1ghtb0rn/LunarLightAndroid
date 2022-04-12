package com.danielfalkedal.lunarlight.Documents

data class Friend(
    val id: String,
    val is_online: Boolean,
    val username: String

){
    constructor() : this("", false, "")
}