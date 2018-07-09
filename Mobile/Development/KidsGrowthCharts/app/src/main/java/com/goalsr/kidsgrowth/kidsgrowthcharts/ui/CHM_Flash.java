package com.goalsr.kidsgrowth.kidsgrowthcharts.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.goalsr.kidsgrowth.kidsgrowthcharts.R;
import com.goalsr.kidsgrowth.kidsgrowthcharts.db.DataBaseHelper;

import java.io.IOException;

public class CHM_Flash extends Activity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chm__flash);

        DataBaseHelper myDBHelper = new DataBaseHelper(this) ;
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.getDeviceId();
        String deviceId = telephonyManager.getDeviceId();
        //Toast.makeText(this,"IMEI NUMBER: "+deviceId, Toast.LENGTH_LONG).show();
        try
        {
            myDBHelper.createDataBase(deviceId);

        }
        catch (IOException ioe)
        {
            throw new Error("Unable to create database") ;

        }

        TextView next = (TextView)findViewById(R.id.txtNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),OptionActivity.class);
                startActivity(i);
                finish();
            }
        });

       /* new Handler().postDelayed(new Runnable() {

            *//*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             *//*

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(getApplicationContext(), PatientsListActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);*/
    }
}
