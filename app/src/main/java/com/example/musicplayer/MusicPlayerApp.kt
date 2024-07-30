package com.example.musicplayer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class MusicPlayerApp: Application() {
    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            "media_notification",
            "media_notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}