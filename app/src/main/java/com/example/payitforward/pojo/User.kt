package com.example.payitforward.pojo

data class User(
    val id: String = "",
    var name: String= "",
    val username: String = "",
    val email: String = "",
    val photo: String? = "",
    val bio: String = ""
)