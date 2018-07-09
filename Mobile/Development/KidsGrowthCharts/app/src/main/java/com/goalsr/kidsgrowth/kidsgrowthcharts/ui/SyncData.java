package com.goalsr.kidsgrowth.kidsgrowthcharts.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.goalsr.kidsgrowth.kidsgrowthcharts.R;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.PreferenceUtils;

public class SyncData extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_data);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        CheckBox checkBox = (CheckBox)findViewById(R.id.checkBoxSync);

        if(PreferenceUtils.getBoolean(getApplicationContext(), "sync_enabled")){
            checkBox.setChecked(true);

        }else{
            checkBox.setChecked(false);
        }

    }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBoxSync:
                if (checked){

                    PreferenceUtils.set(getApplicationContext(), "sync_enabled", Boolean.TRUE);
                    //(PreferenceUtils.getBoolean(getApplicationContext(), "sync_enabled"));
                    Toast.makeText(getApplicationContext(),"Data sync is enabled",Toast.LENGTH_SHORT).show();

                }else{

                    PreferenceUtils.set(getApplicationContext(), "sync_enabled", Boolean.FALSE);
                 //   System.out.println(PreferenceUtils.getBoolean(getApplicationContext(), "sync_enabled"));
                    Toast.makeText(getApplicationContext(),"Data sync is disabled",Toast.LENGTH_SHORT).show();
                }

                // Put some meat on the sandwich

        }
    }


 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.syncdata, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            Intent i = new Intent(getApplicationContext(),PatientsListActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
