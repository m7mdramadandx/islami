package com.ramadan.islami.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import com.ramadan.islami.R
import com.ramadan.islami.ui.activity.InformationList
import java.io.IOException
import kotlin.random.Random


class NotificationUtil(private val context: Context) {
    val unOpenCount = Random.nextInt()

    fun showNotification(
        title: String,
        message: String,
        imageUri: Uri,
        intentName: String,
        data: String,
    ) {
        val bitmap: Bitmap = try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder
                    .decodeBitmap(ImageDecoder.createSource(context.contentResolver, imageUri))
            } else MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        } catch (e: IOException) {
            (context.resources.getDrawable(R.drawable.failure_img) as BitmapDrawable).bitmap
        }

        val intent = Intent(context, InformationList::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_ONE_SHOT)

        val notificationBuilder =
            NotificationCompat.Builder(context, context.getString(R.string.channel_id))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOngoing(true)
                .setVibrate(LongArray(10000))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setColor(context.resources.getColor(R.color.colorAccent))
                .setLargeIcon(bitmap)
                .setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
                    .bigLargeIcon(null))

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                context.getString(R.string.channel_id),
                "default",
                NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.canShowBadge()
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }

//        var unOpenCount: Int = AppUtill.getPreferenceInt("NOTICOUNT", this)
//        unOpenCount += 1

//        AppUtill.savePreferenceLong("NOTICOUNT", unOpenCount, this)
//        notificationManager.notify(unOpenCount ,
//            notificationBuilder.build())

        notificationManager.notify(unOpenCount, notificationBuilder.build())
//        BadgeUtils().setBadge(context, unOpenCount)
    }
}
