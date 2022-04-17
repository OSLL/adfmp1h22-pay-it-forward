package com.example.payitforward

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MessageNotificationService : FirebaseMessagingService() {

    private val CHANNEL_ID = "com.example.payitforward"
    private var notificationId = 1

    override fun onNewToken(token: String) {

    }

    override fun onMessageReceived(message: RemoteMessage) {
        if (message.notification != null) {
            showNotification(message)
        }
    }

    fun showNotification(remoteMessage: RemoteMessage){
        val messageNotification = NotificationCompat.MessagingStyle.Message(remoteMessage.notification?.title, 1000, "anna")
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.icons_new_message_50)
            .setStyle(NotificationCompat.MessagingStyle("name").addMessage(messageNotification))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification.build())
        notificationId += 1
    }

}