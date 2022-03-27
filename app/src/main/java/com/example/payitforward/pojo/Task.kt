package com.example.payitforward.pojo

import com.google.firebase.Timestamp

data class Task(
    var id: String = "",
    var creationDate: Timestamp = Timestamp.now(),
    var deadlineDate: Timestamp = Timestamp.now(),
    var authorId: String = "",
    var completedId: String? = "",
    var name: String = "",
    var description: String = "",
    var imageUrl: String? = "",
    var coins: Int = 0,
    var type: String = "", // 0 - new, 1 - completed, 2 - onReview

)