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

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.goalsr.kidsgrowth.kidsgrowthcharts.BuildConfig;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.Utils;

/**
 * This class defines a simple FragmentActivity as the parent of {@link PatientDetailFragment}.
 */
public class PatientDetailActivity extends FragmentActivity {
    // Defines a tag for identifying the single fragment that this activity holds
    private static final String TAG = "PatientDetailActivity";

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            // Enable strict mode checks when in debug modes
           // Utils.enableStrictMode();
        }
        super.onCreate(savedInstanceState);

        // This activity expects to receive an intent that contains the uri of a contact
        if (getIntent() != null) {

            // For OS versions honeycomb and higher use action bar
            if (Utils.hasHoneycomb()) {
                // Enables action bar "up" navigation
                getActionBar().setDisplayHomeAsUpEnabled(true);
            }

            // Fetch the data Uri from the intent provided to this activity
            final Uri uri = getIntent().getData();

            // Checks to see if fragment has already been added, otherwise adds a new
            // ContactDetailFragment with the Uri provided in the intent
            if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                // Adds a newly created ContactDetailFragment that is instantiated with the
                // data Uri
                ft.add(android.R.id.content, PatientDetailFragment.newInstance(uri), TAG);
                ft.commit();
            }
        } else {
            // No intent provided, nothing to do so finish()
            finish();
        }
    }

    public void goToVisitData(View view){
        Intent intent2 = new Intent(getApplicationContext(), PatientInformation.class) ;
        intent2.putExtra("info", 1);
        startActivity(intent2);
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
                NavUtils.navigateUpFromSameTask(this);

                return true;
        }
        // Otherwise, pass the item to the super implementation for handling, as described in the
        // documentation.
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
