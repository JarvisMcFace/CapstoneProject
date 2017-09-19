package com.gobluegreen.app.data.estimate;

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

public class EstimateDbAdapter {

    public static final String ESTIMATE_TABLE = "Estimates";
    public static final String SERVICE_TYPE_TABLE = "Servicetype";
    public static final String ROOM_TABLE = "Room";
    public static final String CUSTOMER_TABLE = "Customer";


    public static final String CREATE_ESTIMATE_TABLE =
            "CREATE TABLE IF NOT EXISTS "
                    + ESTIMATE_TABLE + " ("
                    + EstimateContract.ESTIMATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + EstimateContract.ESTIMATE_DAVE + "  TEXT NOT NULL, "
                    + EstimateContract.ESTIMATE_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

//      "CREATE TABLE IF NOT EXISTS "
//              + ESTIMATE_TABLE + " ("
//            + EstimateContract.ESTIMATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + EstimateContract.ESTIAMTE_SERVICES_TYPE_ID + " INTERGER, "
//            + EstimateContract.ESTIMATE_UPHOLSTERY_TYPE_ID + " INTERGER, "
//            + EstimateContract.ESTIAMTE_ROOMS_ID + "INTERGER, "
//            + EstimateContract.ESTIMATE_CUSTOMER_ID + " INTERGER, "
//            + EstimateContract.ESTIMATE_ROOM_TYPE_ID + " INTERGER, "
//            + EstimateContract.ESTIMATE_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";


    public static final String CREATE_SERVICE_TYPE_TABLE =
            "CREATE TABLE IF NOT EXISTS "
                    + SERVICE_TYPE_TABLE + " ("
                    + EstimateContract.SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + EstimateContract.SERVICE_TYPE + " TEXT NOT NULL, "
                    + EstimateContract.SERVICE_ESTIMATE_ID + " INTEGER, "
                    + "FOREIGN KEY(" + EstimateContract.SERVICE_ID + ") REFERENCES " + EstimateDbAdapter.ESTIMATE_TABLE + "(" + EstimateContract.ESTIMATE_ID + "))";


    public static final String CREATE_ROOM_TABLE =
            "CREATE TABLE IF NOT EXISTS "
                    + ROOM_TABLE + " ("
                    + EstimateContract.ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + EstimateContract.ROOM_LENGTH + "  TEXT NOT NULL,  "
                    + EstimateContract.ROOM_WIDTH + "  TEXT NOT NULL,  "
                    + EstimateContract.ROOM_CARPET_PROTECTOR + "  TEXT NOT NULL,  "
                    + EstimateContract.ROOM_SQUARE_FEET + "  TEXT NOT NULL,  "
                    + EstimateContract.ROOM_PRICE_ESTIMATE + "  TEXT NOT NULL,  "
                    + EstimateContract.ROOM_ESTIMATE_SQFT + " TEXT NOT NULL, "
                    + EstimateContract.ROOM_ESITMATE_ID + " INTEGER, "
                    + "FOREIGN KEY(" + EstimateContract.ROOM_ID + ") REFERENCES " + EstimateDbAdapter.ESTIMATE_TABLE + "(" + EstimateContract.ESTIMATE_ID + "))";


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
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public EstimateDbAdapter(Context context) {
        this.context = context;
    }

    public EstimateDbAdapter open() throws SQLException {
        databaseHelper = new DatabaseHelper(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public long insertBuilder(String insertTable, ContentValues contentValues) {
        return sqLiteDatabase.insert(insertTable, null, contentValues);
    }

    public Cursor queryEstimate(String tableName) {

        Cursor cursor = sqLiteDatabase.query(
                tableName,
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
