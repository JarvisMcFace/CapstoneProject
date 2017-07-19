package com.gobluegreen.app.data.customer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.gobluegreen.app.data.DatabaseConstants;
import com.gobluegreen.app.data.DatabaseHelper;

/**
 * Created by David on 7/17/17.
 */

public class CustomerDbAdapter {

    public static final String ESTIMATE_BUILDER_TABLE = "EstimateBilder";

    public static final String CREATE_ESTIMATE_BUILDER_TABLE =
            "CREATE TABLE IF NOT EXISTS "
                    + ESTIMATE_BUILDER_TABLE + " ("
                    + CustomerContract.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CustomerContract.FIRST_NAME + " TEXT NOT NULL, "
                    + CustomerContract.LAST_NAME + " TEXT NOT NULL, "
                    + CustomerContract.ADDRESS1 + " TEXT NOT NULL, "
                    + CustomerContract.CITY + " TEXT NOT NULL, "
                    + CustomerContract.STATE + " TEXT NOT NULL, "
                    + CustomerContract.ZIP + " TEXT NOT NULL, "
                    + CustomerContract.PHONE_NUMBER + " TEXT NOT NULL)";


    private final Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public CustomerDbAdapter(Context context) {
        this.context = context;
    }

    public CustomerDbAdapter open() throws SQLException {
        databaseHelper = new DatabaseHelper(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public long insertEstimateBuilder(ContentValues contentValues) {
        return sqLiteDatabase.insert(ESTIMATE_BUILDER_TABLE, null, contentValues);
    }

    public Cursor queryAllEstimateBuilder() {

        Cursor cursor = sqLiteDatabase.query(
                ESTIMATE_BUILDER_TABLE,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    public int deleteEstimateBuilder(String selection, String[] selectionArgs) {
        int rowDeleted = sqLiteDatabase.delete(
                ESTIMATE_BUILDER_TABLE,
                selection,
                selectionArgs
        );
        return rowDeleted;
    }


    public int deleteAllEstimateBuilder() {
        int rowDeleted = sqLiteDatabase.delete(
                ESTIMATE_BUILDER_TABLE,
                null, //all rows
                null
        );
        return rowDeleted;
    }




}
