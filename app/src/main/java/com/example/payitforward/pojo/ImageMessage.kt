package com.example.payitforward.pojo

import com.google.firebase.Timestamp


data class ImageMessage(
    val image: String,
    override val time: Timestamp,
    override val senderId: String,
    override val receiverId: String,
    override val dialogId: String,
    override val type: String = MessageType.IMAGE) : Message {
    constructor() : this("", Timestamp.now(), "", "", "")
}
