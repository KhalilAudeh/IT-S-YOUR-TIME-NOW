package com.example.srourcompu.itsyourtimenow;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.srourcompu.itsyourtimenow.data.AlarmReminderContract;
import com.example.srourcompu.itsyourtimenow.data.AppointmentContract;
import com.example.srourcompu.itsyourtimenow.reminder.AlarmScheduler;

import java.util.Calendar;

public class Add_Appointment extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{


    private static final int EXISTING_VEHICLE_LOADER = 0;

    private EditText userId, purpose, date, time, location;
    private Button add_btn;
    private String mPurpose;
    private String mTime;
    private String mDate;
    private String mLocation;
    private String mUserId;

    private Uri mCurrentAppointmentUri;
    private boolean HasChanged = false;

    // Values for orientation change
    private static final String KEY_ID = "id_key";
    private static final String KEY_PURPOSE = "purpose_key";
    private static final String KEY_TIME = "time_key";
    private static final String KEY_DATE = "date_key";
    private static final String KEY_LOCATION = "location_key";

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            HasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__appointment);

        Intent intent = getIntent();
        mCurrentAppointmentUri = intent.getData();

        if (mCurrentAppointmentUri == null) {

            setTitle("Add Appointment Details");

            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a reminder that hasn't been created yet.)
            invalidateOptionsMenu();
        } else {

            setTitle("Edit Appointment");

            getLoaderManager().initLoader(EXISTING_VEHICLE_LOADER, null, this);
        }


        // Initialize Views
        userId = (EditText)findViewById(R.id.userId);
        purpose = (EditText)findViewById(R.id.purpose);
        date = (EditText)findViewById(R.id.date);
        time = (EditText)findViewById(R.id.time);
        location = (EditText)findViewById(R.id.location);
        add_btn = (Button) findViewById(R.id.add_appointment_button);


        // Setup Reminder Title EditText
        purpose.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPurpose = s.toString().trim();
                purpose.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Setup TextViews using reminder values
        date.setText(mDate);
        time.setText(mTime);
        location.setText(mLocation);
        userId.setText(mUserId);

        // To save state on device rotation
        if (savedInstanceState != null) {
            String savedID = savedInstanceState.getString(KEY_ID);
            userId.setText(savedID);
            mUserId = savedID;

            String savedPurpose = savedInstanceState.getString(KEY_PURPOSE);
            purpose.setText(savedPurpose);
            mPurpose = savedPurpose;

            String savedDate = savedInstanceState.getString(KEY_DATE);
            date.setText(savedDate);
            mDate = savedDate;

            String savedTime = savedInstanceState.getString(KEY_TIME);
            time.setText(savedTime);
            mTime = savedTime;

            String savedLocation = savedInstanceState.getString(KEY_LOCATION);
            location.setText(savedLocation);
            mLocation = savedLocation;

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence(KEY_ID, userId.getText());
        outState.putCharSequence(KEY_PURPOSE, purpose.getText());
        outState.putCharSequence(KEY_DATE, date.getText());
        outState.putCharSequence(KEY_TIME, time.getText());
        outState.putCharSequence(KEY_LOCATION, location.getText());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new reminder, hide the "Delete" menu item.
        if (mCurrentAppointmentUri == null) {
            MenuItem menuItem = menu.findItem(R.id.discard_reminder);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.save_reminder:


                if (purpose.getText().toString().length() == 0){
                    purpose.setError("Reminder Title cannot be blank!");
                }

                else {
                    saveReminder();
                    finish();
                }
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.discard_reminder:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the reminder hasn't changed, continue with navigating up to parent activity
                // which is the {@link MainActivity}.
                if (!HasChanged) {
                    NavUtils.navigateUpFromSameTask(Add_Appointment.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(Add_Appointment.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("unsaved_changes_dialog_msg");
        builder.setPositiveButton("discard", discardButtonClickListener);
        builder.setNegativeButton("keep_editing", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the reminder.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("delete_dialog_msg");
        builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the reminder.
                deleteReminder();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the reminder.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteReminder() {
        // Only perform the delete if this is an existing reminder.
        if (mCurrentAppointmentUri != null) {
            // Call the ContentResolver to delete the reminder at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentreminderUri
            // content URI already identifies the reminder that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentAppointmentUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, "editor_delete_reminder_failed",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, "editor_delete_reminder_successful",
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Close the activity
        finish();
    }

    // On clicking the save button
    public void saveReminder(){

     /*   if (mCurrentReminderUri == null ) {
            // Since no fields were modified, we can return early without creating a new reminder.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }
*/
        ContentValues values = new ContentValues();

        values.put(AppointmentContract.AppointmentEntry.KEY_ID, mUserId);
        values.put(AppointmentContract.AppointmentEntry.KEY_PURPOSE, mPurpose);
        values.put(AppointmentContract.AppointmentEntry.KEY_DATE, mDate);
        values.put(AppointmentContract.AppointmentEntry.KEY_TIME, mTime);
        values.put(AppointmentContract.AppointmentEntry.KEY_LOCATION, mLocation);


        if (mCurrentAppointmentUri == null) {
            // This is a NEW reminder, so insert a new reminder into the provider,
            // returning the content URI for the new reminder.
            Uri newUri = getContentResolver().insert(AppointmentContract.AppointmentEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, "editor_insert_reminder_failed",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, "editor_insert_reminder_successful",
                        Toast.LENGTH_SHORT).show();
            }
        } else {

            int rowsAffected = getContentResolver().update(mCurrentAppointmentUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, "editor_update_reminder_failed",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, "editor_update_reminder_successful",
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Create toast to confirm new reminder
        Toast.makeText(getApplicationContext(), "Saved",
                Toast.LENGTH_SHORT).show();

    }

    // On pressing the back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                AppointmentContract.AppointmentEntry._ID,
                AppointmentContract.AppointmentEntry.KEY_ID,
                AppointmentContract.AppointmentEntry.KEY_PURPOSE,
                AppointmentContract.AppointmentEntry.KEY_DATE,
                AppointmentContract.AppointmentEntry.KEY_TIME,
                AppointmentContract.AppointmentEntry.KEY_LOCATION,

        };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentAppointmentUri,         // Query the content URI for the current reminder
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            int titleColumnIndex = cursor.getColumnIndex(AppointmentContract.AppointmentEntry.KEY_PURPOSE);
            int dateColumnIndex = cursor.getColumnIndex(AppointmentContract.AppointmentEntry.KEY_DATE);
            int timeColumnIndex = cursor.getColumnIndex(AppointmentContract.AppointmentEntry.KEY_TIME);
            int locationColumnIndex = cursor.getColumnIndex(AppointmentContract.AppointmentEntry.KEY_LOCATION);
            int idColumnIndex = cursor.getColumnIndex(AppointmentContract.AppointmentEntry.KEY_ID);

            // Extract out the value from the Cursor for the given column index
            String title = cursor.getString(titleColumnIndex);
            String d = cursor.getString(dateColumnIndex);
            String t = cursor.getString(timeColumnIndex);
            String loca = cursor.getString(locationColumnIndex);
            String id = cursor.getString(idColumnIndex);


            // Update the views on the screen with the values from the database
            purpose.setText(title);
            date.setText(d);
            time.setText(t);
            location.setText(loca);
            userId.setText(id);

        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
