package com.gobluegreen.app.service;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.gobluegreen.app.R;
import com.gobluegreen.app.data.estimate.EstimateContentProvider;
import com.gobluegreen.app.data.estimate.ReviewEstimateCursorHelper;
import com.gobluegreen.app.to.CustomerTO;
import com.gobluegreen.app.to.CustomerType;
import com.gobluegreen.app.to.ReviewEstimateTO;
import com.gobluegreen.app.util.ListUtils;
import com.gobluegreen.app.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by David on 9/21/17.
 */

public class ReviewRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String ZERO_VALUE = "0";
    private Application application;
    private int appWidgetId;
    private List<ReviewEstimateTO> reviewEstimateTOs;


    public ReviewRemoteViewFactory(Application application, Intent intent) {
        this.application = application;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        setWidgetEstimates();
    }

    @Override
    public void onCreate() {
        reviewEstimateTOs.clear();
    }

    @Override
    public void onDataSetChanged() {
        setWidgetEstimates();
    }

    @Override
    public void onDestroy() {
        reviewEstimateTOs.clear();
    }

    @Override
    public int getCount() {
        if (reviewEstimateTOs == null) {
            return 0;
        }
        return reviewEstimateTOs.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = new RemoteViews(application.getPackageName(), R.layout.widget_review_item);

        if (ListUtils.isEmpty(reviewEstimateTOs)) {
            return remoteViews;
        }

        ReviewEstimateTO reviewEstimateTO = reviewEstimateTOs.get(position);

        String priceRage = reviewEstimateTO.getPriceEstimatesRange();
        String numberRooms = reviewEstimateTO.getNumberOfRooms();
        String squareFeet = reviewEstimateTO.getEstimatedSqFt();


        CustomerTO customerTO = reviewEstimateTO.getCustomerTO();
        CustomerType customerType = customerTO.getCustomerType();

        switch (customerType) {

            case COMMERCIAL:
                remoteViews.setTextViewText(R.id.widget_customer_type,application.getString(R.string.commercial));
                break;

            case RESIDENTIAL:
                remoteViews.setTextViewText(R.id.widget_customer_type,application.getString(R.string.residential));
              break;
        }


        Calendar calendar = reviewEstimateTO.getEstimateDate();

        String pattern = "MMM dd, yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(calendar.getTimeInMillis());
        String formattedDate = simpleDateFormat.format(date);
        remoteViews.setTextViewText(R.id.widget_date, formattedDate);


        if (StringUtils.isNotEmpty(priceRage) ) {
            remoteViews.setViewVisibility(R.id.review_price_container, View.VISIBLE);
            remoteViews.setTextViewText(R.id.widget_price_estimate_range,priceRage);
        }

        if (!ZERO_VALUE.contains(numberRooms)) {
            remoteViews.setViewVisibility(R.id.review_room_container, View.VISIBLE);
            remoteViews.setTextViewText(R.id.widget_number_rooms,numberRooms);
        }

        if (!ZERO_VALUE.contains(squareFeet)) {
            remoteViews.setViewVisibility(R.id.review_square_feet_container, View.VISIBLE);
            remoteViews.setTextViewText(R.id.widget_square_feet,squareFeet);
        }

        Intent detailFillInIntent = new Intent();
        remoteViews.setOnClickFillInIntent(R.id.widget_stack_view_container, detailFillInIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        RemoteViews remoteViews = new RemoteViews(application.getPackageName(), R.layout.widget_estimate_loading_state);
        return remoteViews;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private void setWidgetEstimates() {
        ContentResolver contentResolver = application.getContentResolver();
        Cursor cursor = contentResolver.query(EstimateContentProvider.CONTENT_URI, null, null, null, null);
        reviewEstimateTOs = ReviewEstimateCursorHelper.retrieveAllEstimates(cursor);
    }
}
