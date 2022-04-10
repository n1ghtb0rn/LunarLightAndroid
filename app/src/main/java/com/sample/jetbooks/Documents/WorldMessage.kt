package com.sample.jetbooks.Documents

data class WorldMessage(
    val id: String,
    val user_id: String,
    val username: String,
    val timestamp: Long,
    val avatar: String,
    val month: Long,
    val day: Long,
    val message: String



){
    constructor() : this("", "", "", 0, "", 1, 1, "")
}