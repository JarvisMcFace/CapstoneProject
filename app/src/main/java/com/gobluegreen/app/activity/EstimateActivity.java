package com.gobluegreen.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gobluegreen.app.R;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.gobluegreen.app.util.CarpetQuoteCacheUtility;

public class EstimateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.cancel_estimate_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.cancel_estimate:

                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setMessage(R.string.cancel_estimate_question)
                        .setPositiveButton(R.string.cancel_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                removeSessionEstimateAndFinsih();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        })
                        .create();
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void removeSessionEstimateAndFinsih() {

        GoBluegreenApplication application = (GoBluegreenApplication) getApplication();
        CarpetQuoteCacheUtility.deleteEstimateInProgress(application);
        application.setEstimateInProgressTO(new EstimateInProgressTO());

        Intent intent = new Intent(this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
