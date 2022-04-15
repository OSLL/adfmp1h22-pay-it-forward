package com.example.payitforward.pojo

import com.google.firebase.Timestamp

data class Task(
    var id: String = "",
    var creationDate: Timestamp = Timestamp.now(),
    var deadlineDate: Timestamp = Timestamp.now(),
    var authorId: String = "",
    var executorId: String? = "",
    var name: String = "",
    var description: String = "",
    var imageUrl: String? = "",
    var coins: Int = 0,
    var type: String = "", // new, completed, onReview, inProgress, rejected, accepted,
)