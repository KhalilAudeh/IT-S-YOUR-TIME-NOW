package com.example.srourcompu.itsyourtimenow;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

/**
 * Created by srourcompu on 12/5/2018.
 */

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        createNotification(context, "Times Up", "10 seconds Has Passed", "Alert");

    }

    private void createNotification(Context context, String msg, String msgText, String alert) {

        PendingIntent intent = PendingIntent.getActivity(context, 0,
                new Intent(context, Home.class), 0);

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.bell_icon)
                .setContentTitle(msg)
                .setTicker(alert)
                .setContentText(msgText);

        builder.setContentIntent(intent);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());

    }
}
