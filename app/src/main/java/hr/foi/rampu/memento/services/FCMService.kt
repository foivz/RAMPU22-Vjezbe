package hr.foi.rampu.memento.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import hr.foi.rampu.memento.NewsActivity
import hr.foi.rampu.memento.R

class FCMService : FirebaseMessagingService() {
    private var id = 0

    override fun onCreate() {
        FirebaseMessaging.getInstance().subscribeToTopic("news")

        val channelNews = NotificationChannel("news", "News Channel", NotificationManager.IMPORTANCE_HIGH)
        val channelInfo = NotificationChannel("info", "Info Channel", NotificationManager.IMPORTANCE_HIGH)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channelNews)
        notificationManager.createNotificationChannel(channelInfo)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        lateinit var notification: Notification
        val payload = message.data["payload"]

        if (payload == "news") {
            val intentShow = Intent(this, NewsActivity::class.java).apply {
                putExtra("news_name", message.data["newNewsName"])
            }

            val openActivityIntent = PendingIntent.getActivity(this, 0, intentShow, PendingIntent.FLAG_IMMUTABLE)

            notification =
                NotificationCompat.Builder(applicationContext, "news")
                    .setContentTitle(message.data["newNewsName"])
                    .setStyle(NotificationCompat.BigTextStyle().bigText(message.data["newsText"]))
                    .setSmallIcon(R.drawable.ic_baseline_wysiwyg_24)
                    .setContentIntent(openActivityIntent)
                    .setAutoCancel(true)
                    .build()

        } else {
            notification =
                NotificationCompat.Builder(applicationContext, "info")
                    .setContentTitle(message.data["infoTitle"])
                    .setStyle(NotificationCompat.BigTextStyle().bigText(message.data["infoText"]))
                    .setSmallIcon(R.drawable.ic_baseline_info_24)
                    .build()
        }

        with(NotificationManagerCompat.from(this)) {
            notify(++id, notification)
        }
    }
}