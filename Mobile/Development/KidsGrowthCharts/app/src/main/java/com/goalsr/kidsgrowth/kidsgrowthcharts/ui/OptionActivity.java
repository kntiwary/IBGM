package com.goalsr.kidsgrowth.kidsgrowthcharts.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.goalsr.kidsgrowth.kidsgrowthcharts.R;
import com.goalsr.kidsgrowth.kidsgrowthcharts.db.DataBaseHelper;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.SharedValues;

import java.io.IOException;

public class OptionActivity extends Activity {
    private Button btnNew, btnOld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        /*DataBaseHelper myDBHelper = new DataBaseHelper(this) ;
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

        }*/

        btnNew = (Button) findViewById(R.id.btn_newPatient);
        btnOld = (Button) findViewById(R.id.btn_oldPatient);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedValues.setSelectedPatientId(null);
                SharedValues.setSelectedPatientName(null);
                Intent intent = new Intent() ;
                intent.setClass(getApplicationContext(),PatientInformation.class) ;
                startActivity(intent);

            }
        });

        btnOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),PatientsListActivity.class);
                startActivity(i);
            }
        });
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            // finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}
