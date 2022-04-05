package com.c196Assessment.studentscheduler.Utilities;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.c196Assessment.studentscheduler.R;
import com.c196Assessment.studentscheduler.UI.MainActivity;


//This class is created so the user can get a notification even if the app is closed.
//It uses a receiver class to know when it is getting an incoming notification and a
//notification channel creation class to send the notifications.
public class MyReceiver extends BroadcastReceiver {
    String channel_id="test";
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context,intent.getStringExtra("key"),Toast.LENGTH_LONG).show();
        createNotificationChannel(context,channel_id);
        Notification n= new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.night_tree)
                .setContentText(intent.getStringExtra("key"))
                .setContentTitle("Test of Notification with an id of :"+Integer.toString(MainActivity.numAlert)).build();

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(MainActivity.numAlert,n);
    }
    private void createNotificationChannel(Context context, String CHANNEL_ID){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}