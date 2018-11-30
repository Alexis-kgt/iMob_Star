package com.example.alexis.starkr.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TripDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {
            DatabaseHelper.COLUMN_TRIP_ROUTE_ID,
            DatabaseHelper.COLUMN_TRIP_SERVICE_ID,
            DatabaseHelper.COLUMN_TRIP_HEADSIGN,
            DatabaseHelper.COLUMN_TRIP_SHORT_NAME,
            DatabaseHelper.COLUMN_TRIP_DIRECTION_ID,
            DatabaseHelper.COLUMN_TRIP_BLOCK_ID,
            DatabaseHelper.COLUMN_TRIP_WHEELCHAIR_ACCESSIBLE,
            DatabaseHelper.COLUMN_TRIP_BIKES_ALLOWED
    };

    public TripDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
}
