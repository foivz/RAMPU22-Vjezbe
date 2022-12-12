package hr.foi.rampu.memento.services

import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import hr.foi.rampu.memento.R

class FCMService : FirebaseMessagingService() {
    private var id = 0

    override fun onCreate() {
        FirebaseMessaging.getInstance().subscribeToTopic("news")

        val channel = NotificationChannel("news", "News Channel", NotificationManager.IMPORTANCE_HIGH)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val notification =
            NotificationCompat.Builder(applicationContext, "news")
                .setContentTitle(message.data["newNewsName"])
                .setStyle(NotificationCompat.BigTextStyle().bigText(message.data["newsText"]))
                .setSmallIcon(R.drawable.ic_baseline_wysiwyg_24)
                .build()

        with(NotificationManagerCompat.from(this)) {
            notify(++id, notification)
        }
    }
}