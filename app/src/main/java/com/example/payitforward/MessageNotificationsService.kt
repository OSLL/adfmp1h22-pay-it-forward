package com.example.payitforward

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.payitforward.pojo.ImageMessage
import com.example.payitforward.pojo.Message
import com.example.payitforward.pojo.MessageType
import com.example.payitforward.pojo.TextMessage
import com.example.payitforward.util.FirestoreUtil
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import kotlin.random.Random

class MessageNotificationsService : Service() {
    private val CHANNEL_ID = "com.example.payitforward"
    private val userId = FirebaseAuth.getInstance().currentUser!!.uid
    private val startTime = Timestamp.now().seconds

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        FirestoreUtil.getNewMessages(userId) { message ->
            if (Timestamp.now().seconds - startTime > 5) {
                showNotification(message)
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    private fun showNotification(message: Message){
        val notificationID = Random.nextInt()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }
        var messageNotification: NotificationCompat.MessagingStyle.Message

        FirestoreUtil.getUser(message.senderId) { user ->
            if(user != null) {

                if (message is TextMessage) {
                    messageNotification = NotificationCompat.MessagingStyle
                        .Message(message.text, message.time.seconds, user.name)
                } else {
                    messageNotification = NotificationCompat.MessagingStyle
                        .Message("image", message.time.seconds, user.name)
                }
                val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.icons_new_message_50)
                    .setStyle(
                        NotificationCompat.MessagingStyle("name")
                            .addMessage(messageNotification)
                    )

                notificationManager.notify(notificationID, notification.build())
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "channelName"
        val channel = NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH).apply {
            description = "My channel description"
            enableLights(true)
            lightColor = Color.GREEN
        }
        notificationManager.createNotificationChannel(channel)
    }

}