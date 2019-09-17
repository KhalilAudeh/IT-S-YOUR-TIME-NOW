package com.example.srourcompu.itsyourtimenow.reminder;

import android.app.AlarmManager;
import android.content.Context;

/**
 * Created by srourcompu on 12/3/2018.
 */

public class AlarmManagerProvider {

    private static final String TAG = AlarmManagerProvider.class.getSimpleName();
    private static AlarmManager sAlarmManager;
    public static synchronized void injectAlarmManager(AlarmManager alarmManager) {
        if (sAlarmManager != null) {
            throw new IllegalStateException("Alarm Manager Already Set");
        }
        sAlarmManager = alarmManager;
    }
    /*package*/ static synchronized AlarmManager getAlarmManager(Context context) {
        if (sAlarmManager == null) {
            sAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }
        return sAlarmManager;
    }

}
