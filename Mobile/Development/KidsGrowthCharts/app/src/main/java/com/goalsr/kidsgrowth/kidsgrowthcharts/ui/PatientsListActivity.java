/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.goalsr.kidsgrowth.kidsgrowthcharts.ui;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.goalsr.kidsgrowth.kidsgrowthcharts.BuildConfig;
import com.goalsr.kidsgrowth.kidsgrowthcharts.R;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.PreferenceUtils;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.SharedValues;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.Utils;

import com.goalsr.kidsgrowth.kidsgrowthcharts.db.* ;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FragmentActivity to hold the main {@link PatientsListFragment}. On larger screen devices which
 * can fit two panes also load {@link PatientDetailFragment}.
 */
public class PatientsListActivity extends FragmentActivity implements
        PatientsListFragment.OnContactsInteractionListener {

    // Defines a tag for identifying log entries
    private static final String TAG = "PatientsListActivity";

    private PatientDetailFragment mContactDetailFragment;

    // If true, this is a larger screen device which fits two panes
    private boolean isTwoPaneLayout;

    // True if this activity instance is a search result view (used on pre-HC devices that load
    // search results in a separate instance of the activity rather than loading results in-line
    // as the query is typed.
    private boolean isSearchResultView = false;

    private static String connectionInfo = "" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
         //  Utils.enableStrictMode();
        }
        super.onCreate(savedInstanceState);


/*
        DataBaseHelper myDBHelper = new DataBaseHelper(this) ;
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.getDeviceId();
        String deviceId = telephonyManager.getDeviceId();
        //Toast.makeText(this,"IMEI NUMBER: "+deviceId, Toast.LENGTH_LONG).show();
        try
        {
            myDBHelper.createDataBase(deviceId);


        }
        catch (IOException ioe)
        {
            throw new Error("Unable to create database") ;

        }
*/

        // Set main content view. On smaller screen devices this is a single pane view with one
        // fragment. One larger screen devices this is a two pane view with two fragments.
        setContentView(R.layout.activity_main);

        /*Goalsr Shrishail Enabling home button */

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);

        // Check if two pane bool is set based on resource directories
        isTwoPaneLayout = getResources().getBoolean(R.bool.has_two_panes);

        // Check if this activity instance has been triggered as a result of a search query. This
        // will only happen on pre-HC OS versions as from HC onward search is carried out using
        // an ActionBar SearchView which carries out the search in-line without loading a new
        // Activity.
        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {

            // Fetch query from intent and notify the fragment that it should display search
            // results instead of all contacts.
            String searchQuery = getIntent().getStringExtra(SearchManager.QUERY);
            PatientsListFragment mContactsListFragment = (PatientsListFragment)
                    getSupportFragmentManager().findFragmentById(R.id.contact_list);

            // This flag notes that the Activity is doing a search, and so the result will be
            // search results rather than all contacts. This prevents the Activity and Fragment
            // from trying to a search on search results.
            isSearchResultView = true;
            mContactsListFragment.setSearchQuery(searchQuery);

            // Set special title for search results
            String title =getString(R.string.contacts_list_search_results_title, searchQuery);
            setTitle(title);
        }

        if (isTwoPaneLayout) {
            // If two pane layout, locate the contact detail fragment
            mContactDetailFragment = (PatientDetailFragment)
                    getSupportFragmentManager().findFragmentById(R.id.contact_detail);
        }
    }

    /**
     * This interface callback lets the main contacts list fragment notify
     * this activity that a contact has been selected.
     *
     * @param contactUri The contact Uri to the selected contact.
     */
    @Override
    public void onContactSelected(Uri contactUri) {
        if (isTwoPaneLayout && mContactDetailFragment != null) {
            // If two pane layout then update the detail fragment to show the selected contact
            //SharedValues.setSelectedPatientId(contactUri.) ;
            mContactDetailFragment.setContact();
        } else {
            // Otherwise single pane layout, start a new ContactDetailActivity with
            // the contact Uri
            Intent intent = new Intent(this, PatientDetailActivity.class);
            intent.setData(contactUri);
            startActivity(intent);
           // Toast.makeText(this, "Clicked On selected contact", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This interface callback lets the main contacts list fragment notify
     * this activity that a contact is no longer selected.
     */
    @Override
    public void onSelectionCleared() {
        if (isTwoPaneLayout && mContactDetailFragment != null) {
            mContactDetailFragment.setContact();
        }
    }

    @Override
    public boolean onSearchRequested() {
        // Don't allow another search if this activity instance is already showing
        // search results. Only used pre-HC.
        return !isSearchResultView && super.onSearchRequested();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        switch (id) {

            case R.id.menu_setting:
                Intent i = new Intent(getApplicationContext(),SyncData.class);
                startActivity(i);
                finish();

            case android.R.id.home:
                finish();


                return true;
        }
        return super.onOptionsItemSelected(item);

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.menu_setting) {


           Intent i = new Intent(getApplicationContext(),SyncData.class);
            startActivity(i);
            finish();*/
           // syncData();

            // Toast.makeText(this,"Chart is downloaded successfully",Toast.LENGTH_LONG).show();

        }





    public void syncData() {
        // Is the button now checked?
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PatientsListActivity.this);

        // Setting Dialog Title
        //  alertDialog.setTitle("Confirm Log out");

        // Setting Dialog Message
        alertDialog.setMessage("Do you really want to sync data?");

        // Setting Icon to Dialog
        //  alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event

                //setEnableSyncData();
                PreferenceUtils.set(getApplicationContext(), "sync_enabled", Boolean.TRUE);
                //System.out.println(PreferenceUtils.getBoolean(getApplicationContext(), "sync_enabled"));
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
                //setDisableSyncData();
                PreferenceUtils.set(getApplicationContext(), "sync_enabled", Boolean.FALSE);
                System.out.println(PreferenceUtils.getBoolean(getApplicationContext(), "sync_enabled"));
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), PatientsListActivity.class);
                startActivity(intent);
                //dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }





}
