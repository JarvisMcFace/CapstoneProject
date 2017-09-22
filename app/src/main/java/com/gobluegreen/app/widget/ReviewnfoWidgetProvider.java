package com.gobluegreen.app.widget;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.gobluegreen.app.R;
import com.gobluegreen.app.activity.HomeActivity;
import com.gobluegreen.app.activity.ReviewEstimateActivity;
import com.gobluegreen.app.service.ReviewWidgetService;

/**
 * Implementation of App Widget functionality.
 */
public class ReviewnfoWidgetProvider extends AppWidgetProvider {

    private static final String PROVIDER_NAME = "ReviewInfoWidgetProvier";

    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, ReviewnfoWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);

        onUpdate(context, appWidgetManager, appWidgetIds);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            Intent intent = new Intent(context, ReviewWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_review_stack);
            remoteViews.setRemoteAdapter(R.id.widget_stock_info_stack, intent);
            remoteViews.setEmptyView(R.id.widget_stock_info_stack, R.id.widget_empty_state_view);

            Intent viewStockIntent = new Intent(context, ReviewEstimateActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, viewStockIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.widget_stock_info_stack, pendingIntent);

            Intent mainActivityIntent = new Intent(context, HomeActivity.class);
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            PendingIntent mainPendingIntent = PendingIntent.getActivity(context, appWidgetId, mainActivityIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_empty_state_view, mainPendingIntent);

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_stock_info_stack);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        super.onDisabled(context);
    }


}

