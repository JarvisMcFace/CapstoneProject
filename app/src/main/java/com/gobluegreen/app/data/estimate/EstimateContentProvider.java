package com.gobluegreen.app.data.estimate;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gobluegreen.app.to.CustomerTO;
import com.gobluegreen.app.to.EstimateInProgressTO;

/**
 * Created by David on 7/17/17.
 */

public class EstimateContentProvider extends ContentProvider {

    private EstimateDbAdapter dbAdapter;

    private static final String AUTHORITY = "com.gobluegreen.app.data.estimate.provider";
    private static final String BASE_PATH = "estimate";

    private static final int ESTIMATE_CODE = 100;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + BASE_PATH;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, ESTIMATE_CODE);
    }

    public EstimateContentProvider() {
        //intentionally left blank
    }

    @Override
    public boolean onCreate() {
        EstimateDbAdapter customerDbAdapter = new EstimateDbAdapter(getContext());
        dbAdapter = customerDbAdapter.open();
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case ESTIMATE_CODE:
                return CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor = dbAdapter.queryAllEstimateBuilder();
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final long id = dbAdapter.insertEstimateBuilder(values);

        Context context = getContext();
        if (context != null) {
            ContentResolver contentResolver = context.getContentResolver();
            contentResolver.notifyChange(uri, null);
        }

        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return dbAdapter.deleteAllEstimateBuilder();
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        //intentionally left blank
        return 0;
    }


    public static ContentValues getContentValues(EstimateInProgressTO estimateInProgressTO) {

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();

        ContentValues contentValues = new ContentValues();
//        contentValues.put(CustomerContract.FIRST_NAME,customerTO.getFirstName());
//        contentValues.put(CustomerContract.LAST_NAME,customerTO.getLastName());
//        contentValues.put(CustomerContract.ADDRESS1,customerTO.getAddress1());
//        contentValues.put(CustomerContract.CITY,customerTO.getCity());
//        contentValues.put(CustomerContract.STATE,customerTO.getState());
//        contentValues.put(CustomerContract.ZIP,customerTO.getZipCode());
//        contentValues.put(CustomerContract.PHONE_NUMBER,customerTO.getPhoneNumber());

        return contentValues;
    }
}
