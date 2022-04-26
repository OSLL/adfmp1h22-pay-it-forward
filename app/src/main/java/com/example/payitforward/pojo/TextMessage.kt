package com.example.payitforward.pojo

import com.google.firebase.Timestamp


data class TextMessage(
    val text: String,
    override val time: Timestamp,
    override val senderId: String,
    override val receiverId: String,
    override val dialogId: String,
    override val type: String = MessageType.TEXT) : Message {
    constructor() : this("", Timestamp.now(), "", "", "")
}
