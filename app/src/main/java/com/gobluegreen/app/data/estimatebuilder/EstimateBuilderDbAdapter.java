package com.gobluegreen.app.data.estimatebuilder;

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

public class EstimateBuilderDbAdapter {

    public static final String ESTIMATE_BUILDER_TABLE = "EstimateBilder";

    public static final String CREATE_ESTIMATE_BUILDER_TABLE =
            "CREATE TABLE IF NOT EXIST "
                    + ESTIMATE_BUILDER_TABLE + " ("
                    + EstimateBuilderContract.ESTIMATE_BUILDER_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + EstimateBuilderContract.ESTIMATE_BUILDER_CUSTOMER_FIRST_NAME + " TEXT NOT NULL, "
                    + EstimateBuilderContract.ESTIMATE_BUILDER_CUSTOMER_LAST_NAME + " TEXT NOT NULL, "
                    + EstimateBuilderContract.ESTIMATE_BUILDER_CUSTOMER_ADDRESS1 + " TEXT NOT NULL, "
                    + EstimateBuilderContract.ESTIMATE_BUILDER_CUSTOMER_CITY + " TEXT NOT NULL, "
                    + EstimateBuilderContract.ESTIMATE_BUILDER_CUSTOMER_STATE + " TEXT NOT NULL, "
                    + EstimateBuilderContract.ESTIMATE_BUILDER_CUSTOMER_ZIP + " TEXT NOT NULL, "
                    + EstimateBuilderContract.ESTIMATE_BUILDER_CUSTOMER_PHONE_NUMBER + " TEXT NOT NULL)";


    private final Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public EstimateBuilderDbAdapter(Context context) {
        this.context = context;
    }

    public EstimateBuilderDbAdapter open() throws SQLException {
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
