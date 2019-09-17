package com.example.srourcompu.itsyourtimenow;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.srourcompu.itsyourtimenow.data.AlarmReminderContract;

/**
 * Created by srourcompu on 12/2/2018.
 */

class AlarmCursorAdapter extends CursorAdapter{

    private TextView text_title, date_and_time, repeat_info;
    private ImageView active_image, thumbnail_image;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mDrawableBuilder;

    public AlarmCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.activity_alarm__items, viewGroup, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        text_title = (TextView) view.findViewById(R.id.recycle_title);
        date_and_time = (TextView) view.findViewById(R.id.recycle_date_time);
        repeat_info = (TextView) view.findViewById(R.id.recycle_repeat_info);
        active_image = (ImageView) view.findViewById(R.id.active_image);
        thumbnail_image = (ImageView) view.findViewById(R.id.thumbnail_image);

        int titleColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_TITLE);
        int dateColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_DATE);
        int timeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_TIME);
        int repeatColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT);
        int repeatNoColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO);
        int activeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE);

        String title = cursor.getString(titleColumnIndex);
        String date = cursor.getString(dateColumnIndex);
        String time = cursor.getString(timeColumnIndex);
        String repeat = cursor.getString(repeatColumnIndex);
        String repeatNo = cursor.getString(repeatNoColumnIndex);
        String active = cursor.getString(activeColumnIndex);

        String dateTime = date + " " + time;


        setReminderTitle(title);
        setReminderDateTime(dateTime);
        setReminderRepeatInfo(repeat, repeatNo);
        setActiveImage(active);

    }

    // Set reminder title view
    public void setReminderTitle(String title) {
        text_title.setText(title);
        String letter = "A";

        if(title != null && !title.isEmpty()) {
            letter = title.substring(0, 1);
        }

        int color = mColorGenerator.getRandomColor();

        // Create a circular icon consisting of  a random background colour and first letter of title
        mDrawableBuilder = TextDrawable.builder().buildRound(letter, color);
        thumbnail_image.setImageDrawable(mDrawableBuilder);
    }

    // Set date and time views
    public void setReminderDateTime(String datetime) {
        date_and_time.setText(datetime);
    }

    // Set repeat views
    public void setReminderRepeatInfo(String repeat, String repeatNo) {
        if(repeat.equals("true")){
            repeat_info.setText("Repeat Number: " + repeatNo);
        }else if (repeat.equals("false")) {
            repeat_info.setText("Repeat Off");
        }
    }

    // Set active image as on or off
    public void setActiveImage(String active){
        if(active.equals("true")){
            active_image.setImageResource(R.drawable.ic_volume_up_black_24dp);
        }else if (active.equals("false")) {
            active_image.setImageResource(R.drawable.ic_volume_off_black_24dp);
        }
    }

}
