package com.gobluegreen.app.data.estimatebuilder;

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

public class EstimateBuilderContentProvider extends ContentProvider {

    private EstimateBuilderDbAdapter dbAdapter;

    private static final String AUTHORITY = "com.gobluegreen.app.data.estimatebuilderprovider";
    private static final String BASE_PATH = "estimatebuilder";

    private static final int ESTIMATE_BUILDER_CODE = 100;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + BASE_PATH;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, ESTIMATE_BUILDER_CODE);
    }

    public EstimateBuilderContentProvider() {
        //intentionally left blank
    }

    @Override
    public boolean onCreate() {
        EstimateBuilderDbAdapter estimateBuilderDbAdapter = new EstimateBuilderDbAdapter(getContext());
        dbAdapter = estimateBuilderDbAdapter.open();
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case ESTIMATE_BUILDER_CODE:
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
        contentValues.put(EstimateBuilderContract.ESTIMATE_BUILDER_CUSTOMER_FIRST_NAME,customerTO.getFirstName());
        contentValues.put(EstimateBuilderContract.ESTIMATE_BUILDER_CUSTOMER_LAST_NAME,customerTO.getLastName());
        contentValues.put(EstimateBuilderContract.ESTIMATE_BUILDER_CUSTOMER_ADDRESS1,customerTO.getAddress1());
        contentValues.put(EstimateBuilderContract.ESTIMATE_BUILDER_CUSTOMER_CITY,customerTO.getCity());
        contentValues.put(EstimateBuilderContract.ESTIMATE_BUILDER_CUSTOMER_STATE,customerTO.getState());
        contentValues.put(EstimateBuilderContract.ESTIMATE_BUILDER_CUSTOMER_ZIP,customerTO.getZipCode());
        contentValues.put(EstimateBuilderContract.ESTIMATE_BUILDER_CUSTOMER_PHONE_NUMBER,customerTO.getPhoneNumber());

        return contentValues;
    }
}
