package com.example.srourcompu.itsyourtimenow;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Hair_Page extends AppCompatActivity {

    Spinner spinner;
    Long_Hair long_hair;
    Medium_Hair medium_hair;

    Intent intent1, intent2, intent3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair__page);

        // Spinner, fragments
        spinner = (Spinner)findViewById(R.id.hair_spinner);
        long_hair = new Long_Hair();
        medium_hair = new Medium_Hair();

        // Homemade TIPS
        intent1 = new Intent(Hair_Page.this, POP1.class);
        final Button button1 = (Button)findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });

        intent2 = new Intent(Hair_Page.this, POP2.class);
        final Button button2 = (Button)findViewById(R.id.button4);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });

        intent3 = new Intent(Hair_Page.this, POP3.class);
        final Button button3 = (Button)findViewById(R.id.button5);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent3);
            }
        });

        // Spinner, Fragments
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Hair_Page.this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.hair_spinner));

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Toast.makeText(getApplicationContext(), "YOU Selected LONG_HAIR_STYLES !!", Toast.LENGTH_SHORT).show();
                        setFragment(long_hair);
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "YOU Selected MEDIUM_HAIR_STYLES !!", Toast.LENGTH_SHORT).show();
                        setFragment1(medium_hair);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setFragment1(Medium_Hair medium_hair) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, medium_hair);
        fragmentTransaction.commit();
    }

    public void setFragment(Long_Hair long_hair) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, long_hair);
        fragmentTransaction.commit();
    }
}
