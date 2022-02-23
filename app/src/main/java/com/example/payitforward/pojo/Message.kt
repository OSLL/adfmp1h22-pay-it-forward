package com.example.payitforward.pojo

import com.google.firebase.Timestamp

data class Message(
    var text: String? = null,
    var time: Timestamp? = null,
    var senderId: String? = null,
    var receiverId: String? = null,
    var dialogId: String? = null
) {

    fun toMap(): HashMap<String, Any?> {
        return hashMapOf(
            "text" to text,
            "time" to time,
            "senderId" to senderId,
            "receiverId" to receiverId,
            "dialogId" to dialogId
        )
    }
}
