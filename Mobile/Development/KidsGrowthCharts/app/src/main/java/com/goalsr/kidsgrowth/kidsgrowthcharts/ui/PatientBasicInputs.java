package com.goalsr.kidsgrowth.kidsgrowthcharts.ui;


import android.app.Activity;
import android.app.AlertDialog;


import com.mikhaellopez.lazydatepicker.LazyDatePicker;


import android.app.DatePickerDialog;
import android.content.ContentValues;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;

import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.goalsr.kidsgrowth.kidsgrowthcharts.R;

import java.text.DecimalFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


import com.goalsr.kidsgrowth.kidsgrowthcharts.db.DataBaseHelper;
import com.goalsr.kidsgrowth.kidsgrowthcharts.db.DBFeedReaderContract;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.DateUtils;

import com.goalsr.kidsgrowth.kidsgrowthcharts.util.SharedValues;











public class PatientBasicInputs extends Activity implements View.OnClickListener {

    private static final String TAG = "PatientInput";
    //UI References
    private EditText pdob;
    public static String PID;
    public static long GeneratedPatientID;

    private Drawable error_indicator;
    private int error_count = 0;

    private DatePickerDialog toDatePickerDialog;
    private String date1;
    private Date selectedDate;
    private String selectedDateFormat;
    private SimpleDateFormat dateFormatter;

    //    private static final String DATE_FORMAT = "MM-dd-yyyy";
    private static final String DATE_FORMAT = "dd/MM/yyyy";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_basic_inputs);

        //For Validaitons at fieldlevel
        // Setting custom drawable instead of red error indicator,
        error_indicator = getResources().getDrawable(R.drawable.popup_inline_error);

//        Date minDate = LazyDatePicker.stringToDate("01-01-2016", DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -18);

//        Date minDate =cal.getTime();
//        Date maxDate =Calendar.getInstance().getTime();




//        Date minDate = LazyDatePicker.stringToDate(cal.getTime().toString(), DATE_FORMAT);
        Date minDate = LazyDatePicker.stringToDate("01-01-2016", DATE_FORMAT);
        System.out.println("minDate " + minDate);

        Date maxDate = LazyDatePicker.stringToDate(Calendar.getInstance().getTime().toString(), DATE_FORMAT);
        System.out.println("maxDate " + maxDate);











        // Init LazyDatePicker
        LazyDatePicker lazyDatePicker = (LazyDatePicker)findViewById(R.id.lazyDate);
        lazyDatePicker.setDateFormat(LazyDatePicker.DateFormat.DD_MM_YYYY);
        lazyDatePicker.setMinDate(minDate);
        lazyDatePicker.setMaxDate(maxDate);


        lazyDatePicker.setOnDatePickListener(new LazyDatePicker.OnDatePickListener() {
            @Override
            public void onDatePick(Date dateSelected) {
                selectedDate = dateSelected;
                selectedDateFormat = LazyDatePicker.dateToString(dateSelected, DATE_FORMAT);
                Toast.makeText(PatientBasicInputs.this,
                        "Selected date: " + LazyDatePicker.dateToString(dateSelected, DATE_FORMAT),
                        Toast.LENGTH_SHORT).show();
                getAge(selectedDate);
//                Calendar newDate = Calendar.getInstance();
//                date1 = dateFormatter.format(newDate.getTime());
//                System.out.println("date1 " + date1);
//                // Toast.makeText(getApplicationContext(), "date1 String :" + date1, Toast.LENGTH_SHORT).show();
//                try {
//                    Date dateOfBo = dateFormatter.parse(date1);
//                    getAge(dateOfBo);
//                    System.out.println("age " + getAge(dateOfBo));
//                    pdob.setText(dateFormatter.format(dateOfBo));
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


            }
        });










        int left = 0;
        int top = 0;

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        int right = error_indicator.getIntrinsicHeight();
        int bottom = error_indicator.getIntrinsicWidth();

        error_indicator.setBounds(new Rect(left, top, right, bottom));

        // EditText pname = (EditText) findViewById(R.id.patientname);
        // pname.setOnEditorActionListener(new EmptyTextListener(pname));

        //Validation ends

//        pdob = (EditText) findViewById(R.id.patientdob);
//        pdob.setInputType(InputType.TYPE_NULL);
//        pdob.setKeyListener(null);
        // updateLabel();
       /* pdob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(PatientBasicInputs.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                return true;
            }
        });*/
        //updateLabel(); //DOB by default set for current date.
        setDateTimeField();

        try {

            if (SharedValues.getSelectedPatientId() != null) {
                String dbvalue = "";
                SQLiteDatabase db = DataBaseHelper.myDBHelper.getReadableDatabase();
                Cursor pCursor = db.query(DBFeedReaderContract.FeedEntryPatients.TABLE_NAME, null, "_id=" + SharedValues.getSelectedPatientId(), null, null, null, null, null);
                pCursor.moveToFirst();

                SharedValues.setDocid(pCursor.getString(1).toString());

                EditText editText = (EditText) findViewById(R.id.patientname);
                editText.setText(pCursor.getString(2).toString());

//                editText = (EditText) findViewById(R.id.patientdob);
//                editText.setText(pCursor.getString(3).toString());
//                SharedValues.setSelectedPatientDOB(pCursor.getString(3).toString());
                //dbvalue += pCursor.getString(2).toString() + pCursor.getString(3).toString() +
                //        pCursor.getString(4).toString() + pCursor.getString(5).toString() +
                //     pCursor.getString(6).toString() + pCursor.getString(7).toString() ;

                Spinner spinner = (Spinner) findViewById(R.id.patientgenderlist);
                if (pCursor.getString(4).toString().trim().equalsIgnoreCase("Male")) {
                    spinner.setSelection(0);
                } else if (pCursor.getString(4).toString().trim().equalsIgnoreCase("Female")) {
                    spinner.setSelection(1);
                } else {
                    spinner.setSelection(2);
                }
                SharedValues.setSelectedPatientGender(pCursor.getString(4).toString());

                /*TextView patientcitylabel = (TextView) findViewById(R.id.patientcitylabel);
                patientcitylabel.setText("City:         "+pCursor.getString(5).toString());
                patientcitylabel.setWidth(100);

                TextView patientstatelabel = (TextView) findViewById(R.id.patientstatelabel);
                patientstatelabel.setWidth(100);
                patientstatelabel.setText("State:        "+pCursor.getString(6).toString());*/




               /* editText = (EditText) findViewById(R.id.patientstate) ;
                editText.setText(pCursor.getString(6).toString());*/


               /* editText = (EditText) findViewById(R.id.fathername) ;
                editText.setText(pCursor.getString(7).toString());

                editText = (EditText) findViewById(R.id.mothername) ;
                editText.setText(pCursor.getString(8).toString());*/

                SharedValues.setSelectedPatientCity(pCursor.getString(5));

                editText = (EditText) findViewById(R.id.fatherheight);
                editText.setText(pCursor.getString(9).toString());

                editText = (EditText) findViewById(R.id.motherheight);
                editText.setText(pCursor.getString(10).toString());

                EditText midParentalHeight1 = (EditText) findViewById(R.id.midParentalHeight);
                Double result = null;
                if (pCursor.getString(4).toString().trim().equalsIgnoreCase("Male")) {
                    if (!pCursor.getString(10).toString().isEmpty() && !pCursor.getString(9).toString().isEmpty()) {
                        // (Father's Height + Mother's Height + 13) / 2
                        result = (Double.valueOf(pCursor.getString(9).toString()) + Double.valueOf(pCursor.getString(10).toString()) + 13) / 2;
                        midParentalHeight1.setText(result.toString());
                        midParentalHeight1.setEnabled(false);
                        midParentalHeight1.setTextColor(Color.BLACK);
                        SharedValues.setMidParentalHeight(Math.round(result));
                    } else {
                        Toast toast = Toast.makeText(this, "Ooops cant calculate Parental Height", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else if (pCursor.getString(4).toString().trim().equalsIgnoreCase("Female")) {
                    if (!pCursor.getString(10).toString().isEmpty() && !pCursor.getString(9).toString().isEmpty()) {
                        //(Father's Height - 13 + Mother's Height) / 2
                        result = (Double.valueOf(pCursor.getString(9).toString()) - 13 + Double.valueOf(pCursor.getString(10).toString())) / 2;
                        midParentalHeight1.setText(result.toString());
                        midParentalHeight1.setEnabled(false);
                        midParentalHeight1.setTextColor(Color.BLACK);
                        SharedValues.setMidParentalHeight(Math.round(result));
                    } else {
                        Toast toast = Toast.makeText(this, "Ooops cant calculate Parental Height", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }

                editText = (EditText) findViewById(R.id.patientage);
                editText.setText(pCursor.getString(12).toString());


                Button changeSaveButtonText = (Button) findViewById(R.id.patientsavebutton);
                changeSaveButtonText.setText("Update");
                Spinner spinner2 = (Spinner) findViewById(R.id.patientstate);
                /*if (pCursor.getString(6).toString().trim().equalsIgnoreCase("Karnataka")) {
                    spinner2.setSelection(0);
                } else if (pCursor.getString(6).toString().trim().equalsIgnoreCase("Tamilnadu")) {
                    spinner2.setSelection(1);
                } else if (pCursor.getString(6).toString().trim().equalsIgnoreCase("Kerala")) {
                    spinner2.setSelection(2);
                }*/
                Resources res = getResources();
                String[] state = res.getStringArray(R.array.patient_state_array);
                int stateCount = state.length;
                String stateValue = pCursor.getString(6);
                if (stateCount > 0) {
                    for (int j = 0; j < stateCount; j++) {
                        if (state[j].equalsIgnoreCase(stateValue)) {
                            spinner2.setSelection(j);
                        }
                    }
                }
                SharedValues.setSelectedPatientState(pCursor.getString(6).toString());
                pCursor.close();

                db.close();

            }

        } catch (Exception e) {
            e.getStackTrace();
        }


        EditText fatherheightTo = (EditText) findViewById(R.id.fatherheight);
        fatherheightTo.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Your query to fetch Data
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    midParentHeight();
                    //Your query to fetch Data
                } else {
                    EditText midParentalHeight = (EditText) findViewById(R.id.midParentalHeight);
                    midParentalHeight.setText("");
                    midParentalHeight.setEnabled(false);
                    midParentalHeight.setTextColor(Color.DKGRAY);
                }
            }
        });

        EditText searchTo = (EditText) findViewById(R.id.motherheight);
        searchTo.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Your query to fetch Data
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    midParentHeight();
                    //Your query to fetch Data
                } else {
                    EditText midParentalHeight = (EditText) findViewById(R.id.midParentalHeight);
                    midParentalHeight.setText("");
                    midParentalHeight.setEnabled(false);
                    midParentalHeight.setTextColor(Color.DKGRAY);
                }
            }
        });

        Spinner genderSelected = (Spinner) findViewById(R.id.patientgenderlist);
        genderSelected.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String selected = arg0.getItemAtPosition(arg2).toString();
                //System.out.println("SELECTED GENDER " + selected);
                //Based on selected state.. Array of Cities will be load
                if (selected.equalsIgnoreCase("Male")) {
                    midParentHeight();
                } else if (selected.equalsIgnoreCase("Female")) {
                    midParentHeight();
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub.

            }

        });

        Spinner stateSelected = (Spinner) findViewById(R.id.patientstate);
        stateSelected.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String selected = arg0.getItemAtPosition(arg2).toString();
                //System.out.println("SELECTED " + selected);

                Resources res = getResources();
                Spinner spinnerCity = (Spinner) findViewById(R.id.patientcity);
                String[] city = null;


                //Based on selected state.. Array of Cities will be load
                if (selected.equalsIgnoreCase("Select State")) {
                    city = res.getStringArray(R.array.SelectCity);
                } else if (selected.equalsIgnoreCase("Karnataka")) {
                    city = res.getStringArray(R.array.Karnataka);
                } else if (selected.equalsIgnoreCase("Tamilnadu")) {
                    city = res.getStringArray(R.array.Tamilnadu);
                } else if (selected.equalsIgnoreCase("Kerala")) {
                    city = res.getStringArray(R.array.Kerala);
                } else if (selected.equalsIgnoreCase("Maharashtra")) {
                    city = res.getStringArray(R.array.Maharashtra);
                } else if (selected.equalsIgnoreCase("Assam")) {
                    city = res.getStringArray(R.array.Assam);
                } else if (selected.equalsIgnoreCase("West Bengal")) {
                    city = res.getStringArray(R.array.WestBengal);
                } else if (selected.equalsIgnoreCase("Uttar Pradesh")) {
                    city = res.getStringArray(R.array.UttarPradesh);
                } else if (selected.equalsIgnoreCase("Uttarakhand")) {
                    city = res.getStringArray(R.array.Uttarakhand);
                } else if (selected.equalsIgnoreCase("Tripura")) {
                    city = res.getStringArray(R.array.Tripura);
                } else if (selected.equalsIgnoreCase("Telangana")) {
                    city = res.getStringArray(R.array.Telangana);
                } else if (selected.equalsIgnoreCase("Sikkim")) {
                    city = res.getStringArray(R.array.Sikkim);
                } else if (selected.equalsIgnoreCase("Rajasthan")) {
                    city = res.getStringArray(R.array.Rajasthan);
                } else if (selected.equalsIgnoreCase("Punjab")) {
                    city = res.getStringArray(R.array.Punjab);
                } else if (selected.equalsIgnoreCase("Nagaland")) {
                    city = res.getStringArray(R.array.Nagaland);
                } else if (selected.equalsIgnoreCase("Mizoram")) {
                    city = res.getStringArray(R.array.Mizoram);
                } else if (selected.equalsIgnoreCase("Meghalaya")) {
                    city = res.getStringArray(R.array.Meghalaya);
                } else if (selected.equalsIgnoreCase("Manipur")) {
                    city = res.getStringArray(R.array.Manipur);
                } else if (selected.equalsIgnoreCase("Madhya Pradesh")) {
                    city = res.getStringArray(R.array.MadhyaPradesh);
                } else if (selected.equalsIgnoreCase("Jharkhand")) {
                    city = res.getStringArray(R.array.Jharkhand);
                } else if (selected.equalsIgnoreCase("Jammu Kashmir")) {
                    city = res.getStringArray(R.array.JammuKashmir);
                } else if (selected.equalsIgnoreCase("Himachal Pradesh")) {
                    city = res.getStringArray(R.array.HimachalPradesh);
                } else if (selected.equalsIgnoreCase("Haryana")) {
                    city = res.getStringArray(R.array.Haryana);
                } else if (selected.equalsIgnoreCase("Gujarat")) {
                    city = res.getStringArray(R.array.Gujarat);
                } else if (selected.equalsIgnoreCase("Goa")) {
                    city = res.getStringArray(R.array.Goa);
                } else if (selected.equalsIgnoreCase("Chhattisgarh")) {
                    city = res.getStringArray(R.array.Chhattisgarh);
                } else if (selected.equalsIgnoreCase("Bihar")) {
                    city = res.getStringArray(R.array.Bihar);
                } else if (selected.equalsIgnoreCase("Andhra Pradesh")) {
                    city = res.getStringArray(R.array.AndhraPradesh);
                } else if (selected.equalsIgnoreCase("Arunachal Pradesh")) {
                    city = res.getStringArray(R.array.ArunachalPradesh);
                }

                ArrayAdapter<String> adp = new ArrayAdapter<String>(
                        getApplicationContext(), android.R.layout.simple_spinner_item,
                        city) {
                    @Override
                    public View getView(int position, View convertView,
                                        ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);

                        TextView textView = (TextView) view.findViewById(android.R.id.text1);

                        //*YOUR CHOICE OF COLOR*//*
                        textView.setTextColor(Color.BLACK);

                        return view;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        TextView lbl = (TextView) super.getView(position, convertView, parent);
                        lbl.setTextColor(Color.BLACK);
                        lbl.setTextSize(18);
                        lbl.setText(getItem(position));
                        return lbl;
                    }
                };
                spinnerCity.setAdapter(adp);
                setSelectedCity(city); //set Selected City


            }

            public void setSelectedCity(String[] cityList) {
                Spinner spinnerCity = (Spinner) findViewById(R.id.patientcity);
                if (SharedValues.getSelectedPatientCity() != null && SharedValues.getSelectedPatientCity().length() > 0) {
                    int cityCount = cityList.length;
                    if (cityCount > 0) {
                        for (int j = 0; j < cityCount; j++) {
                            if (cityList[j].equalsIgnoreCase(SharedValues.getSelectedPatientCity())) {
                                spinnerCity.setSelection(j);
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub.

            }

        });

        EditText patientage = (EditText) findViewById(R.id.patientage);
//        final EditText patientdobValue = (EditText) findViewById(R.id.patientdob);
        patientage.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Your query to fetch Data
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try{
                    if (s.length() > 0) {
                        String dob = s.toString();
                        if (dob.contains(".")) {
                            Log.i(TAG, "String contains dot");
                            String[] parts = dob.split("[.]");

                            if(parts.length==2){
                                int month1 = Integer.valueOf(parts[0]);
                                int month2 = Integer.valueOf(parts[1]);

                                int monthTotal = month1 * 12 + month2;

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.MONTH, -monthTotal);
                                Log.i(TAG, "converted Date : " + calendar.getTime());

                                String convertedDate = DateUtils.dateToString(DateUtils.DATE_DD_MM_YYYY_FRONT_SLASH, calendar.getTime());

                                Log.i(TAG, "converted Date In String : " + convertedDate);
//                                patientdobValue.setText(convertedDate);
                            }

                        } else {
                            Log.i(TAG, "String doesnot contains dot");
                            double age = Double.parseDouble(s.toString());
                            calculateDateOfBirth(age);
                        }
                        //Your query to fetch Data
                    }
                }catch (Exception e){

                }

            }
        });

    }


    private void setDateTimeField() {

        try {
            pdob.setOnClickListener(this);
            Calendar newCalendar = Calendar.getInstance();

            toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {


                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    //pdob.setText(dateFormatter.format(newDate.getTime()));
                    date1 = dateFormatter.format(newDate.getTime());
                    // Toast.makeText(getApplicationContext(), "date1 String :" + date1, Toast.LENGTH_SHORT).show();
                    try {
                        Date dateOfBo = dateFormatter.parse(date1);
                        getAge(dateOfBo);
                        pdob.setText(dateFormatter.format(dateOfBo));
                        System.out.println("age original control " + dateOfBo);

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


    @Override
    public void onClick(View view) {
        if (view == pdob) {
            //  toDatePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis()-20000);
            try {
                toDatePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                toDatePickerDialog.show();
            }catch (Exception e){
                e.printStackTrace();
            }



        }
    }

    //Calendar myCalendar = Calendar.getInstance();

    /* DatePickerDialog.OnDateSetListener date = new
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

             };*/
    Calendar myCalendar = Calendar.getInstance();

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        pdob.setText(sdf.format(myCalendar.getTime()));
    }

    public void patientSaveCancelAction(View view) {

        alertDialog();

       /* Intent intent = new Intent();
        intent.setClass(getApplicationContext(), PatientsListActivity.class);
        startActivity(intent);*/
    }

    /*
    To Save Patient Data.
    Uses DataFeedEntry where column names are mentioned.
    */
    public void patientSaveAction(View view) throws Exception {
        // Intent intent = new Intent() ;
        // intent.setClass(getApplicationContext(), PatientInformation.class) ;
        // startActivity(intent);
        error_count = 0;
        Button saveButton = (Button) view.findViewById(R.id.patientsavebutton);

        //This sharedvalues is used to store docid and patientid, to be used in other intent.
        // SharedValues sharedValues = ((SharedValues)getApplicationContext()) ;

        //  SharedValues sharedValues = new SharedValues() ;

        //Save Patient Data
        ContentValues values = new ContentValues();

        SQLiteDatabase db = DataBaseHelper.myDBHelper.getReadableDatabase();
        Cursor docCursor = db.query(DBFeedReaderContract.FeedEntryDoctor.TABLE_NAME, null, null, null, null, null, null, null);
        docCursor.moveToFirst();
        String docId = docCursor.getString(1).toString();
        //docCursor.close();
        values.put(DBFeedReaderContract.FeedEntryPatients.P_DOCTOR_UUID, docId);
        SharedValues.setDocid(docId);

        EditText pdata = (EditText) findViewById(R.id.patientname);
        fieldValidationOnSave(pdata, "Patient name is empty");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_NAME, pdata.getText().toString());


        String dob = selectedDate.toString();
        String dateofBirth = selectedDateFormat;
        System.out.println("db " + dob);
        System.out.println("dateofBirth " + dateofBirth);
//        fieldValidationOnSave(pdata, "Patient date of birth is empty");
        SharedValues.setSelectedPatientDOB(dateofBirth.toString());
        values.put(DBFeedReaderContract.FeedEntryPatients.P_DOB, dateofBirth.toString());


        Spinner pgender = (Spinner) findViewById(R.id.patientgenderlist);
        values.put(DBFeedReaderContract.FeedEntryPatients.P_Gender, pgender.getSelectedItem().toString());
        SharedValues.setSelectedPatientGender(pgender.getSelectedItem().toString());

        Spinner pstate = (Spinner) findViewById(R.id.patientstate);
       /* if(pstate.getSelectedItem().toString().equals("Select State")){
            Toast.makeText(this,"Please select state", Toast.LENGTH_SHORT).show();
            pstate.setFocusable(true);
            pstate.setFocusableInTouchMode(true);
            error_count++;
        }*/
        values.put(DBFeedReaderContract.FeedEntryPatients.P_STATE, pstate.getSelectedItem().toString());
        SharedValues.setSelectedPatientState(pstate.getSelectedItem().toString());


        Spinner pCity = (Spinner) findViewById(R.id.patientcity);
        // fieldValidationOnSave(pdata, "City is empty..");
      /*  if(pCity.getSelectedItem().toString().equals("Select City")){
            Toast.makeText(this,"Please select city", Toast.LENGTH_SHORT).show();
            pCity.setFocusable(true);
            pCity.setFocusableInTouchMode(true);
            error_count++;
        }*/
        values.put(DBFeedReaderContract.FeedEntryPatients.P_CITY, pCity.getSelectedItem().toString());


        // values.put(DBFeedReaderContract.FeedEntryPatients.P_STATE, pdata.getText().toString());

      /*  pdata = (EditText) findViewById(R.id.fathername);
        fieldValidationOnSave(pdata);
        values.put(DBFeedReaderContract.FeedEntryPatients.P_FATHERNAME, pdata.getText().toString());

        pdata = (EditText) findViewById(R.id.mothername);
        fieldValidationOnSave(pdata);
        values.put(DBFeedReaderContract.FeedEntryPatients.P_MOTHERNAME, pdata.getText().toString());*/

        pdata = (EditText) findViewById(R.id.fatherheight);
        fieldValidationOnSave(pdata, "Father height is empty..");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_FATHERHEIGHT, pdata.getText().toString());

        pdata = (EditText) findViewById(R.id.patientage);
        fieldValidationOnSave(pdata, "Age is empty..");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_AGE, pdata.getText().toString());
        /*double age =Double.parseDouble(pdata.getText().toString());
        double days = age*365; //547.5 days
        //System.out.println("DAys : "+days);
        double month = days/30; //18
        values.put(DBFeedReaderContract.FeedEntryPatients.P_AGE, String.valueOf(month));*/


        pdata = (EditText) findViewById(R.id.motherheight);
        fieldValidationOnSave(pdata, "Mother height is empty");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_MOTHERHEIGHT, pdata.getText().toString());
        values.put(DBFeedReaderContract.FeedEntryPatients.P_UUID, UUID.randomUUID().toString());

        SQLiteDatabase myDataBase = DataBaseHelper.myDBHelper.getWritableDatabase();

        if (saveButton.getText().toString() != "Update") {

            // Insert the new row, returning the primary key value of the new row
            long newRowId;

            if (error_count == 0) {
                newRowId = myDataBase.insert(
                        DBFeedReaderContract.FeedEntryPatients.TABLE_NAME,
                        DBFeedReaderContract.FeedEntryPatients.P_ID,
                        values);

                //sharedValues.setPatientid(String.valueOf(newRowId));
                //SharedValues.setPatientid(String.valueOf(newRowId));

                SharedValues.setSelectedPatientId(String.valueOf(newRowId));

                EditText pname = (EditText) findViewById(R.id.patientname);
                SharedValues.setSelectedPatientName(pname.getText().toString());

//                EditText pdob = (EditText) findViewById(R.id.patientdob);
                SharedValues.setSelectedPatientDOB(selectedDateFormat.toString());

                Spinner pGender = (Spinner) findViewById(R.id.patientgenderlist);
                SharedValues.setSelectedPatientGender(pGender.getSelectedItem().toString());


                // String labelText = "Growth Data - PID - " + String.valueOf(newRowId) + "-" + docId;
                String labelText = "Growth Data - PID - " + SharedValues.getSelectedPatientId() + "-" + SharedValues.getDocid();
                PID = labelText;

                GeneratedPatientID = newRowId;

                Button saveButtonChange = (Button) findViewById(R.id.patientsavebutton);
                saveButtonChange.setText("Update");

                Toast toast = Toast.makeText(this, "Data saved successfully", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                PatientInformation.tabHost.setCurrentTabByTag("Data");
            }

        } else {
            if (error_count == 0) {
                if (SharedValues.getSelectedPatientId() == null) {
                    myDataBase.update(DBFeedReaderContract.FeedEntryPatients.TABLE_NAME, values, "_id" + "=" + String.valueOf(GeneratedPatientID), null);
                    SharedValues.setSelectedPatientId(String.valueOf(GeneratedPatientID));

                    EditText pname = (EditText) findViewById(R.id.patientname);
                    SharedValues.setSelectedPatientName(pname.getText().toString());

//                    EditText pdob = (EditText) findViewById(R.id.patientdob);
//                    SharedValues.setSelectedPatientDOB(pdob.getText().toString());
                    SharedValues.setSelectedPatientDOB(selectedDateFormat.toString());

                    Spinner sdata = (Spinner) findViewById(R.id.patientgenderlist);
                    SharedValues.setSelectedPatientGender(sdata.getSelectedItem().toString());
                    Toast toast = Toast.makeText(this, "Data updated successfully", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    myDataBase.update(DBFeedReaderContract.FeedEntryPatients.TABLE_NAME, values, "_id" + "=" + SharedValues.getSelectedPatientId(), null);
                    Toast toast = Toast.makeText(this, "Data updated successfully", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
            }
        }

    }

    public void fieldValidationOnSave(EditText editText, String message) {
        if (editText.getText().toString().equals("")) {
            editText.setError(message);
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            error_count++;
        }
    }


    public void fieldValidationOnSaveforDate(EditText text) {

    }

    //For Validations
    private class EmptyTextListener implements TextView.OnEditorActionListener {
        private EditText et;

        public EmptyTextListener(EditText editText) {
            this.et = editText;
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if (5 == EditorInfo.IME_ACTION_NEXT) {
                // Called when user press Next button on the soft keyboard

                if (et.getText().toString().equals(""))
                    et.setError("Oops! empty."); //, error_indicator);
            }
            return false;
        }
    }

    /* Alert Dialog*/

    private void alertDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PatientBasicInputs.this);

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


                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), PatientsListActivity.class);
                startActivity(intent);

                /*
                Intent var = new Intent(getApplicationContext(),MyPass.class);
                finish();
                startActivity(var)*/
                ;
                //Toast.makeText(getApplicationContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();

                //dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    //midParentalHeight
    public void midParentHeight() {

        EditText fatherHeight = (EditText) findViewById(R.id.fatherheight);
        EditText motherHeight = (EditText) findViewById(R.id.motherheight);
        Spinner pgender = (Spinner) findViewById(R.id.patientgenderlist);
        String gender = pgender.getSelectedItem().toString();

        EditText midParentalHeight1 = (EditText) findViewById(R.id.midParentalHeight);

        String mHeight = motherHeight.getText().toString().trim();
        String fHeight = fatherHeight.getText().toString().trim();
        Double result = null;

       /* System.out.println("midParentHeight statss" + mHeight);

        System.out.println("midParentHeight statss" + fHeight);*/
        try {
            if (mHeight.isEmpty() || fHeight.isEmpty()) {
                midParentalHeight1.setText("");
                midParentalHeight1.setEnabled(false);
                midParentalHeight1.setTextColor(Color.DKGRAY);
            }

            if (gender.equalsIgnoreCase("Male")) {
                if (!mHeight.isEmpty() && !fHeight.isEmpty()) {
                    // (Father's Height + Mother's Height + 13) / 2
                    if (!Double.valueOf(fHeight).isNaN() && !Double.valueOf(mHeight).isNaN()) {
                        result = (Double.valueOf(fHeight) + Double.valueOf(mHeight) + 13) / 2;
                        DecimalFormat df = new DecimalFormat("#.##");
                        Double doubleValue = Double.valueOf(df.format(result));
                        SharedValues.setMidParentalHeight(doubleValue.longValue());
                        midParentalHeight1.setText(doubleValue.toString());
                        midParentalHeight1.setEnabled(false);
                        midParentalHeight1.setTextColor(Color.DKGRAY);
                    }
                } /*else {
                       // Toast toast = Toast.makeText(this, "Ooops cant calculate Parental Height", Toast.LENGTH_LONG);
                        //toast.setGravity(Gravity.CENTER, 0, 0);
                        //toast.show();
                    }*/
            } else if (gender.equalsIgnoreCase("Female")) {
                if (!mHeight.isEmpty() && !fHeight.isEmpty()) {
                    //(Father's Height - 13 + Mother's Height) / 2
                    if (!Double.valueOf(fHeight).isNaN() && !Double.valueOf(mHeight).isNaN()) {
                        result = (Double.valueOf(fHeight) - 13 + Double.valueOf(mHeight)) / 2;
                        // double doubleValue = 200.3456;
                        DecimalFormat df = new DecimalFormat("#.##");
                        Double doubleValue = Double.valueOf(df.format(result));
                        SharedValues.setMidParentalHeight(doubleValue.longValue());
                        midParentalHeight1.setText(doubleValue.toString());
                        midParentalHeight1.setEnabled(false);
                        midParentalHeight1.setTextColor(Color.DKGRAY);
                    }
                }/* else {
                        Toast toast = Toast.makeText(this, "Ooops cant calculate Parental Height", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getAge(Date birthDate) {
        System.out.println("birthDate " + birthDate);
        int years = 0;
        int months = 0;
        int days = 0;
        EditText patientAgeText = (EditText) findViewById(R.id.patientage);
        //create calendar object for birth day
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(birthDate.getTime());
        //create calendar object for current day
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);
        //Get difference between years
        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;
        //Get difference between months
        months = currMonth - birthMonth;
        //if month difference is in negative then reduce years by one and calculate the number of months.
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                months--;
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }
        //Calculate the days
        if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
            days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else {
            days = 0;
            if (months == 12) {
                years++;
                months = 0;
            }
        }
        patientAgeText.setText(years + "." + months);
        return years + "." + months;
    }

    public void calculateDateOfBirth(double age) {
//        EditText patientDOBText = (EditText) findViewById(R.id.patientdob);

        // double age = 2.5;
        double days = age * 365; //547.5 days
        double month = days / 30; //18
        double year = Math.round(month) / 12;
        double monthsReminder = Math.round(month) % 12;

        Calendar now = Calendar.getInstance();
        int currMonth = now.get(Calendar.MONTH) + 1;
        int currYear = now.get(Calendar.YEAR);
        int currentDate = now.get(Calendar.DATE);
        String dobd = null;
//        String patientDOB = patientDOBText.getText().toString();
//        if (patientDOB != null && patientDOB.length() > 0) {
//            int currentYear = now.get(Calendar.YEAR);
//            String[] arr = patientDOB.split("/");
//            Double yearValue = Double.valueOf(year);
//
//            int calculatedyear = currentYear - yearValue.intValue();
//            dobd = arr[0] + "/" + arr[1] + "/" + calculatedyear;
//        } else {
//            dobd = currentDate + "/" + currMonth + "/" + (currYear - Math.round(year));
//        }


        //System.out.println("DATE OF BIRTH:"+dobd);
//        patientDOBText.setText(dobd.toString());
    }


    /*public void getAge() {

        int age;

        EditText patientDOBText = (EditText) findViewById(R.id.patientdob);
        EditText patientAgeText = (EditText) findViewById(R.id.patientage);

        String patientDOB = patientDOBText.getText().toString();
        String patientAge =  patientAgeText.getText().toString();
        int DOByear = 0 ;
        int DOBmonth = 0 ;
        int DOBday = 0;
        if(patientDOB != null && patientDOB.length() > 0){
              String[] dob = patientDOB.split("/");
              DOByear = Integer.parseInt(dob[2]);
              DOBmonth = Integer.parseInt(dob[1]) ;
              DOBday = Integer.parseInt(dob[0]);
        }

        final Calendar calenderToday = Calendar.getInstance();
        int currentYear = calenderToday.get(Calendar.YEAR);
        int currentMonth = 1 + calenderToday.get(Calendar.MONTH);
        int todayDay = calenderToday.get(Calendar.DAY_OF_MONTH);

        age = currentYear - DOByear;

        if(DOBmonth > currentMonth){
            --age;
        }
        else if(DOBmonth == currentMonth){
            if(DOBday > todayDay){
                --age;
            }
        }

        String ageS = Integer.toString(age);

        patientAgeText.setText(ageS.toString());

    }*/

    /*public void calculateDateOfBirth(){
        System.out.println("calculateDateOfBirth Starts");
        EditText patientDOBText = (EditText) findViewById(R.id.patientdob);
        EditText patientAgeText = (EditText) findViewById(R.id.patientage);

        String patientDOB = patientDOBText.getText().toString();
        String patientAge =  patientAgeText.getText().toString();


        if (patientAge != null || patientAge.length() > 0) {

            Integer currentYear = null;
            Integer currentMonth = null;
            Integer currentDay = null;
            Integer approximateYear = null;

            final Calendar calenderToday = Calendar.getInstance();


            if(patientDOB != null || patientDOB.length() > 0){
                String[] ds = patientDOB.split("/");
                currentYear = calenderToday.get(Calendar.YEAR);
                currentMonth = Integer.parseInt(ds[1]);
                currentDay = Integer.parseInt(ds[0]);
                approximateYear= currentYear - (Integer.parseInt(patientAge));


            }else {

                currentYear = calenderToday.get(Calendar.YEAR);
                currentMonth = 1 + calenderToday.get(Calendar.MONTH);
                currentDay = calenderToday.get(Calendar.DAY_OF_MONTH);
                approximateYear= currentYear - (Integer.parseInt(patientAge));
            }

            //String myFormat = "dd/MM/yyyy";
            String dob = currentDay + "/" + (currentMonth ) + "/" + (approximateYear);
            patientDOBText.setText(dob.toString());
            patientDOBText.setTextColor(Color.BLACK);

        }

        System.out.println("calculateDateOfBirth Ends");

    }
*/


}


