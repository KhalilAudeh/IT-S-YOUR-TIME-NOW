package com.example.srourcompu.itsyourtimenow;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Nails_Page extends AppCompatActivity {

    Spinner spinner;
    Nail_Shapes nail_shapes;
    Nail_Colors nail_colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nails__page);

        // Spinner, fragments
        spinner = (Spinner)findViewById(R.id.nails_spinner);
        nail_shapes = new Nail_Shapes();
        nail_colors = new Nail_Colors();

        // Spinner, Fragments
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Nails_Page.this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.nails_spinner));

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Toast.makeText(getApplicationContext(), "YOU Selected SHAPE_NAILS_STYLES !!", Toast.LENGTH_SHORT).show();
                        setFragment(nail_shapes);
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "YOU Selected COLOR_NAILS_STYLES !!", Toast.LENGTH_SHORT).show();
                        setFragment(nail_colors);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
