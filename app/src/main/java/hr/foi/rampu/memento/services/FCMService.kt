package hr.foi.rampu.memento.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {
    override fun onCreate() {
        FirebaseMessaging.getInstance().subscribeToTopic("news")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.i("MEMENTO_FCM_MSG", "Message data payload: ${message.data}")
    }
}