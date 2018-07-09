package com.goalsr.kidsgrowth.kidsgrowthcharts.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActionBar ;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.goalsr.kidsgrowth.kidsgrowthcharts.R;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.SharedValues;

import java.util.ArrayList;

public class PatientInformation extends TabActivity implements TabHost.OnTabChangeListener{

    public static TabHost tabHost ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_information);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        tabHost = (TabHost) findViewById(android.R.id.tabhost) ;
       // int indexvalue =  getIntent().getIntExtra("info",0);
        tabHost.setOnTabChangedListener(this);

        TabHost.TabSpec tabSpec ;

        ImageView imageView = new ImageView(getApplicationContext()) ;
        imageView.setMaxWidth(72);
        imageView.setMaxHeight(16);
        imageView.setImageResource(R.drawable.light_blue_circle_logo);

        Intent intent = new Intent().setClass(this, PatientBasicInputs.class) ;
        tabSpec = tabHost.newTabSpec("Patient").setIndicator("").setContent(intent) ;
        //tabSpec.setIndicator(imageView) ;
        tabSpec.setIndicator("Add/Edit Patient");
        tabHost.addTab(tabSpec);


        imageView = new ImageView(getApplicationContext()) ;
        imageView.setMaxWidth(72);
        imageView.setMaxHeight(16);
        imageView.setImageResource(R.drawable.light_blue_plus_logo);

        intent = new Intent().setClass(this, PatientGrowthDataInputs.class) ;
        tabSpec = tabHost.newTabSpec("Data").setIndicator("").setContent(intent) ;
        //tabSpec.setIndicator(imageView) ;
        tabSpec.setIndicator("Visit Data");

        tabHost.addTab(tabSpec);
        setTabColor(tabHost,0);
        setTabbColor(tabHost);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {


                switch (tabId){
                    case "Patient":
                        setTabColor(tabHost,0);
                        setTabbColor(tabHost);
                        break;
                    case "Data":
                        setTabColor(tabHost,1);
                        setTabbColor(tabHost);
                        break;
                }

                /*switch (tabId){
                    case "Patient":
                        Toast.makeText(PatientInformation.this, "Clicke on Patient data tab", Toast.LENGTH_SHORT).show();
                        break;
                    case "Data":
                        Toast.makeText(PatientInformation.this, "Clicke on Patient data tab", Toast.LENGTH_SHORT).show();
                        break;
                }*/


            }
        });

        if(SharedValues.getSelectedPatientId() != null){
            tabHost.getTabWidget().getChildTabViewAt(1).setEnabled(true);
        }else{
            tabHost.getTabWidget().getChildTabViewAt(1).setEnabled(false);
        }

        //Based on click event of menu on action bar, Current tab will invoke
        tabHost.setCurrentTab(getIntent().getIntExtra("info",0));


        //tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.light_blue_circle_logo);


        //tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.light_blue_plus_logo);



    }

    private void setTabbColor(TabHost tabHost) {
        for (int i =0 ; i<tabHost.getTabWidget().getChildCount(); i++){
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FFFFFF"));
           // tabhost.getTabWidget()
        }
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#ade1f4"));
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

         /*Displaying Menu Icon*/
            getMenuInflater().inflate(R.menu.patient_basic_input, menu);
            for (int j = 0; j < menu.size(); j++) {
                MenuItem item = menu.getItem(j);
                if(SharedValues.getSelectedPatientId() != null && SharedValues.getSelectedPatientId().length() > 0) {
                    item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
                }else{
                    item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);
                }
            }
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Tapping on top left ActionBar icon navigates "up" to hierarchical parent screen.
                // The parent is defined in the AndroidManifest entry for this activity via the
                // parentActivityName attribute (and via meta-data tag for OS versions before API
                // Level 16). See the "Tasks and Back Stack" guide for more information:
                // http://developer.android.com/guide/components/tasks-and-back-stack.html
                //NavUtils.navigateUpFromSameTask(this);
                Intent object = new Intent();
                if(SharedValues.getSelectedPatientId() == null){
                    object = new Intent(getApplicationContext(),PatientsListActivity.class);
                }else{
                    object = new Intent(getApplicationContext(),PatientDetailActivity.class);
                }
                startActivity(object);
                finish();
                return true;

            case R.id.chartID:
                if(SharedValues.getSelectedPatientId()!= null) {
                    Intent intent3 = new Intent();
                    intent3.setClass(getApplicationContext(), ChartOptions.class);
                    startActivity(intent3);
                    return true;
                }else{
                    Toast.makeText(this, "Unable to open chart options", Toast.LENGTH_SHORT);
                    return true;
                }


        }
        // Otherwise, pass the item to the super implementation for handling, as described in the
        // documentation.
        return super.onOptionsItemSelected(item);
    }



    public TabHost getTabHost()
    {
        return this.tabHost ;
    }

    @Override
    public void onTabChanged(String tabId) {
      /* switch (tabId){
           case "Patient":
               setTabColor(tabHost,0);
               break;
           case "Data":
               setTabColor(tabHost,1);
               break;
       }*/

    }

    private void setTabColor(TabHost tabHost, int i) {
        switch (i){
            case 0:
                TextView textView = (TextView) tabHost.getTabWidget().getChildTabViewAt(0).findViewById(android.R.id.title);
                textView.setTextColor(Color.parseColor("#303F9F"));
                TextView textView1 = (TextView) tabHost.getTabWidget().getChildTabViewAt(1).findViewById(android.R.id.title);
                textView1.setTextColor(Color.BLACK);
                break;
            case 1:
                TextView textView2 = (TextView) tabHost.getTabWidget().getChildTabViewAt(1).findViewById(android.R.id.title);
                textView2.setTextColor(Color.parseColor("#303F9F"));
                TextView textView3 = (TextView) tabHost.getTabWidget().getChildTabViewAt(0).findViewById(android.R.id.title);
                textView3.setTextColor(Color.BLACK);
        }
    }

    private Boolean exit = false;
//    @Override
//    public void onBackPressed() {
//        if (exit) {
//
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            // finish(); // finish activity
//        } else {
//            Toast.makeText(this, "Press Back again to Exit.",
//                    Toast.LENGTH_SHORT).show();
//            exit = true;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    exit = false;
//                }
//            }, 3 * 1000);
//
//        }
//
//    }

    @Override
    public void onBackPressed() {
        if (exit) {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            // finish(); // finish activity
        } else {
            super.onBackPressed();


        }

    }
}
