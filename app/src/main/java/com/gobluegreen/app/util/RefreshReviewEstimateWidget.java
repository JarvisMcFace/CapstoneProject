package com.gobluegreen.app.util;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;

import com.gobluegreen.app.widget.ReviewnfoWidgetProvider;

/**
 * Created by David on 9/21/2017.
 */
public class RefreshReviewEstimateWidget {

    public static void execute(Application application) {

        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE, null, application, ReviewnfoWidgetProvider.class);
        ComponentName componentName = new ComponentName(application, ReviewnfoWidgetProvider.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.getInstance(application).getAppWidgetIds(componentName));
        application.sendBroadcast(intent);
    }
}
