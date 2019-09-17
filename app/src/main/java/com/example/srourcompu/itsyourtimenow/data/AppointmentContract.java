package com.example.srourcompu.itsyourtimenow.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by srourcompu on 12/19/2018.
 */

public class AppointmentContract {

    private AppointmentContract() {}

    public static final String CONTENT_AUTHORITY = "com.example.srourcompu.itsyourtimenow";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_HEALTH = "appointment-path";

    public static final class AppointmentEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_HEALTH);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HEALTH;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HEALTH;

        public final static String TABLE_NAME = "appointment";

        public final static String _ID = BaseColumns._ID;

        public static final String KEY_ID = "id";
        public static final String KEY_PURPOSE = "purpose";
        public static final String KEY_DATE = "date";
        public static final String KEY_TIME = "time";
        public static final String KEY_LOCATION = "location";

    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

}
