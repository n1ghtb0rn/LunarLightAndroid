package com.sample.jetbooks.Documents

data class User(
    val id: String,
    val username: String,
    val password: String,
    val email: String,
    val avatar: String,
    val year: Long,
    val month: Long,
    val day: Long

){
    constructor() : this("", "", "", "", "", 2010, 1, 1)
}