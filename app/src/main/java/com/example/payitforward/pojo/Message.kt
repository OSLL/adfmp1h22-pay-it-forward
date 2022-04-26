package com.example.payitforward.pojo

import com.google.firebase.Timestamp

object MessageType {
    const val TEXT = "TEXT"
    const val IMAGE = "IMAGE"
}

interface Message {
    val time: Timestamp
    val senderId: String
    val receiverId: String
    val dialogId: String
    val type: String
}
