package com.gobluegreen.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gobluegreen.app.R;

public class EstimateReviewSubmitActivity extends AppCompatActivity {


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, EstimateReviewSubmitActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_review_submit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
