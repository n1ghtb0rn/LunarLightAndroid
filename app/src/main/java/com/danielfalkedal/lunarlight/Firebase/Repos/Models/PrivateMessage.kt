package com.danielfalkedal.lunarlight.Firebase.Repos.Models

data class PrivateMessage(
    val id: String,
    val sender_id: String,
    val receiver_id: String,
    val my_message: String,
    val timestamp: Long

){
    constructor() : this("", "", "", "", 0.toLong())
}