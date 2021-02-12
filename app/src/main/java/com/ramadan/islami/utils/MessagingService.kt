package com.ramadan.islami.utils

import android.net.Uri
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {

    private val TAG = "FireBaseMessagingService"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val title: String = remoteMessage.notification!!.title ?: "title"
        val body: String = remoteMessage.notification!!.body ?: "body"
        val imageUrl: Uri = (remoteMessage.notification!!.imageUrl ?: defaultImg) as Uri
        val intentName: String = remoteMessage.notification!!.clickAction ?: "intentName"
        val data = remoteMessage.data["id"] ?: "data"
        remoteMessage.messageType
        remoteMessage.toIntent()
        Log.e("toto", intentName)
        Log.e("toto", data)
        NotificationUtil(this).showNotification(title, body, imageUrl, intentName, data)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.e(TAG, "New Token")
    }

}
