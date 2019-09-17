package com.example.srourcompu.itsyourtimenow;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.cuboid.cuboidcirclebutton.CuboidButton;

import java.util.GregorianCalendar;

public class Home extends AppCompatActivity {

    CuboidButton beauty, health, appointment, feedback, notification_button, alert_button;
    Button stop_button;
    NotificationManager notificationManager;
    boolean isActive = false;
    int notificationID = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        beauty = (CuboidButton)findViewById(R.id.Beauty);
        health = (CuboidButton)findViewById(R.id.Health);
        appointment = (CuboidButton)findViewById(R.id.Booking);
        feedback = (CuboidButton)findViewById(R.id.Feedback);

        // Notification Buttons
        notification_button = (CuboidButton) findViewById(R.id.notification_button);
        alert_button = (CuboidButton) findViewById(R.id.alert_button);
        stop_button = (Button)findViewById(R.id.stop_btn);

        beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent beauty_intent = new Intent(Home.this, BeautyOptions.class);
               startActivity(beauty_intent);
            }
        });

        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent health_intent = new Intent(Home.this, Health_Options.class);
                startActivity(health_intent);
            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appointment_intent = new Intent(Home.this, Appointment_Options.class);
                startActivity(appointment_intent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void showNotification(View view) {

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setContentTitle("Message")
                .setContentText("New Notification")
                .setTicker("Alert New Notification")
                .setSmallIcon(R.drawable.bell_icon);

        Intent intent = new Intent(Home.this, MoreInfoNotification.class);

        // Task Stack Builder
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(MoreInfoNotification.class);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, builder.build());
        isActive = true;

    }

    public void Alert(View view) {

        Long alertTime = new GregorianCalendar().getTimeInMillis()+5*1000;

        Intent intent = new Intent(this, AlertReceiver.class);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alertTime, PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));

    }

    public void Stop(View view) {

        if(isActive){
            notificationManager.cancel(notificationID);
        }

    }
}
