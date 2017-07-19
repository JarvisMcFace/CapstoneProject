package com.gobluegreen.app.data.rooms;

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
public class RoomsDbAdapter {

    public static final String ROOM_TABLE = "Room";

    public static final String CREATE_ROOM_TABLE =
            "CREATE TABLE IF NOT EXISTS "
                    + ROOM_TABLE + " ("
                    + RoomsContract.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + RoomsContract.LENGTH + "  TEXT NOT NULL,  "
                    + RoomsContract.WIDTH + "  TEXT NOT NULL,  "
                    + RoomsContract.MOVE_FURNITURE + "  TEXT NOT NULL,  "
                    + RoomsContract.CARPET_PROTECTOR + "  TEXT NOT NULL,  "
                    + RoomsContract.SQUARE_FEET + "  TEXT NOT NULL,  "
                    + RoomsContract.PRICE_ESTIMATE + "  TEXT NOT NULL,  "
                    + RoomsContract.PRICE_ESTIMATE + " TEXT NOT NULL )";


    private final Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public RoomsDbAdapter(Context context) {
        this.context = context;
    }

    public RoomsDbAdapter open() throws SQLException {
        databaseHelper = new DatabaseHelper(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public long insertRoom(ContentValues contentValues) {
        return sqLiteDatabase.insert(ROOM_TABLE, null, contentValues);
    }

    public Cursor queryAllRooms() {

        Cursor cursor = sqLiteDatabase.query(
                ROOM_TABLE,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    public int deleteRoom(String selection, String[] selectionArgs) {
        int rowDeleted = sqLiteDatabase.delete(
                ROOM_TABLE,
                selection,
                selectionArgs
        );
        return rowDeleted;
    }


    public int deleteAllRoom() {
        int rowDeleted = sqLiteDatabase.delete(
                ROOM_TABLE,
                null, //all rows
                null
        );
        return rowDeleted;
    }




}
