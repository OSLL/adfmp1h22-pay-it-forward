package com.example.payitforward.pojo

import com.google.firebase.Timestamp

data class Task(
    var id: String,
    var creationDate: Timestamp,
    var deadlineDate: Timestamp,
    var authorId: String,
    var completedId: String?,
    var name: String,
    var description: String,
    var imageUrl: String?,
    var coins: Int,
    var type: Int, // 0 - take, 1 - done, 2 - accept, reject
)