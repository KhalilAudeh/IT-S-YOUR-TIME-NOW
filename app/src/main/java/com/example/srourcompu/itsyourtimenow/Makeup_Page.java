package com.example.srourcompu.itsyourtimenow;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Makeup_Page extends AppCompatActivity {

    TextView textView;
    Spinner spinner;
    frag1 f1;
    fragment2 f2;
    fragment3 f3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeup__page);

        textView = (TextView)findViewById(R.id.textView5);
        spinner = (Spinner)findViewById(R.id.makeup_spinner);
        f1 = new frag1();
        f2 = new fragment2();
        f3 = new fragment3();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Makeup_Page.this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.makeup_spinner));

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Toast.makeText(getApplicationContext(), "YOU Selected Contouring !!", Toast.LENGTH_SHORT).show();
                        setFragment(f1);
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "YOU Selected EYE_MAKEUP !!", Toast.LENGTH_SHORT).show();
                        setFragment1(f2);
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "YOU Selected LIPS TIPS !!", Toast.LENGTH_SHORT).show();
                        setFragment2(f3);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setFragment2(fragment3 f3) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, f3);
        fragmentTransaction.commit();
    }

    public void setFragment1(fragment2 f2) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, f2);
        fragmentTransaction.commit();
    }

    public void setFragment(frag1 frag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, frag);
        fragmentTransaction.commit();
    }

}
