package com.danielfalkedal.lunarlight.Firebase.Repos.Models

data class UserOnline(
    val id: String,
    @field:JvmField
    val is_online: Boolean,
    val username: String

){
    constructor() : this("", false, "")
}