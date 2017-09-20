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

/**
 * Created by David on 7/17/17.
 */
public class EstimateContentProvider extends ContentProvider {

    private EstimateDbAdapter dbAdapter;

    private static final String AUTHORITY = "com.gobluegreen.app.data.estimate.provider";
    private static final String BASE_PATH = "estimate";

    private static final int ESTIMATE = 100;
    private static final int ESTIMATE_CUSTOMER = 101;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + BASE_PATH;

    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, ESTIMATE);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/" + EstimateDbAdapter.CUSTOMER_TABLE, ESTIMATE_CUSTOMER);
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
            case ESTIMATE:
                return CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor= null;

        switch (uriMatcher.match(uri)) {
            case ESTIMATE:
                cursor = dbAdapter.queryEstimate(EstimateDbAdapter.ESTIMATE_TABLE);
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        long id = 0;

        switch (uriMatcher.match(uri)) {
            case ESTIMATE:
                id = dbAdapter.insertBuilder(EstimateDbAdapter.ESTIMATE_TABLE,values);
                break;

            case ESTIMATE_CUSTOMER:
                id = dbAdapter.insertBuilder(EstimateDbAdapter.CUSTOMER_TABLE,values);
                break;
        }

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






}
