package com.goalsr.kidsgrowth.kidsgrowthcharts.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.goalsr.kidsgrowth.kidsgrowthcharts.ui.PatientsListActivity;

/**
 * Created by bhanagandi on 1/6/2016.
 */
public class CHMActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Perform on create common activity
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Perform on start common activity
    }

    @Override
    protected void onResume() {
        super.onResume();
                //Perform on resume common activity
    }

    @Override
    protected void onPause() {
        super.onPause();


        //Perform on pause common activity
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Perform on stop common activity
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e("############# onDestroy", this.getClass().getName());

        //Perform on destroy common activity
        int netstatus = NetworkUtil.getConnectivityStatus(getApplicationContext()) ;
        if (netstatus != 0 ) {
            PushDataToServer pushDataToServer = new PushDataToServer(getApplicationContext());
            pushDataToServer.syncData();
        }
    }
}
