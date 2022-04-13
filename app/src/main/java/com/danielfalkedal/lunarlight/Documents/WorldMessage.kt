package com.danielfalkedal.lunarlight.Documents

data class WorldMessage(
    val id: String,
    val user_id: String,
    val username: String,
    val timestamp: ULong,
    val avatar: String,
    val month: Long,
    val day: Long,
    val message: String



){
    constructor() : this("", "", "", 0.toULong(), "", 1, 1, "")
}