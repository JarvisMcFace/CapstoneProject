package com.gobluegreen.app.util;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.gobluegreen.app.R;

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
        }

        final NavigationView navigationView = (NavigationView) activity.findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(getNavigationItemSelectedListener(weakActivity));
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

    private NavigationView.OnNavigationItemSelectedListener getNavigationItemSelectedListener(final WeakReference<Activity> weakActivity) {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectedDraweritem(weakActivity, menuItem);
                return true;
            }
        };
    }

    private void selectedDraweritem(WeakReference<Activity> weakActivity, MenuItem menuItem) {

        Log.d("David", "David: " + "selectedDraweritem() called with: weakActivity = [" + weakActivity + "], menuItem = [" + menuItem + "]");
    }

}