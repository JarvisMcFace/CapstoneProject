package com.gobluegreen.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gobluegreen.app.data.estimate.EstimateDbAdapter;

/**
 * Created by David on 11/6/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createAllTables(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Intentionally blank
    }

    private void createAllTables(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON;");
        sqLiteDatabase.execSQL(EstimateDbAdapter.CREATE_ESTIMATE_TABLE);
        sqLiteDatabase.execSQL(EstimateDbAdapter.CREATE_SERVICE_TYPE_TABLE);
        sqLiteDatabase.execSQL(EstimateDbAdapter.CREATE_ROOM_TABLE);
        sqLiteDatabase.execSQL(EstimateDbAdapter.CREATE_CUSTOMER_TABLE);
    }
}
