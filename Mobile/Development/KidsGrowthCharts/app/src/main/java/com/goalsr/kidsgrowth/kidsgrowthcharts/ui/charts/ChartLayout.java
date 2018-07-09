package com.goalsr.kidsgrowth.kidsgrowthcharts.ui.charts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.goalsr.kidsgrowth.kidsgrowthcharts.R;
import com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata.ChartXMLDataProvider;
import com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata.ChartXMLDataProviderFiveToEighteen;
import com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata.ChartXMLDataProviderFiveToEighteenBMI;
import com.goalsr.kidsgrowth.kidsgrowthcharts.db.DBFeedReaderContract;
import com.goalsr.kidsgrowth.kidsgrowthcharts.db.DataBaseHelper;
import com.goalsr.kidsgrowth.kidsgrowthcharts.db.ModelGraphData;
import com.goalsr.kidsgrowth.kidsgrowthcharts.ui.CHM_Flash;
import com.goalsr.kidsgrowth.kidsgrowthcharts.ui.ChartOptions;
import com.goalsr.kidsgrowth.kidsgrowthcharts.ui.PatientDetailActivity;
import com.goalsr.kidsgrowth.kidsgrowthcharts.ui.PatientsListActivity;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.CHMActivity;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.SharedValues;

import java.io.File;
import java.io.FileOutputStream;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ChartLayout extends CHMActivity implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener, Comparator<ModelGraphData> {

    private LineChart mChart;
    private String chartGender = "";
    private String chartType = "";
    private String chartName = "";
    private String creditChartText = "";
    String pName = "";
    String pDob = "";
    private TextView textView, textView1, creditText;
    RelativeLayout chartLayout;

    private List<ModelGraphData> modelGraphDatas = new ArrayList<>();
    private String patientReferenceHeight = " Reference Weight not found";
    private String patientReferenceWeight = "Reference Height not found";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chart_layout);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String ref_weight = sharedPreferences.getString("referenceWeight", patientReferenceHeight);

        SharedPreferences sharedPreferences1 = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String ref_height = sharedPreferences1.getString("ref_Height", patientReferenceWeight);


        try {


            if (!SharedValues.getSelectedPatientName().isEmpty() || SharedValues.getSelectedPatientName().length() > 0) {
                pName = SharedValues.getSelectedPatientName().toString();
                pDob = SharedValues.getSelectedPatientDOB().toString();
                /*textView = (TextView)findViewById(R.id.textName);
                textView.setText("NAME:"+pName);*/
               /* textView1 = (TextView)findViewById(R.id.textDob);
                textView1.setText("DOB:"+pDob);
                textView.setTextColor(Color.BLACK);
                textView1.setTextColor(Color.BLACK);*/


            } else {
                pName = "";
            }
            // pName ="MAHESH";

            // TextView PatientName = (TextView) findViewById(R.id.patientName);
            // PatientName.setText("Name : "+pName);
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "Wait.. App is restarting", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent intent = new Intent(getApplicationContext(), CHM_Flash.class);
            startActivity(intent);
            finish();
        }


        //LenghtHeightWeightHeadCircumference chart 1
        Intent intent = getIntent();
        chartGender = intent.getStringExtra("genderForChart");
        chartType = intent.getStringExtra("chartType");
        chartName = intent.getStringExtra("chartName");
        creditChartText = intent.getStringExtra("creditChartText");
        // System.out.println("chartGender" + chartGender);
        // System.out.println("chartType" + chartType);
        //System.out.println("chartName" + chartName);

        TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewTitle.setText(chartName.toString());
        textViewTitle.setGravity(View.TEXT_ALIGNMENT_CENTER);
        creditText = (TextView) findViewById(R.id.creditText);
        creditText.setText("*Height:" + " " + ref_height + "\n" + "*Weight:" + " " + ref_weight + "\n" + creditChartText.toString());
        creditText.setGravity(View.TEXT_ALIGNMENT_CENTER);

        /*LinearLayout linearLayout = (LinearLayout)findViewById(R.id.chart);
        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
        if(chartType.equals("LenghtHeightWeightHeadCircumference") || chartType.equals("WeightForHeight")){
            params.height = 950;
        }else if(chartType.equals("IAPHeightWeight") || chartType.equals("IAPHeightWeight0_18") || chartType.equals("IAPBMI")){
            params.height = 850;
        }else if(chartType.equals("BMI_0_to_18")){
            params.height = 915;
        }else{
            params.height = 875;
        }*/

        chartLayout = (RelativeLayout) findViewById(R.id.chartRelativeLayout);
        if (chartGender.equals("WHOBoys")) {
            chartLayout.setBackgroundColor(getResources().getColor(R.color.skyblue));
        } else {
            chartLayout.setBackgroundColor(getResources().getColor(R.color.pink));
        }

        //ChartXMLDataProvider chartXMLDataProvider = new ChartXMLDataProvider(getApplicationContext(), "Male", "0to5YearsBoysLenghtHeightWeightHeadCircumference") ;
        if (chartType.equals("IAPHeightWeight")) {
            ChartXMLDataProviderFiveToEighteen chartXMLDataProviderFiveToEighteen = new ChartXMLDataProviderFiveToEighteen(getApplicationContext(), chartGender, chartType);
        } else if (chartType.equals("IAPHeightWeight0_18")) {
            ChartXMLDataProviderFiveToEighteen chartXMLDataProviderFiveToEighteen = new ChartXMLDataProviderFiveToEighteen(getApplicationContext(), chartGender, chartType);
        } else if (chartType.contains("IAPBMI")) {
            ChartXMLDataProviderFiveToEighteenBMI chartXMLDataProviderFiveToEighteenbmi = new ChartXMLDataProviderFiveToEighteenBMI(getApplicationContext(), chartGender, chartType);
        } else if (chartType.contains("BMI_0_to_18")) {
            ChartXMLDataProviderFiveToEighteenBMI chartXMLDataProviderFiveToEighteenbmi = new ChartXMLDataProviderFiveToEighteenBMI(getApplicationContext(), chartGender, chartType);
        } else {
            ChartXMLDataProvider chartXMLDataProvider = new ChartXMLDataProvider(getApplicationContext(), chartGender, chartType);
        }

        mChart = (LineChart) findViewById(R.id.kchart1);
        //mChart.setOnChartValueSelectedListener(this);

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");


        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(true);
        mChart.setHighlightPerDragEnabled(true);


        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        // set an alternative background color
        mChart.setBackgroundColor(Color.LTGRAY);


        // add data
        setData(36, 1);

        mChart.animateX(1);

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setTypeface(tf);
        l.setTextSize(9f);
        l.setTextColor(Color.DKGRAY);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setWordWrapEnabled(true);

//        l.setYOffset(11f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(tf);
        xAxis.setTextSize(2f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);
        xAxis.isAxisModulusCustom();
        // xAxis.setSpaceBetweenLabels(2);
        // xAxis.setLabelsToSkip(2);
        // xAxis.setLabelsToSkip(0);
        //xAxis.setLabelsToSkip(0);
        xAxis.setLabelRotationAngle(-90);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        if (chartType.equals("LenghtHeightWeightHeadCircumference")) {
            leftAxis.setAxisMaxValue(140);
            leftAxis.setAxisMinValue(0);
            leftAxis.mAxisRange = 5;
            leftAxis.setLabelCount(28, true);
        } else if (chartType.equals("IAPHeightWeight")) {
            leftAxis.setAxisMaxValue(200);
            leftAxis.setAxisMinValue(0);
            leftAxis.mAxisRange = 5;
            leftAxis.setLabelCount(40, true);
        } else if (chartType.equals("IAPHeightWeight0_18")) {
            leftAxis.setAxisMaxValue(200);
            leftAxis.setAxisMinValue(0);
            leftAxis.mAxisRange = 5;
            leftAxis.setLabelCount(40, true);
        } else if (chartType.equals("IAPBMI")) {
            leftAxis.setAxisMaxValue(35);
            leftAxis.setAxisMinValue(0);
            leftAxis.mAxisRange = 1;
            leftAxis.setLabelCount(35, true);
        } else if (chartType.equals("BMI_0_to_18")) {
            leftAxis.setAxisMaxValue(35);
            leftAxis.setAxisMinValue(0);
            leftAxis.mAxisRange = 1;
            leftAxis.setLabelCount(35, true);
        } else {
            leftAxis.setAxisMaxValue(27);
            leftAxis.setAxisMinValue(0);
            leftAxis.mAxisRange = 1;
        }
        leftAxis.setStartAtZero(true);
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawTopYLabelEntry(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setTypeface(tf);
        rightAxis.setTextColor(Color.RED);

        LimitLine ll = new LimitLine(mChart.getLineData().getYMax(), "Threshold Value");
        ll.setLineColor(Color.MAGENTA);
        ll.setLineWidth(1f);
        ll.setTextColor(Color.RED);
        ll.setTextSize(7f);

        /*DONT DELETE for FUTURE REFERENCE*************************************
        int dcount = mChart.getLineData().getDataSetCount() ;
        float maxcnt = ChartXMLDataProviderFiveToEighteenBMI.dataSets.get(0).getYMax() ;

        LimitLine[] allpoints = new LimitLine[mChart.getLineData().getDataSetCount()] ;
        String[] textToShow = {"3", "5", "10", "25", "50","23 Adult Equivalent (Overweight)","27 Adult equivalent(Obese)"} ;

        for (int i=0; i < mChart.getLineData().getDataSetCount(); i++)
        {
            allpoints[i] = new LimitLine(mChart.getLineData().getDataSetByIndex(i).getYMax(), textToShow[i]) ;
            allpoints[i].setLineColor(Color.MAGENTA);
            allpoints[i].setLineWidth(1f);
            allpoints[i].setTextColor(Color.RED);
            allpoints[i].setTextSize(9f);
            rightAxis.addLimitLine(allpoints[i]);
        }
        */

        if (chartType.equals("LenghtHeightWeightHeadCircumference")) {
            rightAxis.setAxisMaxValue(140);
            rightAxis.setStartAtZero(false);
            rightAxis.setAxisMinValue(0);
            rightAxis.setLabelCount(28, true);
            rightAxis.mAxisRange = 5;

            //commented on 14-01-2016
            LimitLine[] allpoints = new LimitLine[mChart.getLineData().getDataSetCount()];
            String[] textToShow = {"1 (-3)", "3 (-2)", "50 (0)", "97(+2)", "3 (-2)", "50(0)", "97 (+2)", "1 (-3)", "3 (-2)", "50 (0)", "97 (+2)"};

            for (int i = 0; i < 11; i++) {
                allpoints[i] = new LimitLine(mChart.getLineData().getDataSetByIndex(i).getYMax(), textToShow[i]);
                allpoints[i].setLineColor(Color.TRANSPARENT);
                allpoints[i].setLineWidth(1f);
                allpoints[i].setTextColor(Color.RED);
                allpoints[i].setTextSize(9f);
                rightAxis.addLimitLine(allpoints[i]);
                //i = 100;
            }


        } else if (chartType.equals("IAPHeightWeight")) {
            rightAxis.setAxisMaxValue(200);
            rightAxis.setAxisMinValue(0);
            rightAxis.mAxisRange = 5;
            rightAxis.setLabelCount(40, true);
           /* // THIS IS FOR DISPLAY OF TEXT for the GRAPH commented on 14-01-2016
            LimitLine[] allpoints = new LimitLine[mChart.getLineData().getDataSetCount()];
            String[] textToShow = {"", "", "", "Weight:" + ref_weight, "Height:" + ref_height};
            //Toast.makeText(this, "Hiiiiii"+SharedValues.getReferenceWeight()Weight, ref_heightToast.LENGTH_SHORT).show();

            for (int i = 0; i < 5; i++) {
                allpoints[i] = new LimitLine(mChart.getLineData().getDataSetByIndex(i).getYMax(), textToShow[i]);
                allpoints[i].setLineColor(Color.TRANSPARENT);
                allpoints[i].setLineWidth(1f);
                allpoints[i].setTextColor(Color.RED);
                allpoints[i].setTextSize(9f);
                rightAxis.addLimitLine(allpoints[i]);
            }*/
            //END OF GRAPH TEXT DISPLAY


        } else if (chartType.equals("IAPHeightWeight0_18")) {
            rightAxis.setAxisMaxValue(200);
            rightAxis.setAxisMinValue(0);
            rightAxis.mAxisRange = 5;
            rightAxis.setLabelCount(40, true);

          /*  // THIS IS FOR DISPLAY OF TEXT for the GRAPH commented on 14-01-2016
            LimitLine[] allpoints = new LimitLine[mChart.getLineData().getDataSetCount()];
            String[] textToShow = {"", "", "", "Weight:" + ref_weight, "Height:" + ref_height};
            //Toast.makeText(this, "Hiiiiii"+SharedValues.getReferenceWeight(), Toast.LENGTH_SHORT).show();

            for (int i = 0; i < 5; i++) {
                allpoints[i] = new LimitLine(mChart.getLineData().getDataSetByIndex(i).getYMax(), textToShow[i]);
                allpoints[i].setLineColor(Color.TRANSPARENT);
                allpoints[i].setLineWidth(1f);
                allpoints[i].setTextColor(Color.RED);
                allpoints[i].setTextSize(9f);
                rightAxis.addLimitLine(allpoints[i]);
            }*/
            //END OF GRAPH TEXT DISPLAY


        } else if (chartType.equals("IAPBMI")) {
            rightAxis.setAxisMaxValue(35);
            rightAxis.setAxisMinValue(0);
            rightAxis.mAxisRange = 1;
            rightAxis.setLabelCount(35, true);
            creditText.setText(creditChartText.toString());

            int dcount = mChart.getLineData().getDataSetCount();
            float maxcnt = ChartXMLDataProviderFiveToEighteenBMI.dataSets.get(0).getYMax();

            // THIS IS FOR DISPLAY OF TEXT for the GRAPH commented on 14-01-2016
            LimitLine[] allpoints = new LimitLine[mChart.getLineData().getDataSetCount()];
            String[] textToShow = {"3", "5", "10", "25", "50", "23 Adult Equivalent (Overweight)", "27 Adult equivalent(Obese)", "P-BMI"};

            for (int i = 0; i < mChart.getLineData().getDataSetCount(); i++) {
                allpoints[i] = new LimitLine(mChart.getLineData().getDataSetByIndex(i).getYMax(), textToShow[i]);
                allpoints[i].setLineColor(Color.TRANSPARENT);
                allpoints[i].setLineWidth(1f);
                allpoints[i].setTextColor(Color.RED);
                allpoints[i].setTextSize(9f);
                rightAxis.addLimitLine(allpoints[i]);
            }
            //END OF GRAPH TEXT DISPLAY

        } else if (chartType.equals("BMI_0_to_18")) {
            rightAxis.setAxisMaxValue(35);
            rightAxis.setAxisMinValue(0);
            rightAxis.mAxisRange = 1;
            rightAxis.setLabelCount(35, true);
            //creditText.setText("*Height:" + " " + ref_height + "\n" + "*Weight:" + " " + ref_weight + "\n" + creditChartText.toString());
            creditText.setText(creditChartText.toString());
            int dcount = mChart.getLineData().getDataSetCount();
            float maxcnt = ChartXMLDataProviderFiveToEighteenBMI.dataSets.get(0).getYMax();
            System.out.println("d_count....." + dcount);
            System.out.println("max_count....." + maxcnt);


            // THIS IS FOR DISPLAY OF TEXT for the GRAPH commented on 14-01-2016
            LimitLine[] allpoints = new LimitLine[mChart.getLineData().getDataSetCount()];
            String[] textToShow = {"3", "5", "10", "25", "50", "23 Adult Equivalent (Overweight)", "27 Adult equivalent(Obese)", "P-BMI"};

            for (int i = 0; i < mChart.getLineData().getDataSetCount(); i++) {
                allpoints[i] = new LimitLine(mChart.getLineData().getDataSetByIndex(i).getYMax(), textToShow[i]);
                allpoints[i].setLineColor(Color.TRANSPARENT);
                allpoints[i].setLineWidth(1f);
                allpoints[i].setTextColor(Color.RED);
                allpoints[i].setTextSize(9f);
                rightAxis.addLimitLine(allpoints[i]);
            }
            //END OF GRAPH TEXT DISPLAY

        } else {
            rightAxis.setAxisMaxValue(27);
            rightAxis.setAxisMinValue(0);
            rightAxis.mAxisRange = 1;

        }

        rightAxis.setStartAtZero(true);
        rightAxis.setDrawGridLines(true);
        rightAxis.setDrawAxisLine(true);

        //To disable display of LEGENDS at the bottom.
        mChart.getLegend().setEnabled(false);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        //tvX.setText("" + (mSeekBarX.getProgress() + 1));
        //tvY.setText("" + (mSeekBarY.getProgress()));

        //setData(mSeekBarX.getProgress() + 1, mSeekBarY.getProgress());

        // redraw
        mChart.invalidate();
    }

    private void setData(float count, float range) {

        //System.out.println("setData \n SharedValues.PID: "+SharedValues.getSelectedPatientId());
        // System.out.println("setData \n SharedValues.Name: " + SharedValues.getSelectedPatientName());

        // System.out.println("setData \n SharedValues.MidParental Height: " + SharedValues.getMidParentalHeight());

        SQLiteDatabase db = DataBaseHelper.myDBHelper.getReadableDatabase();
        //int visitRecordsCount = 0;
        Cursor pCursor = db.query(DBFeedReaderContract.FeedEntryVisits.TABLE_NAME, null, DBFeedReaderContract.FeedEntryVisits.V_PATIENT_UUID + "=" + SharedValues.getSelectedPatientId(), null, null, null, DBFeedReaderContract.FeedEntryVisits.V_ID + " ASC", null);

        pCursor.moveToFirst();
        int visitRecordsCount = pCursor.getCount();

        int patientCurrentAge = 0;
        float[] patientHeight = new float[visitRecordsCount];
        float[] patientWeight = new float[visitRecordsCount];
        float[] patientBMI = new float[visitRecordsCount];
        float[] patientHeadCircumference = new float[visitRecordsCount];
        int[] patientAge = new int[visitRecordsCount];
        int[] patientAgeMonth = new int[visitRecordsCount];
        int ci = 0;
        while (!pCursor.isAfterLast()) {
            //Shrishial
            ModelGraphData modelGraphData = new ModelGraphData();
            modelGraphData.setPatientModelHeight(pCursor.getFloat(4)); //getting patient height
            modelGraphData.setPatientModelWeight(pCursor.getFloat(5)); //getting patient weight
            modelGraphData.setPatientModelHeadCircumference(pCursor.getFloat(6)); //getting patient HeadCircumference
            modelGraphData.setPatientModelBMI(pCursor.getFloat(7));//getting patient BMI
            modelGraphData.setPatientModelAge(pCursor.getFloat(8)); //getting patient Age
            modelGraphData.setPatientModelAgeMonth(pCursor.getInt(9)); //getting patient AgeMonth
            modelGraphData.setPatientVisitedCount(pCursor.getCount()); //getting patient visitedCount
            modelGraphDatas.add(modelGraphData);
            modelGraphDatas.size();
            System.out.println("modelGraphDatas.size()..." + modelGraphDatas.size());

            //Shrishial ends


            patientHeight[ci] = pCursor.getFloat(4);
            patientWeight[ci] = pCursor.getFloat(5);
            patientHeadCircumference[ci] = pCursor.getFloat(6);
            patientBMI[ci] = pCursor.getFloat(7);
            patientAge[ci] = pCursor.getInt(8);
            patientAgeMonth[ci] = pCursor.getInt(9);
            patientCurrentAge = pCursor.getInt(8);
            ci++;
            pCursor.moveToNext();
        }
        pCursor.close();
        //System.out.println("patientCurrentAge " + patientCurrentAge);

        System.out.println("before ...Comapred Arraya" + modelGraphDatas);
        Collections.sort(modelGraphDatas, new Comparator<ModelGraphData>() {
            @Override
            public int compare(ModelGraphData lhs, ModelGraphData rhs) {
                int lhsAge = lhs.getPatientModelAgeMonth();
                int rhsAge = rhs.getPatientModelAgeMonth();
                return lhsAge - rhsAge;
            }
        });
        System.out.println("Comapred Arraya" + modelGraphDatas);

        if (chartType.equals("IAPHeightWeight")) {

            //IAPHeightWeight 5-18 year chart
            // System.out.println(" IAPHeight patientCurrentAge "+patientCurrentAge );
            // float userMonth = (((monthReminder / 12) * 4) + decimalReminderMonth * 4);

            ArrayList<Entry> yValsW10 = new ArrayList<Entry>();
            ArrayList<Entry> yValsH11 = new ArrayList<Entry>();
            ArrayList<Entry> yValsH12 = new ArrayList<Entry>();
            float mpIndex = 0;

            for (int i = 0; i < modelGraphDatas.size(); i++) {
                float age = modelGraphDatas.get(i).getPatientModelAge();
                int month = modelGraphDatas.get(i).getPatientModelAgeMonth();
                float quotientValue = (month / 12) * 4;
                float temp = (month % 12);
                float temp1 = temp / 12;

                float reminderValue = temp1 * 4;
                float finalIndex = quotientValue + reminderValue;

                float ft = (float) modelGraphDatas.get(i).getPatientModelWeight();
                if(ft!=0){
                    yValsW10.add(new Entry(ft, ((int) finalIndex - 20)));
                }else {
                    System.out.println("Weight is not entered by doctor");
                }

                float ft2 = (float) modelGraphDatas.get(i).getPatientModelHeight();
                if (ft2!=0){
                    yValsH11.add(new Entry(ft2, ((int) finalIndex - 20)));
                }else {
                    System.out.println("Height is not entered by doctor");
                }

                mpIndex= finalIndex;
            }

            long ft3 = SharedValues.getMidParentalHeight();
            if(ft3!=0){
                yValsH12.add(new Entry(ft3, ((int)mpIndex - 20)));
            }else {
                System.out.println("Mid parental height is empty");
            }


           /* *//*Mid Parental Height*//*

            int k = 0;
            int k_index = 0;
            for (int i = 5; i < 19; i++) {
                for (int mn = 0; mn < visitRecordsCount; mn++) {
                    int age = (patientAgeMonth[mn] / 12);
                    int months = patientAgeMonth[mn];
                    float monthsRem = months % 12;
                    float decimalMonths = monthsRem / 12;
                    k = (months / 12) + Math.round(decimalMonths);
                    k_index = ((months / 12) * 2) + Math.round(decimalMonths);

                    if (i == (int) (patientAgeMonth[mn]) / 12 || i == 18) {
                        // float ft = (float) patientWeight[mn];
                        //yValsW10.add(new Entry(ft, (k_index - 10)));

                        // float ft2 = (float) patientHeight[mn];
                        //yValsH11.add(new Entry(ft2, (k_index - 10)));
                        *//*if (i == 18) {
                            long ft3 = SharedValues.getMidParentalHeight();
                            yValsH12.add(new Entry(ft3, (i - 5))); // MPI HIEGHT
                        }*//*
                        k++;
                    }
                }

            }*/


            LineDataSet setW10 = new LineDataSet(yValsW10, "P-W");
            setW10.setAxisDependency(YAxis.AxisDependency.LEFT);
            setW10.setColor(Color.BLACK);
            setW10.setLineWidth(3f);
            setW10.setDrawValues(true);
            setW10.setDrawCircles(true);
            setW10.setDrawFilled(true);
            setW10.setDrawHighlightIndicators(true);
            setW10.setHighlightEnabled(true);
            setW10.setLabel("P-W");
            setW10.setDrawHighlightIndicators(true);
            setW10.setCircleColor(Color.RED);


            LineDataSet setH11 = new LineDataSet(yValsH11, "P-H");
            setH11.setAxisDependency(YAxis.AxisDependency.LEFT);
            setH11.setColor(Color.BLACK);
            setH11.setLineWidth(3f);
            setH11.setDrawValues(true);
            setH11.setDrawCircles(true);
            setH11.setDrawFilled(true);
            setH11.setDrawHighlightIndicators(true);
            setH11.setHighlightEnabled(true);
            setH11.setLabel("P-H");
            setH11.setDrawHighlightIndicators(true);
            setH11.setCircleColor(Color.RED);

            LineDataSet setH12 = new LineDataSet(yValsH12, "MPI-H"); // MPI HIEGHT
            setH12.setAxisDependency(YAxis.AxisDependency.LEFT);
            setH12.setColor(Color.BLUE);
            setH12.setLineWidth(2f);
            setH12.setDrawValues(true);
            setH12.setDrawCircles(true);
            setH12.setDrawFilled(false);
            setH12.setDrawHighlightIndicators(true);
            setH12.setHighlightEnabled(true);
            setH12.setLabel("MPI-H");
            setH12.setDrawHighlightIndicators(true);

            ChartXMLDataProviderFiveToEighteen.dataSets.add(setW10);
            ChartXMLDataProviderFiveToEighteen.dataSets.add(setH11);
            ChartXMLDataProviderFiveToEighteen.dataSets.add(setH12);

            LineData data = new LineData(ChartXMLDataProviderFiveToEighteen.xVals, ChartXMLDataProviderFiveToEighteen.dataSets);

            data.setDrawValues(false);


            // set data
            mChart.setData(data);
            mChart.invalidate();
        } else if (chartType.contains("IAPHeightWeight0_18")) {
            System.out.println(" IAPHeightWeight0_18  patientCurrentAge " + patientCurrentAge);
            ArrayList<Entry> yValsW10 = new ArrayList<Entry>();
            ArrayList<Entry> yValsH11 = new ArrayList<Entry>();
            ArrayList<Entry> yValsH12 = new ArrayList<Entry>();

            float mpIndex=  0;

            for (int i = 0; i < modelGraphDatas.size(); i++) {
                float age = modelGraphDatas.get(i).getPatientModelAge();
                int month = modelGraphDatas.get(i).getPatientModelAgeMonth();//(int) (age) * 12 + (int) (age % 12);
                //float monthReminder = month % 12;
                //float decimalReminderMonth = monthReminder / 12;
                //float userMonth = (((monthReminder / 12) * 4) + decimalReminderMonth * 4);
                float qoutientValue = (month / 12) * 4;
                float temp = (month % 12);
                float temp1 = temp / 12;

                float reminderValue = temp1 * 4;
                float finalIndex = qoutientValue + reminderValue;

                float ft = (float) modelGraphDatas.get(i).getPatientModelWeight();
                if (ft!=0){
                    yValsW10.add(new Entry(ft, ((int) finalIndex)));
                }else {
                    System.out.println("Weight is not entered by doctor");
                }

                float ft2 = (float) modelGraphDatas.get(i).getPatientModelHeight();
                if(ft2!=0){
                    yValsH11.add(new Entry(ft2, ((int) finalIndex)));
                }else {
                    System.out.println("Height is not entered by doctor");
                }


                mpIndex = finalIndex;


            }
            long ft3 = SharedValues.getMidParentalHeight();
            if (ft3!=0){
                yValsH12.add(new Entry(ft3, (int)mpIndex)); // MPI HIEGHT
            }else {
                System.out.println("Mid parental height is empty");
            }


            /*Mid parental height*/

            /*int k = 0;
            int k_index = 0;
            for (int i = 0; i < 19; i++) {
                for (int mn = 0; mn < visitRecordsCount; mn++) {

                    int age = (patientAgeMonth[mn] / 12);
                    int months = patientAgeMonth[mn];
                    float monthsRem = months % 12;
                    float decimalMonths = monthsRem / 12;
                    k = (months / 12) + Math.round(decimalMonths);
                    k_index = ((months / 12) * 2) + Math.round(decimalMonths);


                    if (i == k_index || i == 18) {
                        //float ft = (float) patientWeight[mn];
                        // yValsW10.add(new Entry(ft, k_index));

                        // float ft2 = (float) patientHeight[mn];
                        //  yValsH11.add(new Entry(ft2, k_index));
                        *//*if (i == 18) {
                            long ft3 = SharedValues.getMidParentalHeight();
                            yValsH12.add(new Entry(ft3, i)); // MPI HIEGHT
                        }
                        k++;*//*

                    }
                }

            }
*/


            /*old logic*/
            /*for (int mn = 0; mn < visitRecordsCount; mn++) {

                int age = (patientAgeMonth[mn] / 12);
                Arrays.sort(patientAgeMonth);
                int months = patientAgeMonth[mn];
                float monthsRem = months % 12;
                float decimalMonths = monthsRem / 12;
               /* for(int myArray : patientAgeMonth){
                    Toast.makeText(this, "Sorted Array is"+myArray, Toast.LENGTH_SHORT).show();
                }*/
                /*float muserMonth = months / 12;
                float myUserMonth = muserMonth * 4;
                float mdecimalMonth = decimalMonths * 4;
                float finUserMonth = myUserMonth + mdecimalMonth;*/
            //float userMonth = (((months / 12) * 4) + decimalMonths * 4);
            //System.out.println("User month is..");
               /* k = (months / 12) + Math.round(decimalMonths);
                k_index = ((months / 12) * 4) + Math.round(decimalMonths);
                if (decimalMonths > 0) {
                    k_index++;
                }*/

                   /* if(decimalMonths == 0.75){
                        k_index = k_index+1;
                    }*/

                   /* if(monthsRem==9.0 || monthsRem==1.9 || monthsRem==2.9){
                        k_index = k_index+1;
                    }*/

            // if (i == k_index || i == 18 ) {

            //Patient weight
            //float ft = (float) patientWeight[mn];
            //yValsW10.add(new Entry(ft, (int) userMonth));

            //Patient height
            //float ft2 = (float) patientHeight[mn];
            //yValsH11.add(new Entry(ft2, (int) userMonth));
                       /* if (i == 18) {
                            long ft3 = SharedValues.getMidParentalHeight();
                            yValsH12.add(new Entry(ft3, i)); // MPI HIEGHT
                        }*/
            //k++;


            //}


            LineDataSet setW10 = new LineDataSet(yValsW10, "P-W");
            setW10.setAxisDependency(YAxis.AxisDependency.LEFT);
            setW10.setColor(Color.BLACK);
            setW10.setLineWidth(3f);
            setW10.setDrawValues(true);
            setW10.setDrawCircles(true);
            setW10.setDrawFilled(true);
            setW10.setDrawHighlightIndicators(true);
            setW10.setHighlightEnabled(true);
            setW10.setLabel("P-W");
            setW10.setDrawHighlightIndicators(true);
            setW10.setCircleColor(Color.RED);

            LineDataSet setH11 = new LineDataSet(yValsH11, "P-H");
            setH11.setAxisDependency(YAxis.AxisDependency.LEFT);
            setH11.setColor(Color.BLACK);
            setH11.setLineWidth(3f);
            setH11.setDrawValues(true);
            setH11.setDrawCircles(true);
            setH11.setDrawFilled(true);
            setH11.setDrawHighlightIndicators(true);
            setH11.setHighlightEnabled(true);
            setH11.setLabel("P-H");
            setH11.setDrawHighlightIndicators(true);
            setH11.setCircleColor(Color.RED);

            LineDataSet setH12 = new LineDataSet(yValsH12, "MPI-H"); // MPI HIEGHT
            setH12.setAxisDependency(YAxis.AxisDependency.LEFT);
            setH12.setColor(Color.BLUE);
            setH12.setLineWidth(2f);
            setH12.setDrawValues(true);
            setH12.setDrawCircles(true);
            setH12.setDrawFilled(false);
            setH12.setDrawHighlightIndicators(true);
            setH12.setHighlightEnabled(true);
            setH12.setLabel("MPI-H");
            setH12.setDrawHighlightIndicators(true);

            ChartXMLDataProviderFiveToEighteen.dataSets.add(setW10);
            ChartXMLDataProviderFiveToEighteen.dataSets.add(setH11);
            ChartXMLDataProviderFiveToEighteen.dataSets.add(setH12);

            LineData data = new LineData(ChartXMLDataProviderFiveToEighteen.xVals, ChartXMLDataProviderFiveToEighteen.dataSets);

            data.setDrawValues(false);


            // set data
            mChart.setData(data);
            mChart.invalidate();
        } else if (chartType.contains("IAPBMI")) {

            //chart IAPBMI 5-18
            //System.out.println(" IAPBMI patientCurrentAge "+patientCurrentAge );
            ArrayList<Entry> yValsBMI = new ArrayList<Entry>();


            for (int i = 0; i < modelGraphDatas.size(); i++) {
                float age = modelGraphDatas.get(i).getPatientModelAge();
                int month = modelGraphDatas.get(i).getPatientModelAgeMonth();//(int) (age) * 12 + (int) (age % 12);

                float qoutientValue = (month / 12) * 4;
                float temp = (month % 12);
                float temp1 = temp / 12;

                float reminderValue = temp1 * 4;
                float finalIndex = qoutientValue + reminderValue;

                float ft = (float) modelGraphDatas.get(i).getPatientModelBMI();
                if(ft!=0){
                    yValsBMI.add(new Entry(ft, ((int) finalIndex - 20)));
                }else {
                    System.out.println("BMI IS EMPTY");
                }


            }


            LineDataSet setBMI = new LineDataSet(yValsBMI, "P-BMI");
            setBMI.setAxisDependency(YAxis.AxisDependency.LEFT);
            setBMI.setColor(Color.BLACK);
            setBMI.setLineWidth(3f);
            setBMI.setDrawValues(true);
            setBMI.setDrawCircles(true);
            setBMI.setDrawFilled(true);
            setBMI.setDrawHighlightIndicators(true);
            setBMI.setHighlightEnabled(true);
            setBMI.setLabel("P-BMI");
            setBMI.setCircleColor(Color.RED);

            ChartXMLDataProviderFiveToEighteenBMI.dataSets.add(setBMI);

            LineData data = new LineData(ChartXMLDataProviderFiveToEighteenBMI.xVals, ChartXMLDataProviderFiveToEighteenBMI.dataSets);


            data.setDrawValues(false);

            // set data
            mChart.setData(data);

            mChart.invalidate();
        } else if (chartType.equals("BMI_0_to_18")) {
            //chart BMI_O_to_18
            // System.out.println(" BMI_0_to_18 starts "  );
            ArrayList<Entry> yValsBMI = new ArrayList<Entry>();
            for (int i = 0; i < modelGraphDatas.size(); i++) {
                float age = modelGraphDatas.get(i).getPatientModelAge();
                int month = modelGraphDatas.get(i).getPatientModelAgeMonth();//(int) (age) * 12 + (int) (age % 12);
                float qoutientValue = (month / 12) * 4;
                float temp = (month % 12);
                float temp1 = temp / 12;

                float reminderValue = temp1 * 4;
                float finalIndex = qoutientValue + reminderValue;

                float ft = (float) modelGraphDatas.get(i).getPatientModelBMI();
                if (ft!=0){
                    yValsBMI.add(new Entry(ft, ((int) finalIndex)));
                }else {
                    System.out.println("BMI is empty");
                }

            }


            LineDataSet setBMI = new LineDataSet(yValsBMI, "P-BMI");
            setBMI.setAxisDependency(YAxis.AxisDependency.LEFT);
            setBMI.setColor(Color.BLACK);
            setBMI.setLineWidth(3f);
            setBMI.setDrawValues(true);
            setBMI.setDrawCircles(true);
            setBMI.setDrawFilled(true);
            setBMI.setDrawHighlightIndicators(true);
            setBMI.setHighlightEnabled(true);
            setBMI.setLabel("P-BMI");
            setBMI.setCircleColor(Color.RED);

            ChartXMLDataProviderFiveToEighteenBMI.dataSets.add(setBMI);

            LineData data = new LineData(ChartXMLDataProviderFiveToEighteenBMI.xVals, ChartXMLDataProviderFiveToEighteenBMI.dataSets);


            data.setDrawValues(false);

            // set data
            mChart.setData(data);

            mChart.invalidate();
            //System.out.println(" BMI_0_to_18 Ends ");
        } else if (chartType.contains("LenghtHeightWeightHeadCircumference")) {
            // System.out.println("LenghtHeightWeightHeadCircumference patientCurrentAge "+patientCurrentAge );
            ArrayList<Entry> yVals10 = new ArrayList<Entry>();
            ArrayList<Entry> yVals11 = new ArrayList<Entry>();
            ArrayList<Entry> yVals12 = new ArrayList<Entry>();

            int k = 0 ;

            /*if(patientCurrentAge <= 5) {*/
            //if ()
            for (int i = 0; i < 61; i++) {
                //if (visitRecordsCount < i) {
                for (int mn = 0; mn < visitRecordsCount; mn++) {
                    if (i == patientAgeMonth[mn]) {
                        float ft = (float) patientWeight[mn];
                        if(ft!=0){
                            yVals10.add(new Entry(ft, i));
                        }else {
                            System.out.println("Patient weight is empty");
                        }


                        float ft1 = (float) patientHeadCircumference[mn];
                        if (ft1!=0){
                            yVals11.add(new Entry(ft1, i));
                        }else {
                            System.out.println("Patient head circumferences is empty");
                        }

                        float ft2 = (float) patientHeight[mn];
                        if (ft2!=0){
                            yVals12.add(new Entry(ft2, i));
                        }else {
                            System.out.println("Patient Height is empty");
                        }


                        //System.out.println("patientHeight " + ft2 +" ft: patientWeight "+ft+" patientHeadCircumference "+ft1 );
                    }
                }

                //}
                //k++ ;
            }


            /*for (int i = 0; i < modelGraphDatas.size(); i++) {
                float age = modelGraphDatas.get(i).getPatientModelAge();
                int month = modelGraphDatas.get(i).getPatientModelAgeMonth();//(int) (age) * 12 + (int) (age % 12);
                float qoutientValue = (month / 12) * 4;
                float temp = (month % 12);
                float temp1 = temp / 12;

                float reminderValue = temp1 * 4;
                float finalIndex = qoutientValue + reminderValue;

                float ft = (float)modelGraphDatas.get(i).getPatientModelWeight();
                yVals10.add(new Entry(ft,(int)finalIndex));


                float ft1 = (float) modelGraphDatas.get(i).getPatientModelHeadCircumference();
                yVals12.add(new Entry(ft1,(int)finalIndex));

                float ft2 = (float) modelGraphDatas.get(i).getPatientModelHeight();
                 yVals11.add(new Entry(ft2, ((int) finalIndex)));



            }*/


            LineDataSet set10 = new LineDataSet(yVals10, "P-W");
            set10.setAxisDependency(YAxis.AxisDependency.LEFT);
            set10.setColor(Color.BLACK);
            set10.setLineWidth(3f);
            set10.setDrawValues(true);
            set10.setDrawCircles(true);
            set10.setDrawFilled(true);
            set10.setDrawHighlightIndicators(true);
            set10.setHighlightEnabled(true);
            set10.setLabel("P-W");
            set10.setDrawHighlightIndicators(true);
            set10.setCircleColor(Color.RED);

            LineDataSet set11 = new LineDataSet(yVals11, "P-HC");
            set11.setAxisDependency(YAxis.AxisDependency.LEFT);
            set11.setColor(Color.BLACK);
            set11.setLineWidth(3f);
            set11.setDrawValues(true);
            set11.setDrawCircles(true);
            set11.setDrawFilled(true);
            set11.setDrawHighlightIndicators(true);
            set11.setHighlightEnabled(true);
            set11.setLabel("P-HC");
            set11.setDrawHighlightIndicators(true);
            set11.setCircleColor(Color.RED);

            LineDataSet set12 = new LineDataSet(yVals12, "P-H");
            set12.setAxisDependency(YAxis.AxisDependency.LEFT);
            set12.setColor(Color.BLACK);
            set12.setLineWidth(3f);
            set12.setDrawValues(true);
            set12.setDrawCircles(true);
            set12.setDrawFilled(true);
            set12.setDrawHighlightIndicators(true);
            set12.setHighlightEnabled(true);
            set12.setLabel("P-H");
            set12.setDrawHighlightIndicators(true);
            set12.setCircleColor(Color.RED);

            ChartXMLDataProvider.dataSets.add(set10);
            ChartXMLDataProvider.dataSets.add(set11);
            ChartXMLDataProvider.dataSets.add(set12);
            LineData data = new LineData(ChartXMLDataProvider.xVals, ChartXMLDataProvider.dataSets);
            //data.setValueTextColor(Color.WHITE);
            //data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            mChart.setData(data);
            mChart.invalidate();
        } else {

            //System.out.println(" Else Last Chart patientCurrentAge "+patientCurrentAge );
            ArrayList<Entry> yValsHW10 = new ArrayList<Entry>();
            int k = 0;

            for (int i = 45; i < 111; i++) {
                //if (visitRecordsCount < i) {
                for (int mn = 0; mn < visitRecordsCount; mn++) {
                    Arrays.sort(patientAgeMonth);
                    if (i == (int) patientHeight[mn]) {
                        float ft = (float) patientWeight[mn];
                        if(ft!=0){
                            yValsHW10.add(new Entry(ft, k));
                            k++;
                        }else {
                            System.out.println("Patient weight is missing");
                        }


                    }
                }
            }

            LineDataSet setHW10 = new LineDataSet(yValsHW10, "P-HW");
            setHW10.setAxisDependency(YAxis.AxisDependency.LEFT);
            setHW10.setColor(Color.BLACK);
            setHW10.setLineWidth(3f);
            setHW10.setDrawValues(true);
            setHW10.setDrawCircles(true);
            setHW10.setDrawFilled(true);
            setHW10.setDrawHighlightIndicators(true);
            setHW10.setHighlightEnabled(true);
            setHW10.setLabel("P-HW");
            setHW10.setCircleColor(Color.RED);

            ChartXMLDataProvider.dataSets.add(setHW10);

            LineData dataHW = new LineData(ChartXMLDataProvider.xVals, ChartXMLDataProvider.dataSets);

            dataHW.setDrawValues(false);

            // set data
            mChart.setData(dataHW);

            mChart.invalidate();
        }

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
        mChart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

         /*Displaying Menu Icon*/
        getMenuInflater().inflate(R.menu.charts_main, menu);
        for (int j = 0; j < menu.size(); j++) {
            MenuItem item = menu.getItem(j);
            item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

           /* String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

            String image_name = pName+"_"+ timeStamp + ".jpg";
            mChart.saveToGallery(image_name, 90);*/

            takeScreenshot();

            alertDialog();

            // Toast.makeText(this,"Chart is downloaded successfully",Toast.LENGTH_LONG).show();
            return true;
        } else if (id == android.R.id.home) {

            // Tapping on top left ActionBar icon navigates "up" to hierarchical parent screen.
            // The parent is defined in the AndroidManifest entry for this activity via the
            // parentActivityName attribute (and via meta-data tag for OS versions before API
            // Level 16). See the "Tasks and Back Stack" guide for more information:
            // http://developer.android.com/guide/components/tasks-and-back-stack.html
            //NavUtils.navigateUpFromSameTask(this);
            Intent i = new Intent(getApplicationContext(), ChartOptions.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
  /* Alert Dialog*/

    private void alertDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChartLayout.this);

        // Setting Dialog Message
        alertDialog.setMessage("Chart is downloaded successfully please check your gallery");

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        alertDialog.show();
    }


    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            //Zoom Out the graph
            while (!mChart.isFullyZoomedOut()) {
                mChart.zoomOut();

            }
            Thread.sleep(2000);
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);

            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(true);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            galleryAddPic(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }


    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }


    private void galleryAddPic(File imageFile) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(String.valueOf(imageFile));
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


   /* private Boolean exit = false;

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
*/

    @Override
    public int compare(ModelGraphData lhs, ModelGraphData rhs) {
        return 0;
    }
}
