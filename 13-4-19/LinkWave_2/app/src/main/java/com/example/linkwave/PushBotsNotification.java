package com.example.linkwave;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.google.firebase.database.FirebaseDatabase;
import com.pushbots.push.Pushbots;

public class PushBotsNotification extends Application {
    public static final String CHANNEL_1_ID = "channel1";

    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        // Initialize Pushbots Library
        createNotificationChannels();
        Pushbots.sharedInstance().init(this);
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");


            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);

        }
    }
}
