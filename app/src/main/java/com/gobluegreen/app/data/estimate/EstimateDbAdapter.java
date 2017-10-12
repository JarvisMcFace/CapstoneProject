package com.gobluegreen.app.data.estimate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.gobluegreen.app.data.DatabaseConstants;
import com.gobluegreen.app.data.DatabaseHelper;

import static com.gobluegreen.app.data.estimate.EstimateContract.ESTIMATE_DATE;

/**
 * Created by David on 7/17/17.
 */

public class EstimateDbAdapter {

    private static final String TAG = EstimateDbAdapter.class.getSimpleName();

    public static final String ESTIMATE_TABLE = "Estimates";
    public static final String CUSTOMER_TABLE = "Customer";

    public static final String CREATE_ESTIMATE_TABLE =
            "CREATE TABLE IF NOT EXISTS "
                    + ESTIMATE_TABLE + " ("
                    + EstimateContract.ESTIMATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + EstimateContract.ESTIMATE_NUMBER_ROOMS + " TEXT NOT NULL, "
                    + EstimateContract.ESTIMATE_PRICE_ESTIMATE + " TEXT NOT NULL, "
                    + EstimateContract.ESTIMATE_SQFT + " TEXT NOT NULL, "
                    + ESTIMATE_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

    public static final String CREATE_CUSTOMER_TABLE =
            "CREATE TABLE IF NOT EXISTS "
                    + CUSTOMER_TABLE + " ("
                    + EstimateContract.CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + EstimateContract.CUSTOMER_TYPE + " TEXT NOT NULL, "
                    + EstimateContract.CUSTOMER_FIRST_NAME + " TEXT NOT NULL, "
                    + EstimateContract.CUSTOMER_LAST_NAME + " TEXT NOT NULL, "
                    + EstimateContract.CUSTOMER_ADDRESS1 + " TEXT NOT NULL, "
                    + EstimateContract.CUSTOMER_CITY + " TEXT NOT NULL, "
                    + EstimateContract.CUSTOMER_STATE + " TEXT NOT NULL, "
                    + EstimateContract.CUSTOMER_ZIP + " TEXT NOT NULL, "
                    + EstimateContract.CUSTOMER_PHONE_NUMBER + " TEXT NOT NULL, "
                    + EstimateContract.CUSTOMER_ESITMATE_ID + " INTEGER, "
                    + "FOREIGN KEY(" + EstimateContract.CUSTOMER_ESITMATE_ID + ") REFERENCES " + EstimateDbAdapter.ESTIMATE_TABLE + "(" + EstimateContract.ESTIMATE_ID + "))";

    private final Context context;
    private SQLiteDatabase sqLiteDatabase;

    public EstimateDbAdapter(Context context) {
        this.context = context;
    }

    public EstimateDbAdapter open() throws SQLException {
        DatabaseHelper databaseHelper = new DatabaseHelper(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public long insertBuilder(String insertTable, ContentValues contentValues) {
        return sqLiteDatabase.insert(insertTable, null, contentValues);
    }

    public Cursor queryEstimate(String tableName) {

        String rawQuery = "SELECT *, (strftime('%s', " + ESTIMATE_DATE + ") * 1000) AS " + EstimateContract.ESTIMATE_DATE + " FROM " + CUSTOMER_TABLE + ", " + ESTIMATE_TABLE
                + " WHERE " + EstimateContract.CUSTOMER_ESITMATE_ID + " = " + ESTIMATE_TABLE + "." + EstimateContract.ESTIMATE_ID ;
        Cursor cursor = sqLiteDatabase.rawQuery(rawQuery,null);

        return cursor;
    }

    public int deleteEstimateBuilder(String selection, String[] selectionArgs) {
        int rowDeleted = sqLiteDatabase.delete(
                ESTIMATE_TABLE,
                selection,
                selectionArgs
        );
        return rowDeleted;
    }


    public int deleteAllEstimateBuilder() {
        int rowDeleted = sqLiteDatabase.delete(
                ESTIMATE_TABLE,
                null, //all rows
                null
        );
        return rowDeleted;
    }


}
