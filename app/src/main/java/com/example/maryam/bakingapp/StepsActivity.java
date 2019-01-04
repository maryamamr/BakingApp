package com.example.maryam.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.maryam.bakingapp.Utils.AppUtils;

public class StepsActivity extends AppCompatActivity {
    public Fragment stepsDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Bundle bundle = getIntent().getExtras();
        if (savedInstanceState == null) {
            stepsDetails = new StepsDetailsFragment();
        } else {
            stepsDetails = getSupportFragmentManager().getFragment(savedInstanceState, AppUtils.STEP_FRAGMENT_TAG);
        }
        stepsDetails.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.steps_container, stepsDetails).commit();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, AppUtils.STEP_FRAGMENT_TAG, stepsDetails);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(StepsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
