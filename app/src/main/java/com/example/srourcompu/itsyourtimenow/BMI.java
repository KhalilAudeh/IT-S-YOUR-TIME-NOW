package com.example.srourcompu.itsyourtimenow;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class BMI extends AppCompatActivity {

    EditText weight, height;
    TextView Result, saved_result, view_date;
    Switch sw;
    Button result_button, save_button;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String TEXT1 = "text1";
    public static final String SWITCH = "switch";

    private String text;
    private boolean switchOnOff;

    DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        // BMI
        weight = (EditText)findViewById(R.id.weight_editText);
        height = (EditText)findViewById(R.id.height_editText);
        result_button = (Button)findViewById(R.id.result_button);
        Result = (TextView)findViewById(R.id.result);
        // Save Result
        saved_result = (TextView)findViewById(R.id.saved_result);
        sw = (Switch)findViewById(R.id.switch1);
        save_button = (Button)findViewById(R.id.save_button);
        // Add Date View
        view_date = (TextView)findViewById(R.id.view_date);

        result_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BMI_Result(view);
                saved_result.setText(Result.getText().toString());
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        loadData();
        updateViews();

        view_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        BMI.this,
                        R.style.Theme_AppCompat_DayNight_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("BMI", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                view_date.setText(date);
            }
        };
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferences.Editor editor1 = sharedPreferences.edit();

        editor.putString(TEXT, saved_result.getText().toString());
        editor1.putString(TEXT1, view_date.getText().toString());
        editor.putBoolean(SWITCH, sw.isChecked());
        editor1.putBoolean(SWITCH, sw.isChecked());

        editor.apply();
        editor1.apply();

        Toast.makeText(this, "YOUR Record is Saved !!", Toast.LENGTH_SHORT).show();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, " ") + sharedPreferences.getString(TEXT1, "");
        switchOnOff = sharedPreferences.getBoolean(SWITCH, false);
    }

    public void updateViews(){
        saved_result.setText(text);
        sw.setChecked(switchOnOff);
    }

    public void BMI_Result(View view) {
        // Take weight and height
        String weight_str = weight.getText().toString();
        String height_str = height.getText().toString();

        // Check if the user puts her values
        if (weight_str != null && height_str != null && !"".equals(weight_str) && !"".equals(height_str)){

            float weight_value = Float.parseFloat(weight_str);
            float height_value = Float.parseFloat(height_str) / 100;

            float bmi = weight_value / (height_value * height_value);
            displayResult(bmi);
        }
    }

    private void displayResult(float bmi) {

        String bmi_label = "";

        if (Float.compare(bmi, 16f) > 0 && Float.compare(bmi, 18.5f) <= 0){
            bmi_label = "Considering YOUR AGE Also, YOU are Under_Weight";
        }
       else if (Float.compare(bmi, 18.5f) > 0 && Float.compare(bmi, 25f) <= 0){
            bmi_label = "Considering YOUR AGE Also, YOU have Normal BMI Value";
        }
       else if (Float.compare(bmi, 25f) > 0 && Float.compare(bmi, 30f) <= 0){
            bmi_label = "Considering YOUR AGE Also, YOU are Over_Weight";
        }
       else{
            bmi_label = "Considering YOUR AGE Also, YOU are in the Obese_Class";
        }

        bmi_label = bmi + "\n\n" + bmi_label;
        Result.setText(bmi_label);
    }
}
