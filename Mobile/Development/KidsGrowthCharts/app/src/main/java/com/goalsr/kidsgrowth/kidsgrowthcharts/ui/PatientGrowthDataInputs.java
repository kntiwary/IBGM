package com.goalsr.kidsgrowth.kidsgrowthcharts.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.goalsr.kidsgrowth.kidsgrowthcharts.R;
import com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata.ChartXMLDataProvider;
import com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata.ChartXMLDataProviderFiveToEighteen;
import com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata.ChartXMLDataProviderFiveToEighteenBMI;
import com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata.Percentile;
import com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata.ReadXMLData;
import com.goalsr.kidsgrowth.kidsgrowthcharts.db.DBFeedReaderContract;
import com.goalsr.kidsgrowth.kidsgrowthcharts.db.DataBaseHelper;
import com.goalsr.kidsgrowth.kidsgrowthcharts.ui.charts.ChartLayout;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.Age;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.CHMActivity;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.DateUtils;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.PreferenceUtils;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.SharedValues;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.AgeCalculator;

import org.xml.sax.SAXException;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;



import com.mikhaellopez.lazydatepicker.LazyDatePicker;


public class PatientGrowthDataInputs extends CHMActivity implements View.OnClickListener {

    public static long visitid;
    private EditText pvisitdate;
    private Drawable error_indicator;
    private int error_count = 0;

    private DatePickerDialog toDatePickerDialog;
    private String date1;
    private Date selectedDate;
    private String selectedDateFormat;
    private SimpleDateFormat dateFormatter;

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_growth_data_inputs);

        //For Validaitons at fieldlevel
        // Setting custom drawable instead of red error indicator,
        error_indicator = getResources().getDrawable(R.drawable.popup_inline_error);

        int left = 0;
        int top = 0;

        int right = error_indicator.getIntrinsicHeight();
        int bottom = error_indicator.getIntrinsicWidth();

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        error_indicator.setBounds(new Rect(left, top, right, bottom));

//        pvisitdate = (EditText) findViewById(R.id.pvisitdate);
//        pvisitdate.setInputType(InputType.TYPE_NULL);
//        pvisitdate.setKeyListener(null);

        TextView weightPercentileText = (TextView) findViewById(R.id.weightPercentileText);
        weightPercentileText.setVisibility(View.GONE);

        TextView heightPercentileText = (TextView) findViewById(R.id.heightPercentileText);
        heightPercentileText.setVisibility(View.GONE);

        TextView bmiPercentileText = (TextView) findViewById(R.id.bmiPercentileText);
        bmiPercentileText.setVisibility(View.GONE);







        Date minDate = LazyDatePicker.stringToDate("01-01-2000", DATE_FORMAT);
        Date maxDate = LazyDatePicker.stringToDate("01-01-2028", DATE_FORMAT);




        LazyDatePicker lazyDatePicker = (LazyDatePicker)findViewById(R.id.lazyDate);
        lazyDatePicker.setDateFormat(LazyDatePicker.DateFormat.DD_MM_YYYY);
        lazyDatePicker.setMinDate(minDate);
        lazyDatePicker.setMaxDate(maxDate);


        lazyDatePicker.setOnDatePickListener(new LazyDatePicker.OnDatePickListener() {
            @Override
            public void onDatePick(Date dateSelected) {
                selectedDate = dateSelected;
                selectedDateFormat = LazyDatePicker.dateToString(dateSelected, DATE_FORMAT);
                Toast.makeText(PatientGrowthDataInputs.this,
                        "Selected date: " + LazyDatePicker.dateToString(dateSelected, DATE_FORMAT),
                        Toast.LENGTH_SHORT).show();
//                getAge(selectedDate);


            }
        });








//        pvisitdate.setHint("Please select visit date");

//        setDateTimeField();
        updateLabel();

        EditText pAgeOnCurrentDate = (EditText) findViewById(R.id.pvisitage);
        System.out.println("pAgeOnCurrentDate  " + pAgeOnCurrentDate);
        SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate2 = new Date();//= SharedValues.getSelectedPatientDOB(); //
        String dob = SharedValues.getSelectedPatientDOB();
        System.out.println("dob"+ dob);
        try {
            birthDate2 = DateUtils.stringToDate("dd/MM/yyyy", SharedValues.getSelectedPatientDOB());
            System.out.println("birthDate2"+ birthDate2);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        // System.out.println("VISIT INPUT SharedValues.getSelectedPatientId(): "+ SharedValues.getSelectedPatientId());


        Age age2 = AgeCalculator.calculateAge(birthDate2, new Date());
        System.out.println("age2  " + age2);
        // Age age = AgeCalculator.anotherAgeCalculation(birthDate) ;
        float years = age2.getYears();
        float month = age2.getMonths();
        float ageInM = (years * 12) + month;
        float modMonth = ageInM % 12;
        final int patientAge = Math.round(years + modMonth);
        System.out.println("patientAge  " + patientAge);
        //Toast.makeText(this, ""+patientAge, Toast.LENGTH_SHORT).show();

        SharedValues.setPatientAge(patientAge);

        SharedValues.setTempPatientAge(roundToHalf(patientAge));

        pAgeOnCurrentDate.setText(String.valueOf(Math.round(years)) + "." + Math.round(modMonth));
        pAgeOnCurrentDate.setEnabled(false);
        pAgeOnCurrentDate.setTextColor(Color.DKGRAY);


        EditText bmiText = (EditText) findViewById(R.id.calculateBMIText);



       /* bmiText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bmiPercentileMessage();
                //Your query to fetch Data
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    //Your query to fetch Data

                }
            }
        });*/

        if (years > 5) {
            EditText pheadcircumference = (EditText) findViewById(R.id.pheadcircumference);
            pheadcircumference.setVisibility(View.INVISIBLE);
        }

        EditText pweightText = (EditText) findViewById(R.id.pweight);



        /*pweightText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (!hasFocus) {
                    EditText pweightText = (EditText)findViewById(R.id.pweight);
                    System.out.println("pweightText "+pweightText.getText().toString());
                    System.out.println("Age  " + patientAge);
                    ReadXMLData readXMLData = new ReadXMLData();
                    Percentile percentileObject = new Percentile();
                    try {
                          percentileObject = readXMLData.readPercentileValues(getApplicationContext(), patientAge, SharedValues.getSelectedPatientGender().toString(), "Weight");
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }
                    String resultString = percentileObject.getLiesBetweenValue((double)Double.valueOf(pweightText.getText().toString()), "Weight");
                    //Toast.makeText(getApplicationContext(), resultString, Toast.LENGTH_LONG).show();
                    TextView percentileText = (TextView)findViewById(R.id.percentileText);
                    percentileText.setText(" Weight: "+resultString);
                }
            }
        });*/

        pweightText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Your query to fetch Data
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {

                    if (s.length() > 0) {
                        calculateBMI();

                        EditText pweightText = (EditText) findViewById(R.id.pweight);
                        EditText pcur = (EditText) findViewById(R.id.pvisitage);
                        ReadXMLData readXMLData = new ReadXMLData();
                        Percentile weightPercentileObject = new Percentile();
                        try {
//                            weightPercentileObject = readXMLData.readPercentileValues(getApplicationContext(), SharedValues.getTempPatientAge(), SharedValues.getSelectedPatientGender().toString(), "Weight");
                            weightPercentileObject = readXMLData.readPercentileValues(getApplicationContext(), Double.valueOf(pcur.getText().toString()), SharedValues.getSelectedPatientGender().toString(), "Weight");
//                            heightPercentileObject = readXMLData.readPercentileValues(getApplicationContext(), Double.valueOf(pcur.getText().toString()), SharedValues.getSelectedPatientGender().toString(), "Height");
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String wieghtPercentileResult = weightPercentileObject.getLiesBetweenValue((double) Double.valueOf(pweightText.getText().toString()), "Weight");
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("referenceWeight", wieghtPercentileResult);
                        editor.commit();
                        //From preference utils
                        PreferenceUtils.set(getApplicationContext(), "referenceWeight", wieghtPercentileResult);

                        //SharedValues.setReferenceWeight(wieghtPercentileResult);

                        TextView weightPercentileText = (TextView) findViewById(R.id.weightPercentileText);
                        weightPercentileText.setVisibility(View.VISIBLE);
                        weightPercentileText.setText("Weight: " + wieghtPercentileResult);

                        //Your query to fetch Data
                    } else {
                        EditText calculateBMItxt = (EditText) findViewById(R.id.calculateBMIText);
                        calculateBMItxt.setText("");
                        calculateBMItxt.setEnabled(false);
                        calculateBMItxt.setTextColor(Color.DKGRAY);
                    }

                } catch (Exception e) {

                }

            }
        });

        EditText pheightText = (EditText) findViewById(R.id.pheight);
        final String patientheight = pheightText.getText().toString();
        /*pheightText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    EditText pweightText = (EditText)findViewById(R.id.pheight);
                    System.out.println("patientheight " + pweightText.getText().toString());
                    System.out.println("Age  " + patientAge);


                    Toast.makeText(getApplicationContext(),  pweightText.getText().toString()+  "Height lies between 3rd and 10th Percentile", Toast.LENGTH_LONG).show();
                }
            }
        });*/

        pheightText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Your query to fetch Data
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {

                    if (s.length() > 0) {
                        calculateBMI();

                        EditText pheightText = (EditText) findViewById(R.id.pheight);
                        EditText pcur = (EditText) findViewById(R.id.pvisitage);
                        ReadXMLData readXMLData = new ReadXMLData();
                        Percentile heightPercentileObject = new Percentile();
                        heightPercentileObject.setHeight( (double) Double.valueOf(pheightText.getText().toString()));
                        try {
                            System.out.println("Age:" + SharedValues.getPatientAge());

                            System.out.println(" Sharath sir: "+ Double.valueOf(pcur.getText().toString()));
                            heightPercentileObject = readXMLData.readPercentileValues(getApplicationContext(), Double.valueOf(pcur.getText().toString()), SharedValues.getSelectedPatientGender().toString(), "Height");
//                            heightPercentileObject = readXMLData.readPercentileValues(getApplicationContext(), Double.valueOf(pcur.getText().toString()), SharedValues.getSelectedPatientGender().toString(), "Height");
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        String heightPercentileResult = heightPercentileObject.getLiesBetweenValue(pheightText.getText().toString(), "Height");
                        String heightPercentileResult = heightPercentileObject.getLiesBetweenValue((double) Double.valueOf(pheightText.getText().toString()), "Height");
                        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("ref_Height", heightPercentileResult);
                        editor.commit();

                        //Shrishail seeting reference value to shared preference
                        SharedValues.setReferenceHeight(heightPercentileResult);


                   /* System.out.println(" SharedValues.getPatientAge(): "+ Double.valueOf(pcur.getText().toString()));
                    System.out.println("  SharedValues.getSelectedPatientGender(): "+  SharedValues.getSelectedPatientGender());
                    System.out.println("Double.valueOf(pheightText.getText().toString()): "+Double.valueOf(pheightText.getText().toString()));
                    System.out.println("heightPercentileResult: "+heightPercentileResult);*/

                        TextView heightPercentileText = (TextView) findViewById(R.id.heightPercentileText);
                        heightPercentileText.setVisibility(View.VISIBLE);
                        heightPercentileText.setText("Height: " + heightPercentileResult);
                        //Your query to fetch Data
                    } else {
                        EditText calculateBMItxt = (EditText) findViewById(R.id.calculateBMIText);
                        calculateBMItxt.setText("");
                        calculateBMItxt.setEnabled(false);
                        calculateBMItxt.setTextColor(Color.DKGRAY);
                    }

                } catch (Exception e) {

                }

            }
        });

       /* pvisitdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(PatientGrowthDataInputs.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                return true;
            }
        });*/
        //  updateLabel();
        TextView genId = (TextView) findViewById(R.id.textView5);

        //genId.setText(PatientBasicInputs.PID);
        if (SharedValues.getSelectedPatientId() != null) {
            SharedValues.setSelectedPatientId(SharedValues.getSelectedPatientId());
            SharedValues.setSelectedPatientDOB(SharedValues.getSelectedPatientDOB());
        }
        genId.setText("Visit Data");
    }

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new
            DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }

            };

    private void setDateTimeField() {
        pvisitdate.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();

        try {
            toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {


                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    pvisitdate.setText(dateFormatter.format(newDate.getTime()));
                    date1 = dateFormatter.format(newDate.getTime());
                    Date birthDate3 = new Date();
                    Date visitDate = new Date();

                    try {


                        EditText pAgeOnCurrentDate = (EditText) findViewById(R.id.pvisitage);
                        birthDate3 = DateUtils.stringToDate("dd/MM/yyyy", SharedValues.getSelectedPatientDOB());
                        visitDate = dateFormatter.parse(date1);
                        Age age2 = AgeCalculator.calculateAge(birthDate3, visitDate);
                        // Age age = AgeCalculator.anotherAgeCalculation(birthDate) ;
                        float years = age2.getYears();
                        float month = age2.getMonths();
                        float ageInM = (years * 12) + month;
                        float modMonth = ageInM % 12;
                        if (years < 0) {
                            // System.out.println("years: negative " + years);
                            pAgeOnCurrentDate.setText("");
                            pAgeOnCurrentDate.setEnabled(false);
                            pAgeOnCurrentDate.setTextColor(Color.DKGRAY);
                            alertForVisitDate();
                        } else {
                            //System.out.println("years: " + years);
                            pAgeOnCurrentDate.setText(String.valueOf(Math.round(years)) + "." + Math.round(modMonth));
                            //SharedValues.setPatientAge(Math.round(years));
                            //int midage = Math.round(years+modMonth);
                            // Toast.makeText(PatientGrowthDataInputs.this, "Visited Age"+midage, Toast.LENGTH_SHORT).show();
                            final float ageInMonths = (years * 12) + modMonth;
                            final float ageinYears = ageInMonths / 12;


                            //Toast.makeText(PatientGrowthDataInputs.this, "Age in Months"+ageInMonths+"Age in Years"+ageinYears+"floaredValue"+floaredValue+"cieledValue"+cieledValue, Toast.LENGTH_SHORT).show();
                            SharedValues.setTempPatientAge(roundToHalf(ageinYears));
                            pAgeOnCurrentDate.setEnabled(false);
                            pAgeOnCurrentDate.setTextColor(Color.DKGRAY);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //  updateLabel();
                    //Toast.makeText(getApplicationContext(), "Selected Date :" + dateFormatter.format(newDate.getTime()), Toast.LENGTH_SHORT).show();
                }

            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


    public double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        //pvisitdate.setText(sdf.format(myCalendar.getTime()));
    }

    /*pDataCancelAction*/

    public void pDataCancelAction(View view) {
        alertDialog();
    }

    public void alertForVisitDate() {
        Toast toast = Toast.makeText(this, "Visit date is before date of birth ! Enter Correct Visit date", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onClick(View view) {
        if (view == pvisitdate) {
            //  toDatePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis()-20000);
            toDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            toDatePickerDialog.show();


        }
    }

    public void pDataSaveAction(View view) {

        try {


            error_count = 0;


            Button saveButton = (Button) view.findViewById(R.id.pdatasave);

            //Save Patient Data
            ContentValues values = new ContentValues();

            SQLiteDatabase db = DataBaseHelper.myDBHelper.getReadableDatabase();

            values.put(DBFeedReaderContract.FeedEntryVisits.V_DOCTOR_UUID, SharedValues.getDocid());

            // System.out.println("GRwOTH INPUT SharedValues.getSelectedPatientId(): "+SharedValues.getSelectedPatientId());
            values.put(DBFeedReaderContract.FeedEntryVisits.V_PATIENT_UUID, SharedValues.getSelectedPatientId());

//            EditText pdata = (EditText) findViewById(R.id.pvisitdate);

            String dateofVisit = selectedDateFormat;
//            values.put(DBFeedReaderContract.FeedEntryVisits.V_VISITDATE, pdata.getText().toString());
            values.put(DBFeedReaderContract.FeedEntryVisits.V_VISITDATE, dateofVisit.toString());
//            fieldValidationOnSave(pdata, "Visit date is empty");
//            String visitdt = pdata.getText().toString();
            String visitdt = dateofVisit.toString();

            String dateOfBirth = SharedValues.getSelectedPatientDOB().toString();


            EditText pdata = (EditText) findViewById(R.id.pheight);
            //fieldValidationOnSave(pdata, "Height field is empty");

            float bmi = 0;
            float heightsquaremeters = 0;
          //  if (error_count == 0) {
            if(pdata.getText().toString().matches("")){
                System.out.println("Height is not entered by doctor");
            }else if(!pdata.getText().toString().isEmpty()) {
                values.put(DBFeedReaderContract.FeedEntryVisits.V_HEIGHT, pdata.getText().toString());
                float heightcms = Float.valueOf(pdata.getText().toString());
                float heightmeters = heightcms / 100;
                heightsquaremeters = heightmeters * heightmeters;
            }


                pdata = (EditText) findViewById(R.id.pweight);
               // fieldValidationOnSave(pdata, "Weight field is empty");
               // if (error_count == 0) {
            if(pdata.getText().toString().matches("")){
              ///  values.put(DBFeedReaderContract.FeedEntryVisits.V_WEIGHT, pdata.getText().toString());
               // float weight = 00;
               // bmi = weight / heightsquaremeters;
                String bm = String.valueOf(bmi);
            }else {
                values.put(DBFeedReaderContract.FeedEntryVisits.V_WEIGHT, pdata.getText().toString());
                float weight = Float.valueOf(pdata.getText().toString());
                bmi = weight / heightsquaremeters;
                String bm = String.valueOf(bmi);
            }

                //}
           // }

            pdata = (EditText) findViewById(R.id.pheadcircumference);
            values.put(DBFeedReaderContract.FeedEntryVisits.V_HEADCIRCUMFERENCE, pdata.getText().toString());

            values.put(DBFeedReaderContract.FeedEntryVisits.V_BMI, String.valueOf(bmi));

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date birthDate = new Date();//= SharedValues.getSelectedPatientDOB(); //
            try {
                birthDate = sdf.parse(SharedValues.getSelectedPatientDOB());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date visitdate = null;
            try {
                visitdate = sdf.parse(visitdt);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
           /* System.out.println("DOB: "+sdf.format(birthDate));
            System.out.println("Visit Date: "+sdf.format(visitdate));*/

                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(birthDate);
                cal2.setTime(visitdate);

                if (cal1.after(cal2)) {
               /* System.out.println("visitdate is before dob ");
                System.out.println("Invalid ");*/
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(PatientGrowthDataInputs.this);

                    // Setting Dialog Title
                    //  alertDialog.setTitle("Confirm Log out");

                    // Setting Dialog Message
                    alertDialog.setMessage("Visit date is before date of birth ! Enter Correct Visit date");

                    // Setting Icon to Dialog
                    //  alertDialog.setIcon(R.drawable.delete);

                    // Setting Positive "Yes" Button
                /*alertDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        // Write your code here to invoke YES event
                        Intent var = new Intent(getApplicationContext(),PatientInformation.class);
                        var.putExtra("info", 1);
                        finish();

                        startActivity(var);
                        //Toast.makeText(getApplicationContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();

                    }
                });*/

                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("Ok ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();

                } else if (cal1.before(cal2) || cal1.equals(cal2)) {
               /* System.out.println("visitdate is after dob ");
                System.out.println("valid ");*/
                    Age age = AgeCalculator.calculateAge(birthDate, visitdate);
                    // Age age = AgeCalculator.anotherAgeCalculation(birthDate) ;
                    int yrs = age.getYears();
                    int months = age.getMonths();
                    int ageInMonths = (yrs * 12) + months;

                    values.put(DBFeedReaderContract.FeedEntryVisits.V_AGEYEARS, age.getYears());
                    values.put(DBFeedReaderContract.FeedEntryVisits.V_AGEMONTHS, ageInMonths);

                    Calendar calendar = Calendar.getInstance();
                    DecimalFormat mFormat = new DecimalFormat("00");
                    values.put(DBFeedReaderContract.FeedEntryVisits.V_ENTRYDATE, Long.valueOf(mFormat.format(Calendar.getInstance().get(Calendar.YEAR)) + mFormat.format(Calendar.getInstance().get(Calendar.MONTH) + 1) + mFormat.format(calendar.get(Calendar.DATE)) + mFormat.format(Calendar.getInstance().get(Calendar.HOUR)) + mFormat.format(Calendar.getInstance().get(Calendar.MINUTE)) + mFormat.format(Calendar.getInstance().get(Calendar.SECOND))));

                    pdata = (EditText) findViewById(R.id.pvisitage);
                    fieldValidationOnSave(pdata, "Age is empty");
                    values.put(DBFeedReaderContract.FeedEntryVisits.V_AGE, pdata.getText().toString());


                    SQLiteDatabase myDataBase = DataBaseHelper.myDBHelper.getWritableDatabase();

                   // if (error_count == 0) {
                        if (saveButton.getText().toString() != "Update") {

                            // Insert the new row, returning the primary key value of the new row
                            long newRowId;

                            newRowId = myDataBase.insert(
                                    DBFeedReaderContract.FeedEntryVisits.TABLE_NAME,
                                    DBFeedReaderContract.FeedEntryVisits.V_ID,
                                    values);

                            visitid = newRowId;

                            // saveButton.setText("Edit");
                            saveButton.setText("Update");

                            Toast toast = Toast.makeText(this, "Data saved successfully", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        /*Intent i =  new Intent(getApplicationContext(),PatientsListActivity.class);
                        startActivity(i);
                        finish();*/
                            PatientInformation.tabHost.setCurrentTabByTag("Data");
                        } else {

                            Toast toast = Toast.makeText(this, "Data updated successfully", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            myDataBase.update(DBFeedReaderContract.FeedEntryVisits.TABLE_NAME, values, "_id" + "=" + String.valueOf(visitid), null);
                        }
                    }
               // }

            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Toast.makeText(this, "\n"+msg, Toast.LENGTH_SHORT).show();

        }


    }


    public void pDataViewChart(View view) {
        Intent intent = new Intent(getApplicationContext(), ChartOptions.class);
        startActivity(intent);
        finish();


    }


    public void calculateBMI() {

        EditText patientHeight = (EditText) findViewById(R.id.pheight);
        EditText patientWeight = (EditText) findViewById(R.id.pweight);
        EditText pcur = (EditText) findViewById(R.id.pvisitage);


        EditText calculateBMItxt = (EditText) findViewById(R.id.calculateBMIText);
        try {
            String patientHeight1 = patientHeight.getText().toString().trim();
            String patientWeight1 = patientWeight.getText().toString().trim();
            Double bmiResult = null;

            if (!patientHeight1.isEmpty() && !patientWeight1.isEmpty()) {
                //BMI = ( Weight in Kilograms / ( Height in Meters x Height in Meters ) )
                if (!Double.valueOf(patientWeight1).isNaN() && !Double.valueOf(patientHeight1).isNaN()) {
                    bmiResult = (Double.valueOf(patientWeight1) / ((Double.valueOf(patientHeight1) / 100) * (Double.valueOf(patientHeight1) / 100)));
                    DecimalFormat df = new DecimalFormat("#.##");
                    Double doubleValue = Double.valueOf(df.format(bmiResult));
                    calculateBMItxt.setText(doubleValue.toString());
                    calculateBMItxt.setEnabled(false);
                    calculateBMItxt.setTextColor(Color.DKGRAY);
                }

                EditText pbmiText = (EditText) findViewById(R.id.calculateBMIText);
                ReadXMLData readXMLData = new ReadXMLData();
                Percentile bmiPercentileObject = new Percentile();
                try {
//                    bmiPercentileObject = readXMLData.readPercentileValues(getApplicationContext(), SharedValues.getTempPatientAge(), SharedValues.getSelectedPatientGender().toString(), "BMI");
                    bmiPercentileObject = readXMLData.readPercentileValues(getApplicationContext(), Double.valueOf(pcur.getText().toString()), SharedValues.getSelectedPatientGender().toString(), "BMI");
//                    weightPercentileObject = readXMLData.readPercentileValues(getApplicationContext(), Double.valueOf(pcur.getText().toString()), SharedValues.getSelectedPatientGender().toString(), "Weight");
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
                String bmiPercentileResult = bmiPercentileObject.getLiesBetweenValue((double) Double.valueOf(pbmiText.getText().toString()), "BMI");
                TextView bmiPercentileText = (TextView) findViewById(R.id.bmiPercentileText);
                bmiPercentileText.setVisibility(View.VISIBLE);
                bmiPercentileText.setText("BMI: " + bmiPercentileResult);

            } else {
                calculateBMItxt.setText("");
                calculateBMItxt.setEnabled(false);
                calculateBMItxt.setTextColor(Color.DKGRAY);
                /*Toast toast = Toast.makeText(this, "Oops cant calculate BMI", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();*/

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Commented chart methods, moved to ChartOptions.java

    /*public void weightChartAction(View view)
    {
        *//*
        Intent intent = new Intent() ;
        intent.setClass(getApplicationContext(), weightmonthsactivity.class) ;
        startActivity(intent);
        *//*

        ChartXMLDataProvider.xVals.clear();
        ChartXMLDataProvider.dataSets.clear();
        ChartXMLDataProviderFiveToEighteen.xVals.clear();
        ChartXMLDataProviderFiveToEighteen.dataSets.clear();

        String decideChartBasedOnGender = "" ;
        String decideGenderForChart = "" ;

        if (SharedValues.getSelectedPatientGender().equals("Male"))
        {
            decideGenderForChart = "WHOBoys" ;
            decideChartBasedOnGender = "LenghtHeightWeightHeadCircumference" ;
        }
        else
        {
            decideGenderForChart = "WHOGirls" ;
            decideChartBasedOnGender = "LenghtHeightWeightHeadCircumference" ;
        }

        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ChartLayout.class);
        intent.putExtra("genderForChart", decideGenderForChart) ;
        intent.putExtra("chartType", decideChartBasedOnGender);
        startActivity(intent);

    }

    public void weightForHeightAction(View view)
    {
        clearDataSetsForChart();

        String decideChartBasedOnGender = "" ;
        String decideGenderForChart = "" ;

        if (SharedValues.getSelectedPatientGender().equals("Male"))
        {
            decideGenderForChart = "WHOBoys" ;
            decideChartBasedOnGender = "WeightForHeight" ;
        }
        else
        {
            decideGenderForChart = "WHOGirls" ;
            decideChartBasedOnGender = "WeightForHeight" ;
        }

        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ChartLayout.class);
        intent.putExtra("genderForChart", decideGenderForChart) ;
        intent.putExtra("chartType", decideChartBasedOnGender);
        startActivity(intent);
    }

    public void iapFiveToEighteenHeightWeightAction(View view)
    {
        clearDataSetsForChart();

        String decideChartBasedOnGender = "" ;
        String decideGenderForChart = "" ;

        if (SharedValues.getSelectedPatientGender().equals("Male"))
        {
            decideGenderForChart = "WHOBoys" ;
            decideChartBasedOnGender = "IAPHeightWeight" ;
        }
        else
        {
            decideGenderForChart = "WHOGirls" ;
            decideChartBasedOnGender = "IAPHeightWeight" ;
        }

        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ChartLayout.class);
        intent.putExtra("genderForChart", decideGenderForChart) ;
        intent.putExtra("chartType", decideChartBasedOnGender);
        startActivity(intent);
    }

    public void iapFiveToEighteenBMIAction(View view)
    {
        clearDataSetsForChart();

        String decideChartBasedOnGender = "" ;
        String decideGenderForChart = "" ;

        if (SharedValues.getSelectedPatientGender().equals("Male"))
        {
            decideGenderForChart = "WHOBoys" ;
            decideChartBasedOnGender = "IAPBMI" ;
        }
        else
        {
            decideGenderForChart = "WHOGirls" ;
            decideChartBasedOnGender = "IAPBMI" ;
        }

        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ChartLayout.class);
        intent.putExtra("genderForChart", decideGenderForChart) ;
        intent.putExtra("chartType", decideChartBasedOnGender);
        startActivity(intent);
    }

    private void clearDataSetsForChart()
    {
        ChartXMLDataProvider.xVals.clear();
        ChartXMLDataProvider.dataSets.clear();
        ChartXMLDataProviderFiveToEighteen.xVals.clear();
        ChartXMLDataProviderFiveToEighteen.dataSets.clear();
        ChartXMLDataProviderFiveToEighteenBMI.xVals.clear();
        ChartXMLDataProviderFiveToEighteenBMI.dataSets.clear();
    }*/

    public void fieldValidationOnSave(EditText editText, String message) {
        if (editText.getText().toString().equals("")) {

            Toast.makeText(this, "Please select visit date", Toast.LENGTH_SHORT).show();
           /*// editText.setError(message);*/
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            error_count++;
        }
    }

    /*ALERT DIALOG*/
    private void alertDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PatientGrowthDataInputs.this);

        // Setting Dialog Title
        //  alertDialog.setTitle("Confirm Log out");

        // Setting Dialog Message
        alertDialog.setMessage("Do you really want to cancel?");

        // Setting Icon to Dialog
        //  alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event


                Intent var = new Intent(getApplicationContext(), PatientInformation.class);
                finish();
                startActivity(var);
                //Toast.makeText(getApplicationContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
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
