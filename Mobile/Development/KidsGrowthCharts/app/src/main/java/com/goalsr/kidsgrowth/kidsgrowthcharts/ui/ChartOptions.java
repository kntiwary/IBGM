package com.goalsr.kidsgrowth.kidsgrowthcharts.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goalsr.kidsgrowth.kidsgrowthcharts.R;
import com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata.ChartXMLDataProvider;
import com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata.ChartXMLDataProviderFiveToEighteen;
import com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata.ChartXMLDataProviderFiveToEighteenBMI;
import com.goalsr.kidsgrowth.kidsgrowthcharts.db.DBFeedReaderContract;
import com.goalsr.kidsgrowth.kidsgrowthcharts.db.DataBaseHelper;
import com.goalsr.kidsgrowth.kidsgrowthcharts.ui.charts.ChartLayout;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.Age;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.AgeCalculator;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.CHMActivity;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.DateUtils;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.SharedValues;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChartOptions extends CHMActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_options);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Button iapFiveToEighteenHeightWeightActionbtn =(Button)findViewById(R.id.button4);
        iapFiveToEighteenHeightWeightActionbtn.setVisibility(View.GONE);

        Button iapFiveToEighteenBMIActionbtn =(Button)findViewById(R.id.button5);
        iapFiveToEighteenBMIActionbtn.setVisibility(View.GONE);

        //System.out.println("CHART OPTIONS SharedValues.getSelectedPatientId(): " + SharedValues.getSelectedPatientId());

        Age age = new Age();
        Date currentDate = new Date();
        Date dateOfBirth = null;
        if(SharedValues.getSelectedPatientDOB() != null) {
              dateOfBirth = DateUtils.stringToDate("dd/MM/yyyy", SharedValues.getSelectedPatientDOB());
        }
       /* System.out.print(currentDate + " currentDate\n ");
        System.out.print(dateOfBirth + " dateOfBirth\n ");
        System.out.println("SharedValues.getSelectedPatientDOB(): " + SharedValues.getSelectedPatientDOB());*/

        try {

              /* ///in milliseconds
                long diff = currentDate.getTime() - dateOfBirth.getTime();

                long diffDays = diff / (24 * 60 * 60 * 1000);

                System.out.print(diffDays + " days, ");*/
            if(dateOfBirth != null) {
                age = AgeCalculator.calculateAge(dateOfBirth, currentDate);
            }
             //System.out.println("age" + age);

        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "Wait.. App is restarting", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent intent = new Intent(getApplicationContext(),CHM_Flash.class);
            startActivity(intent);
            finish();
        }
        //System.out.println("age.getYears()   " + age.getYears());
        if(age.getYears() >= 5){
           // System.out.println("age.getYears()" + age.getYears());
                //weightChart button3
                Button weightChartAction = (Button) findViewById(R.id.weightChart);
                weightChartAction.setVisibility(View.GONE);

                //weightForHeight
                Button weightForHeight = (Button) findViewById(R.id.button3);
                weightForHeight.setVisibility(View.GONE);

                iapFiveToEighteenHeightWeightActionbtn = (Button) findViewById(R.id.button4);
                iapFiveToEighteenHeightWeightActionbtn.setVisibility(View.VISIBLE);

                iapFiveToEighteenBMIActionbtn = (Button) findViewById(R.id.button5);
                iapFiveToEighteenBMIActionbtn.setVisibility(View.VISIBLE);
        } else {

        }

        try {

            //genId.setText(PatientBasicInputs.PID);

            if (SharedValues.getSelectedPatientId() != null) {
                SharedValues.setSelectedPatientId(SharedValues.getSelectedPatientId());
                SharedValues.setSelectedPatientDOB(SharedValues.getSelectedPatientDOB());
                SharedValues.setSelectedPatientName(SharedValues.getSelectedPatientName());
            }
            String pName = "";
            if (SharedValues.getSelectedPatientName() != null && !SharedValues.getSelectedPatientName().isEmpty() && SharedValues.getSelectedPatientName().length() > 0) {
                pName = SharedValues.getSelectedPatientName().toString();
            } else {
                pName = "";
            }
            //System.out.println(pName);
            TextView PatientName = (TextView) findViewById(R.id.pName);
            PatientName.setText("Name : " + pName);
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "Wait.. App is restarting", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent intent = new Intent(getApplicationContext(),CHM_Flash.class);
            startActivity(intent);
            finish();
        }





    }


    //Button in UI Button_One
    //Height Weight Head chart 1
    public void weightChartAction(View view) {

        try {
            clearDataSetsForChart();

        /*
        Intent intent = new Intent() ;
        intent.setClass(getApplicationContext(), weightmonthsactivity.class) ;
        startActivity(intent);
        */

            /*ChartXMLDataProvider.xVals.clear();
            ChartXMLDataProvider.dataSets.clear();
            ChartXMLDataProviderFiveToEighteen.xVals.clear();
            ChartXMLDataProviderFiveToEighteen.dataSets.clear();*/

            String decideChartBasedOnGender = "";
            String decideGenderForChart = "";
            String chartName = "";
            String creditChartText = "";

            if (SharedValues.getSelectedPatientGender().equals("Male")) {
                decideGenderForChart = "WHOBoys";
                decideChartBasedOnGender = "LenghtHeightWeightHeadCircumference";
                chartName = decideGenderForChart +" Length/Height, Weight and Head Circumference Chart";
                creditChartText = "Modified from WHO 2006 MGRS Charts";
            } else {
                decideGenderForChart = "WHOGirls";
                decideChartBasedOnGender = "LenghtHeightWeightHeadCircumference";
                chartName =  decideGenderForChart +" Length/Height, Weight and Head Circumference Chart";
                creditChartText = "Modified from WHO 2006 MGRS Charts";
            }

            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), ChartLayout.class);
            intent.putExtra("genderForChart", decideGenderForChart);
            intent.putExtra("chartType", decideChartBasedOnGender);
            intent.putExtra("chartName", chartName);
            intent.putExtra("creditChartText", creditChartText);

            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage().toString();
            String msg = e.getMessage().toString();
            Toast.makeText(this, "Please go to VISIT DATA and come back", Toast.LENGTH_SHORT).show();
        }

    }

    ////Button in UI Button_Two
    //Weight for Height chart 2
    public void weightForHeightAction(View view) {
        try {
            clearDataSetsForChart();

            String decideChartBasedOnGender = "";
            String decideGenderForChart = "";
            String chartName = "";
            String creditChartText = "";
            if (SharedValues.getSelectedPatientGender().equals("Male")) {
                decideGenderForChart = "WHOBoys";
                decideChartBasedOnGender = "WeightForHeight";
                chartName =  decideGenderForChart +" Weight for Height/Length Chart";
                creditChartText = "Modified from WHO 2006 MGRS Charts";
            } else {
                decideGenderForChart = "WHOGirls";
                decideChartBasedOnGender = "WeightForHeight";
                chartName =  decideGenderForChart +" Weight  For Height/Length Chart";
                creditChartText = "Modified from WHO 2006 MGRS Charts";
            }

            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), ChartLayout.class);
            intent.putExtra("genderForChart", decideGenderForChart);
            intent.putExtra("chartType", decideChartBasedOnGender);
            intent.putExtra("chartName", chartName);
            intent.putExtra("creditChartText", creditChartText);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage().toString();
            String msg = e.getMessage().toString();
            Toast.makeText(this, "Please go to VISIT DATA and come back", Toast.LENGTH_SHORT).show();
        }


    }

    //Button in UI Button_Three
   //IAP 5-18 Height-Weight 3
    public void iapFiveToEighteenHeightWeightAction(View view) {
        try {


            clearDataSetsForChart();

            String decideChartBasedOnGender = "";
            String decideGenderForChart = "";
            String chartName = "";
            String creditChartText = "";

            if (SharedValues.getSelectedPatientGender().equals("Male")) {
                decideGenderForChart = "WHOBoys";
                decideChartBasedOnGender = "IAPHeightWeight";
                chartName =  decideGenderForChart +" WHO 2006 & IAP 2015 Combined Height & Weight Charts";
                creditChartText = "1. WHO 2006 MGRS Charts\n" +
                        "2. Revised IAP Growth Charts for Height, Weight and Body Mass Index for 5 to 18 year old Indian Children    V.Khadlikar et al; from Indian Academy of Pediatrics. Growth Chart Committee. Indian Pediatrics. Jan 2015, Volume 52";
            } else {
                decideGenderForChart = "WHOGirls";
                decideChartBasedOnGender = "IAPHeightWeight";
                chartName =  decideGenderForChart +" WHO 2006 & IAP 2015 Combined Height & Weight Charts";
                creditChartText = "1. WHO 2006 MGRS Charts\n" +
                        "2. Revised IAP Growth Charts for Height, Weight and Body Mass Index for 5 to 18 year old Indian Children    V.Khadlikar et al; from Indian Academy of Pediatrics. Growth Chart Committee. Indian Pediatrics. Jan 2015, Volume 52";
            }

            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), ChartLayout.class);
            intent.putExtra("genderForChart", decideGenderForChart);
            intent.putExtra("chartType", decideChartBasedOnGender);
            intent.putExtra("chartName", chartName);
            intent.putExtra("creditChartText", creditChartText);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage().toString();
            String msg = e.getMessage().toString();
            Toast.makeText(this, "Please go to VISIT DATA tab and come back", Toast.LENGTH_SHORT).show();

        }

    }

    //Button in UI Button_Four
    //chart number 4
    public void iapZeroToEighteenHeightWeightAction(View view) {
        try {


            clearDataSetsForChart();

            String decideChartBasedOnGender = "";
            String decideGenderForChart = "";
            String chartName = "";
            String creditChartText = "";

            if (SharedValues.getSelectedPatientGender().equals("Male")) {
                decideGenderForChart = "WHOBoys";
                decideChartBasedOnGender = "IAPHeightWeight0_18";
                chartName =  decideGenderForChart +" WHO 2006 & IAP 2015 Combined Height & Weight Chart";
                creditChartText = "1. WHO 2006 MGRS Charts\n" +
                        "2. Revised IAP Growth Charts for Height, Weight and Body Mass Index for 0 to 18 year old Indian Children    V.Khadlikar et al; from Indian Academy of Pediatrics. Growth Chart Committee. Indian Pediatrics. Jan 2015, Volume 52";
            } else {
                decideGenderForChart = "WHOGirls";
                decideChartBasedOnGender = "IAPHeightWeight0_18";
                chartName =  decideGenderForChart +" WHO 2006 & IAP 2015 Combined Height & Weight Chart";
                creditChartText = "1. WHO 2006 MGRS Charts\n" +
                        "2. Revised IAP Growth Charts for Height, Weight and Body Mass Index for 0 to 18 year old Indian Children    V.Khadlikar et al; from Indian Academy of Pediatrics. Growth Chart Committee. Indian Pediatrics. Jan 2015, Volume 52";
            }

            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), ChartLayout.class);
            intent.putExtra("genderForChart", decideGenderForChart);
            intent.putExtra("chartType", decideChartBasedOnGender);
            intent.putExtra("chartName", chartName);
            intent.putExtra("creditChartText", creditChartText);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage().toString();
            String msg = e.getMessage().toString();
            Toast.makeText(this, "Please go to VISIT DATA tab and come back", Toast.LENGTH_SHORT).show();

        }

    }

    //Button in UI Button_Five
    //IAP 5-18 BMI 5
    public void iapFiveToEighteenBMIAction(View view) {
        try {


            clearDataSetsForChart();

            String decideChartBasedOnGender = "";
            String decideGenderForChart = "";
            String chartName ="";
            String creditChartText = "";

            if (SharedValues.getSelectedPatientGender().equals("Male")) {
                decideGenderForChart = "WHOBoys";
                decideChartBasedOnGender = "IAPBMI";
                chartName =  decideGenderForChart +" IAP Body Mass Index (BMI) Chart 2015";
                creditChartText = "Revised IAP Growth Charts for Height, Weight and Body Mass Index for 5 to 18 year old Indian Children V.Khadlikar et al; from Indian Academy of Pediatrics. Growth Chart Committee. Indian Pediatrics. Jan 2015, Volume 52";
            } else {
                decideGenderForChart = "WHOGirls";
                decideChartBasedOnGender = "IAPBMI";
                chartName =  decideGenderForChart +" IAP Body Mass Index (BMI) Chart 2015";
                creditChartText = "Revised IAP Growth Charts for Height, Weight and Body Mass Index for 5 to 18 year old Indian Children V.Khadlikar et al; from Indian Academy of Pediatrics. Growth Chart Committee. Indian Pediatrics. Jan 2015, Volume 52";
            }

            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), ChartLayout.class);
            intent.putExtra("genderForChart", decideGenderForChart);
            intent.putExtra("chartType", decideChartBasedOnGender);
            intent.putExtra("chartName", chartName);
            intent.putExtra("creditChartText", creditChartText);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage().toString();
            String msg = e.getMessage().toString();
            Toast.makeText(this, "Please go to VISIT DATA and come back", Toast.LENGTH_SHORT).show();
        }

    }

    //Button in UI Button_Six
    //IAP 5-18 BMI 6
    public void iapZeroToEighteenBMIAction(View view) {
        try {
            clearDataSetsForChart();
            String decideChartBasedOnGender = "";
            String decideGenderForChart = "";
            String chartName ="";
            String creditChartText = "";

            if (SharedValues.getSelectedPatientGender().equals("Male")) {
                decideGenderForChart = "WHOBoys";
                decideChartBasedOnGender = "BMI_0_to_18";
                chartName =  decideGenderForChart +" IAP Body Mass Index (BMI) Chart 2015 ";
                creditChartText = "Revised IAP Growth Charts for Height, Weight and Body Mass Index for 0 to 18 year old Indian Children V.Khadlikar et al; from Indian Academy of Pediatrics. Growth Chart Committee. Indian Pediatrics. Jan 2015, Volume 52";
            } else {
                decideGenderForChart = "WHOGirls";
                decideChartBasedOnGender = "BMI_0_to_18";
                chartName =  decideGenderForChart +" IAP Body Mass Index (BMI) Chart 2015 ";
                creditChartText = "Revised IAP Growth Charts for Height, Weight and Body Mass Index for 0 to 18 year old Indian Children V.Khadlikar et al; from Indian Academy of Pediatrics. Growth Chart Committee. Indian Pediatrics. Jan 2015, Volume 52";
            }

            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), ChartLayout.class);
            intent.putExtra("genderForChart", decideGenderForChart);
            intent.putExtra("chartType", decideChartBasedOnGender);
            intent.putExtra("chartName", chartName);
            intent.putExtra("creditChartText", creditChartText);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage().toString();
            String msg = e.getMessage().toString();
            Toast.makeText(this, "Please go to VISIT DATA and come back", Toast.LENGTH_SHORT).show();
        }

    }

    private void clearDataSetsForChart() {
        ChartXMLDataProvider.xVals.clear();
        ChartXMLDataProvider.dataSets.clear();
        ChartXMLDataProviderFiveToEighteen.xVals.clear();
        ChartXMLDataProviderFiveToEighteen.dataSets.clear();
        ChartXMLDataProviderFiveToEighteenBMI.xVals.clear();
        ChartXMLDataProviderFiveToEighteenBMI.dataSets.clear();
    }


    /*Menu Option and Home Button*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

         /*Displaying Menu Icon*/
        // getMenuInflater().inflate(R.menu.charts_main, menu);
        /*for (int j = 0; j < menu.size(); j++) {
            MenuItem item = menu.getItem(j);
            item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
      */


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {

            /*String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

            String image_name = "CHART_" + timeStamp + ".jpg";
            mChart.saveToGallery(image_name, 90);
            return true;*/
            Intent i = new Intent(getApplicationContext(), OptionActivity.class);
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
//            Toast.makeText(this, "Press Back again to Exit.",
//                    Toast.LENGTH_SHORT).show();
//            exit = true;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    exit = false;
//                }
//            }, 3 * 1000);
            super.onBackPressed();

        }

    }

}
