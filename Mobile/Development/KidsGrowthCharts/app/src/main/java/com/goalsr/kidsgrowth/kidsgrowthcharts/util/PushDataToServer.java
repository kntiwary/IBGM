package com.goalsr.kidsgrowth.kidsgrowthcharts.util;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalsr.kidsgrowth.kidsgrowthcharts.db.DataBaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vinayaka on 12/30/2015.
 */
public class PushDataToServer {
    static ArrayList<CHMModel> chmModels;
    Context context;

    public PushDataToServer(Context context){
        this.context = context;
    }

    public void syncData() {

        Log.e("############# Date", "" + PreferenceUtils.getLong(context, "last_sync_time"));

        String query = "select p.p_uuid, p.p_doctor_uuid, p.p_dob, p.p_city, p.p_state, p.p_fatherheight, p.p_motherheight, " +
                "pv.v_visitdate, pv.v_height, pv.v_weight, pv.v_headcircumference, pv.v_bmi, pv.v_entrydate from " +
                "Patients as p, Visits as pv where p._id = pv.v_patient_uuid and v_entrydate >= " + PreferenceUtils.getLong(context, "last_sync_time") + " order by pv.v_entrydate ASC";

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        dataBaseHelper.openDataBase();
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();

      /*  ArrayList<JSONObject> jsonArray = new ArrayList<JSONObject>() ;
        for (int i=0; i < cursor.getCount(); i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("patient_uuid", cursor.getString(0));
                jsonObject.put("doctor_uuid", cursor.getString(1));
                jsonObject.put("dob", cursor.getString(2));
                jsonObject.put("city", cursor.getString(3));
                jsonObject.put("state", cursor.getString(4));
                jsonObject.put("father_height", cursor.getString(5));
                jsonObject.put("mother_height", cursor.getString(6));
                jsonObject.put("visit_date", cursor.getString(7));
                jsonObject.put("height", cursor.getString(8));
                jsonObject.put("weight", cursor.getString(9));
                jsonObject.put("head_circumference", cursor.getString(10));
                jsonObject.put("bmi", cursor.getString(11));
                jsonObject.put("v_entrydate", cursor.getString(12));
                jsonArray.add(jsonObject);
                Log.e("############# json", jsonObject.toString());
            } catch (JSONException jsexception) {
                Log.e("log_tag", "Error:  "+ jsexception.toString());
            }
            cursor.moveToNext();
        }*/
        ArrayList<JSONObject> jsonArray = new ArrayList<JSONObject>();
        chmModels = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            CHMModel chmModel = new CHMModel();
            JSONObject jsonObject = new JSONObject();
            try {
                chmModel.setPatient_uuid(cursor.getString(0));
                chmModel.setDoctor_uuid(cursor.getString(1));
                Date dob = DateUtils.stringToDate(DateUtils.DATE_DD_MM_YYYY_FRONT_SLASH, cursor.getString(2));
                String dobString = DateUtils.dateToString(DateUtils.DATE_YYY_MM_DD_HIPHEN, dob);
                Age age = new Age();
                age = AgeCalculator.calculateAge(dob, new Date());
                chmModel.setAge(String.valueOf(age.getYears()));
                chmModel.setDob(dobString);
                chmModel.setCity(cursor.getString(3));
                chmModel.setState(cursor.getString(4));
                chmModel.setFather_height(cursor.getString(5));
                chmModel.setMother_height(cursor.getString(6));
                Date visitDate = DateUtils.stringToDate(DateUtils.DATE_DD_MM_YYYY_FRONT_SLASH, cursor.getString(7));
                String visitDateString = DateUtils.dateToString(DateUtils.DATE_YYY_MM_DD_HIPHEN, visitDate);
                chmModel.setVisit_date(visitDateString);
                chmModel.setHeight(cursor.getString(8));
                chmModel.setWeight(cursor.getString(9));
                chmModel.setHead_circumference(cursor.getString(10));
                chmModel.setBmi(cursor.getString(11));
                chmModels.add(chmModel);
                Log.e("############# json", jsonObject.toString());
            } catch (Exception ex) {
                Log.e("log_tag", "Error:  " + ex.toString());
            }
            cursor.moveToNext();
        }
        dataBaseHelper.close();
        cursor.close();

      /*  Log.e("############# jsonArray", jsonArray.toString());
        if (jsonArray != null && !jsonArray.isEmpty()) {
            Log.e("############# isEmpty()", jsonArray.toString());

            Calendar calendar = Calendar.getInstance();
            DecimalFormat mFormat = new DecimalFormat("00");

            PreferenceUtils.set(context, "last_sync_time", Long.valueOf(mFormat.format(Calendar.getInstance().get(Calendar.YEAR)) + mFormat.format(Calendar.getInstance().get(Calendar.MONTH) + 1) + mFormat.format(calendar.get(Calendar.DATE)) + mFormat.format(Calendar.getInstance().get(Calendar.HOUR)) + mFormat.format(Calendar.getInstance().get(Calendar.MINUTE)) + mFormat.format(Calendar.getInstance().get(Calendar.SECOND))));
        }*/

        if (chmModels != null && !chmModels.isEmpty()) {
            Log.e("############# isEmpty()", chmModels.toString());
            /*Calendar calendar = Calendar.getInstance();
            DecimalFormat mFormat = new DecimalFormat("00");
            PreferenceUtils.set(context, "last_sync_time", Long.valueOf(mFormat.format(Calendar.getInstance().get(Calendar.YEAR)) + mFormat.format(Calendar.getInstance().get(Calendar.MONTH) + 1) + mFormat.format(calendar.get(Calendar.DATE)) + mFormat.format(Calendar.getInstance().get(Calendar.HOUR)) + mFormat.format(Calendar.getInstance().get(Calendar.MINUTE)) + mFormat.format(Calendar.getInstance().get(Calendar.SECOND))));*/

            System.out.println(PreferenceUtils.getBoolean(this.context, "sync_enabled") +"CHCHCHCC ");
            if(PreferenceUtils.getBoolean(this.context, "sync_enabled")) {

                sendToService();
            }

        }
    }

    UpdateToServerAsync updateToServerAsync = null;
    public void sendToService(){
        updateToServerAsync = new UpdateToServerAsync();
        updateToServerAsync.execute((Void)null);

    }

    public class UpdateToServerAsync extends AsyncTask<Void,Void,Boolean>{

        //Context context;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {


            try {
                String phpServiceLink = "http://52.35.99.82:8080/healthcare/healthCare/insertPatient";
                //System.out.println("MODEL:  "+new ObjectMapper().writeValueAsString(chmModels));


                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<CHMResponse> entity;
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpEntity<ArrayList<CHMModel>> httpEntity = new HttpEntity<ArrayList<CHMModel>>(chmModels, RestUtils.getHeaders(null, ""));
                entity = restTemplate.exchange(phpServiceLink, HttpMethod.PUT, httpEntity, CHMResponse.class);
                //System.out.println("RoutePlan location 1 :"+entity.getBody()[0].getCustomerLocations());
                if(entity!=null){
                    if(entity.getBody()!=null){
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }

            } catch (HttpClientErrorException e) {
                Log.e("UserLoginTask", e.getMessage());
                return false;
            } catch (HttpServerErrorException e) {
                Log.e("UserLoginTask Service", e.getMessage());
                return false;
            } catch (Exception e) {
                Log.e("UserLoginTask Service", e.getMessage());
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                try {
                    Calendar calendar = Calendar.getInstance();
                    DecimalFormat mFormat = new DecimalFormat("00");
                    // Toast.makeText(context,"Updated to server",Toast.LENGTH_LONG).show();
                    PreferenceUtils.set(context, "last_sync_time", Long.valueOf(mFormat.format(Calendar.getInstance().get(Calendar.YEAR)) + mFormat.format(Calendar.getInstance().get(Calendar.MONTH) + 1) + mFormat.format(calendar.get(Calendar.DATE)) + mFormat.format(Calendar.getInstance().get(Calendar.HOUR)) + mFormat.format(Calendar.getInstance().get(Calendar.MINUTE)) + mFormat.format(Calendar.getInstance().get(Calendar.SECOND))));
                }catch(Exception e){

                }
            }
            else
            {
               // Toast.makeText(context, "Not updated to server", Toast.LENGTH_SHORT).show();
            }
        }
    }

   /* private static void sendToService(ArrayList<CHMModel> chmModels) {



    }*/


    /*private static void sendJSONData(JSONObject jsonObjects) {
        try {
            String phpServiceLink = "http://192.168.1.100/healthcare/index.php/healthCare/insertPatient";
            URL url;
            URLConnection urlConn;
            DataOutputStream printout;
            DataInputStream input;
            url = new URL (phpServiceLink);
            urlConn = url.openConnection();
            urlConn.setDoInput (true);
            urlConn.setDoOutput (true);
            urlConn.setUseCaches (false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Host", "android.schoolportal.gr");
            urlConn.connect();

            // Send POST output.
            printout = new DataOutputStream(urlConn.getOutputStream ());
            printout.writeChars(URLEncoder.encode(jsonObjects.toString(), "UTF-8"));
            printout.flush();
            printout.close();

        } catch(Exception e) {
            Log.e("log_tag", "Error:  "+e.toString());
        }
    }*/
}
