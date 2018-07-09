package com.goalsr.kidsgrowth.kidsgrowthcharts.db ;

import android.provider.BaseColumns;

/**
 * Created by Vinayaka on 12/3/2015.
 */
public final class DBFeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DBFeedReaderContract() {}

    //public  static  abstract  class FeedEntryDoctor {
     //   public static String P_DOCTORID = "" ;
    //}
    /* Inner class that defines the table contents */

    public static abstract class FeedEntryDoctor implements BaseColumns {
        public static final String TABLE_NAME = "Doctors" ;
        public static final String D_ID = "_id" ;
        public static final String D_UUID = "d_uuid" ;
    }

    public static abstract class FeedEntryPatients implements BaseColumns {
        public static final String TABLE_NAME = "Patients";
        public static final String P_ID = "_id";
        public static final String P_NAME = "p_name";
        public static final String P_DOB = "p_dob";
        public static final String P_Gender = "p_gender" ;
        public static final String P_CITY = "p_city" ;
        public static final String P_STATE = "p_state" ;
        public static final String P_FATHERNAME = "p_fathername" ;
        public static final String P_MOTHERNAME = "p_mothername" ;
        public static final String P_DOCTOR_UUID = "p_doctor_uuid" ;
        public static final String P_FATHERHEIGHT = "p_fatherheight" ;
        public static final String P_MOTHERHEIGHT = "p_motherheight" ;
        public static final String P_UUID = "p_uuid";
        public static final String P_AGE = "p_age";
    }

    public static abstract class FeedEntryVisits implements BaseColumns {
        public static final String TABLE_NAME = "Visits";
        public static final String V_ID = "_id";
        public static final String V_PATIENT_UUID = "v_patient_uuid";
        public static final String V_DOCTOR_UUID = "v_doctor_uuid" ;
        public static final String V_VISITDATE = "v_visitdate";
        public static final String V_HEIGHT = "v_height";
        public static final String V_WEIGHT = "v_weight" ;
        public static final String V_HEADCIRCUMFERENCE = "v_headcircumference" ;
        public static final String V_BMI = "v_bmi" ;
        public static final String V_AGEYEARS = "v_ageyears" ;
        public static final String V_AGEMONTHS = "v_agemonths" ;
        public static final String V_ENTRYDATE = "v_entrydate";
        public static final String V_AGE = "v_age";
    }
}
