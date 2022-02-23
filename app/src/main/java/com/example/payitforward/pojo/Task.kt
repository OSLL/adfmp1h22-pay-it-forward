package com.example.payitforward.pojo

data class Task(
    var id: Long,
    var creationDate: String,
    var deadlineDate: String,
    var author: User,
    var name: String,
    var description: String,
    var imageUrl: String?,
    var coins: Int,
    var type: Int
)