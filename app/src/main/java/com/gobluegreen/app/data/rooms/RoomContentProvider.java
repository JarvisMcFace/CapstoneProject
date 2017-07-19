package com.gobluegreen.app.data.rooms;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gobluegreen.app.data.customer.CustomerContract;
import com.gobluegreen.app.to.CustomerTO;
import com.gobluegreen.app.to.EstimateInProgressTO;

/**
 * Created by David on 7/17/17.
 */

public class RoomContentProvider extends ContentProvider {

    private RoomsDbAdapter dbAdapter;

    private static final String AUTHORITY = "com.go bluegreen.app.data.roomprovider";
    private static final String BASE_PATH = "room";

    private static final int ROOM_CODE = 100;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + BASE_PATH;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, ROOM_CODE);
    }

    public RoomContentProvider() {
        //intentionally left blank
    }

    @Override
    public boolean onCreate() {
        RoomsDbAdapter roomsDbAdapter = new RoomsDbAdapter(getContext());
        dbAdapter = dbAdapter.open();
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case ROOM_CODE:
                return CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor = dbAdapter.queryAllRooms();
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final long id = dbAdapter.insertRoom(values);

        Context context = getContext();
        if (context != null) {
            ContentResolver contentResolver = context.getContentResolver();
            contentResolver.notifyChange(uri, null);
        }

        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return dbAdapter.deleteAllRoom();
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        //intentionally left blank
        return 0;
    }


    public static ContentValues getContentValues(EstimateInProgressTO estimateInProgressTO) {

        //TODO David
        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CustomerContract.FIRST_NAME,customerTO.getFirstName());
        contentValues.put(CustomerContract.LAST_NAME,customerTO.getLastName());
        contentValues.put(CustomerContract.ADDRESS1,customerTO.getAddress1());
        contentValues.put(CustomerContract.CITY,customerTO.getCity());
        contentValues.put(CustomerContract.STATE,customerTO.getState());
        contentValues.put(CustomerContract.ZIP,customerTO.getZipCode());
        contentValues.put(CustomerContract.PHONE_NUMBER,customerTO.getPhoneNumber());

        return contentValues;
    }
}
