package com.gobluegreen.app.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.gobluegreen.app.R;
import com.gobluegreen.app.activity.ReviewEstimateActivity;

import java.lang.ref.WeakReference;

/**
 * Created by David on 9/28/17.
 */

public class LeftNavigationDrawer {

    private static LeftNavigationDrawer instance;

    public LeftNavigationDrawer() {
    }

    public static LeftNavigationDrawer getInstance() {
        if (instance == null) {
            instance = new LeftNavigationDrawer();
        }

        return instance;
    }

    public void navOnStart(final WeakReference<Activity> weakActivity) {
        final Activity activity = weakActivity.get();

        DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.nav_drawer_layout);
        if (drawerLayout != null) {
            drawerLayout.addDrawerListener(new DrawerListener(weakActivity));
//            drawerLayout.setDrawerListener(new DrawerListener(weakActivity));
        }

        final NavigationView navigationView = (NavigationView) activity.findViewById(R.id.navigation);
        if (navigationView == null) {
            return;
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectDrawerItem(weakActivity, menuItem);
                return true;
            }
        });
    }

    private void selectDrawerItem(WeakReference<Activity> weakActivity, MenuItem menuItem) {
        Intent drawerIntent = null;
        final Activity activity = weakActivity.get();

        switch (menuItem.getItemId()) {

            case R.id.navigation_locations:
                drawerIntent = ReviewEstimateActivity.newIntent(activity);
                drawerIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }

        DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.nav_drawer_layout);

        if (drawerLayout != null) {
            drawerLayout.closeDrawer(Gravity.START);
        }

        if (drawerIntent != null) {
            Handler handler = new Handler();
            final Intent finalDrawerIntent = drawerIntent;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.startActivity(finalDrawerIntent);
                }
            }, 200);
        }

    }


    private class DrawerListener extends DrawerLayout.SimpleDrawerListener {

        private final WeakReference<Activity> weakActivity;

        public DrawerListener(WeakReference<Activity> weakActivity) {
            this.weakActivity = weakActivity;
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            Activity activity = weakActivity.get();
            activity.invalidateOptionsMenu();

        }

        @Override
        public void onDrawerClosed(View drawerView) {
            Activity activity = weakActivity.get();
            activity.invalidateOptionsMenu();

        }
    }
}
