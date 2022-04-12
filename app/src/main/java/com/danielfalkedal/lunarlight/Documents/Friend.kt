package com.danielfalkedal.lunarlight.Documents

data class Friend(
    val id: String,
    val user_id: String

){
    constructor() : this("", "")
}