package com.example.srourcompu.itsyourtimenow;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

/**
 * Created by srourcompu on 11/16/2018.
 */

public class POP2 extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop2);

        DisplayMetrics window1 = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(window1);

        int width = window1.widthPixels;
        int height = window1.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.6));
    }
}
