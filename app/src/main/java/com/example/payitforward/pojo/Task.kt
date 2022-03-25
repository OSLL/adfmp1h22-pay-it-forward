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
    var type: String, // 0 - new, 1 - completed, 2 - onReview
)