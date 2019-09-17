package com.example.srourcompu.itsyourtimenow;

import android.content.Context;
import android.database.Cursor;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.srourcompu.itsyourtimenow.data.AppointmentContract;

/**
 * Created by srourcompu on 12/19/2018.
 */

class AppointmentCursorAdapter extends CursorAdapter{

    private TextView purpose, date, result;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mDrawableBuilder;

    public AppointmentCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.activity_appointment__items, viewGroup, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        purpose = (TextView) view.findViewById(R.id.recycle_title);
        date = (TextView) view.findViewById(R.id.recycle_date);
        result = (TextView) view.findViewById(R.id.recycle_result);


        int titleColumnIndex = cursor.getColumnIndex(AppointmentContract.AppointmentEntry.KEY_PURPOSE);
        int dateColumnIndex = cursor.getColumnIndex(AppointmentContract.AppointmentEntry.KEY_DATE);
        String pending = "Pending";


        String title = cursor.getString(titleColumnIndex);
        String date = cursor.getString(dateColumnIndex);


        setReminderTitle(title);
        setReminderDate(date);
        setResult(pending);

    }

    // set if pending, accepted or failed
    private void setResult(String pending) {

        SystemClock.sleep(20000);

        if (result.equals("Accepted (Available to YOUR Choice)")) {
            result.setText("Failed (NOT Available Date and Time)");
        } else {
            result.setText("Accepted (Available to YOUR Choice)");
        }
    }

    // Set reminder title view
    public void setReminderTitle(String title) {
        purpose.setText(title);
        String letter = "A";

        if(title != null && !title.isEmpty()) {
            letter = title.substring(0, 1);
        }

        int color = mColorGenerator.getRandomColor();

        // Create a circular icon consisting of  a random background colour and first letter of title
        mDrawableBuilder = TextDrawable.builder().buildRound(letter, color);
    }

    // Set date and time views
    public void setReminderDate(String dateSet) {
        date.setText(dateSet);
    }


}
