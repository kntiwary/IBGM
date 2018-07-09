package com.goalsr.kidsgrowth.kidsgrowthcharts.db;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.*;
import android.os.Environment;

import com.goalsr.kidsgrowth.kidsgrowthcharts.util.SharedValues;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Vinayaka on 12/3/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "//data/data/com.goalsr.kidsgrowth.kidsgrowthcharts/databases/";

    private static String DB_NAME = "AndroidTestDB";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    public static DataBaseHelper myDBHelper;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
        myDBHelper = this;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase(String deviceId) throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            //do nothing - database already exist
        } else {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase(deviceId);

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            /*File outFile = new File(Environment.getDataDirectory(), DB_NAME);
            outFile.setWritable(true);*/
            File file = new File(myPath);
            if (file.exists() && !file.isDirectory()) {
                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
                checkDB.close();
            }

        } catch (SQLiteException e) {

            //database does't exist yet.
            e.printStackTrace();

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase(String deviceId) throws IOException {


        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

        //Unique Random ID generated for doctor.
        String docUID = deviceId;

        final String TEXT_TYPE = " TEXT";
        final String COMMA_SEP = ",";
        final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBFeedReaderContract.FeedEntryPatients.TABLE_NAME + " (" +
                        DBFeedReaderContract.FeedEntryPatients.P_ID + " INTEGER PRIMARY KEY," +
                        DBFeedReaderContract.FeedEntryPatients.P_DOCTOR_UUID + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryPatients.P_NAME + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryPatients.P_DOB + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryPatients.P_Gender + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryPatients.P_CITY + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryPatients.P_STATE + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryPatients.P_FATHERNAME + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryPatients.P_MOTHERNAME + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryPatients.P_FATHERHEIGHT + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryPatients.P_MOTHERHEIGHT + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryPatients.P_UUID + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryPatients.P_AGE + TEXT_TYPE +
                        // Any other options for the CREATE command
                        " )";
        this.myDataBase = this.getWritableDatabase();

        this.myDataBase.execSQL(SQL_CREATE_ENTRIES);

        final String SQL_CREATE_ENTRY_FOR_DOCTOR =
                "CREATE TABLE " + DBFeedReaderContract.FeedEntryDoctor.TABLE_NAME + " (" +
                        DBFeedReaderContract.FeedEntryDoctor.D_ID + "INTEGER PRIMARY KEY," +
                        DBFeedReaderContract.FeedEntryDoctor.D_UUID + TEXT_TYPE +
                        " )";
        this.myDataBase.execSQL(SQL_CREATE_ENTRY_FOR_DOCTOR);

        final String SQL_CREATE_ENTRIES_FOR_VISIT =
                " CREATE TABLE " + DBFeedReaderContract.FeedEntryVisits.TABLE_NAME + " (" +
                        DBFeedReaderContract.FeedEntryVisits.V_ID + " INTEGER PRIMARY KEY," +
                        DBFeedReaderContract.FeedEntryVisits.V_PATIENT_UUID + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryVisits.V_DOCTOR_UUID + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryVisits.V_VISITDATE + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryVisits.V_HEIGHT + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryVisits.V_WEIGHT + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryVisits.V_HEADCIRCUMFERENCE + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryVisits.V_BMI + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryVisits.V_AGEYEARS + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryVisits.V_AGEMONTHS + TEXT_TYPE + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryVisits.V_ENTRYDATE + " INTEGER" + COMMA_SEP +
                        DBFeedReaderContract.FeedEntryVisits.V_AGE + TEXT_TYPE +
                        " )";
        this.myDataBase.execSQL(SQL_CREATE_ENTRIES_FOR_VISIT);

        //Content Values for Doctor
        ContentValues docContents = new ContentValues();
        docContents.put(DBFeedReaderContract.FeedEntryDoctor.D_UUID, String.valueOf(docUID));
        this.myDataBase.insert(DBFeedReaderContract.FeedEntryDoctor.TABLE_NAME,
                DBFeedReaderContract.FeedEntryDoctor.D_ID,
                docContents);

       /* //Test Data to keep
        ContentValues values = new ContentValues() ;
        values.put(DBFeedReaderContract.FeedEntryPatients.P_DOCTOR_UUID, String.valueOf(docUID));
        values.put(DBFeedReaderContract.FeedEntryPatients.P_NAME, "Robin Hood");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_DOB, "05/05/1987");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_Gender, "Male");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_CITY, "Bangalore");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_STATE, "Karnataka");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_FATHERNAME, "Singhji");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_MOTHERNAME, "kaurji");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_FATHERHEIGHT, "180");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_MOTHERHEIGHT, "170");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_UUID, UUID.randomUUID().toString());

        // Insert the new row, returning the primary key value of the new row
        this.myDataBase.insert(
                DBFeedReaderContract.FeedEntryPatients.TABLE_NAME,
                DBFeedReaderContract.FeedEntryPatients.P_ID,
                values);

        values = new ContentValues() ;
        values.put(DBFeedReaderContract.FeedEntryPatients.P_DOCTOR_UUID, String.valueOf(docUID));
        values.put(DBFeedReaderContract.FeedEntryPatients.P_NAME, "James Bond");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_DOB, "04/04/1998");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_Gender, "Male");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_CITY, "Bangalore");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_STATE, "Karnataka");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_FATHERNAME, "Buntiji");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_MOTHERNAME, "Kaulji");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_FATHERHEIGHT, "168");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_MOTHERHEIGHT, "158");
        values.put(DBFeedReaderContract.FeedEntryPatients.P_UUID, UUID.randomUUID().toString());

        // Insert the new row, returning the primary key value of the new row
        this.myDataBase.insert(
                DBFeedReaderContract.FeedEntryPatients.TABLE_NAME,
                DBFeedReaderContract.FeedEntryPatients.P_ID,
                values);*/

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        myDataBase.close();

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

}
