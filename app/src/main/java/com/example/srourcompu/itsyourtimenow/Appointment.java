package com.example.srourcompu.itsyourtimenow;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.srourcompu.itsyourtimenow.data.AlarmReminderContract;
import com.example.srourcompu.itsyourtimenow.data.AlarmReminderDBHelper;
import com.example.srourcompu.itsyourtimenow.data.AppointmentContract;

public class Appointment extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private FloatingActionButton add_appointment_button;
    AppointmentCursorAdapter appointmentCursorAdapter;
    ListView listView;

    private static final int VEHICLE_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);


        // List View
        listView = (ListView)findViewById(R.id.list_item);
        android.view.View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        appointmentCursorAdapter = new AppointmentCursorAdapter(this, null);
        listView.setAdapter(appointmentCursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(Appointment.this, Add_Appointment.class);

                Uri currentVehicleUri = ContentUris.withAppendedId(AlarmReminderContract.AlarmReminderEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentVehicleUri);

                startActivity(intent);

            }
        });


        add_appointment_button = (FloatingActionButton) findViewById(R.id.add_appointment);

        add_appointment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Add_Appointment.class);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(VEHICLE_LOADER, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                AppointmentContract.AppointmentEntry._ID,
                AppointmentContract.AppointmentEntry.KEY_ID,
                AppointmentContract.AppointmentEntry.KEY_PURPOSE,
                AppointmentContract.AppointmentEntry.KEY_DATE,
                AppointmentContract.AppointmentEntry.KEY_TIME,
                AppointmentContract.AppointmentEntry.KEY_LOCATION
        };

        return new CursorLoader(this,   // Parent activity context
                AlarmReminderContract.AlarmReminderEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        appointmentCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        appointmentCursorAdapter.swapCursor(null);
    }

}
