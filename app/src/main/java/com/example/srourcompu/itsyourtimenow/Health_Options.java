package com.example.srourcompu.itsyourtimenow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Health_Options extends AppCompatActivity {

    Button bmi, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_options);

        bmi = (Button)findViewById(R.id.bmi_button);
        settings = (Button)findViewById(R.id.settings_button);

        bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bmi_intent = new Intent(Health_Options.this, BMI.class);
                startActivity(bmi_intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reminder_intent = new Intent(Health_Options.this, Health_Reminder.class);
                startActivity(reminder_intent);
            }
        });
    }
}
