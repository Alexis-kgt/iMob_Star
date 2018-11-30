package com.example.alexis.starkr.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class StopTimeDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {
            DatabaseHelper.COLUMN_STOP_TIMES_TRIP_ID,
            DatabaseHelper.COLUMN_STOP_TIMES_ARRIVAL_TIME,
            DatabaseHelper.COLUMN_STOP_TIMES_DEPARTURE_TIME,
            DatabaseHelper.COLUMN_STOP_TIMES_STOP_ID,
            DatabaseHelper.COLUMN_STOP_TIMES_STOP_SEQUENCE,
            DatabaseHelper.COLUMN_STOP_TIMES_STOP_HEADSIGN,
            DatabaseHelper.COLUMN_STOP_TIMES_PICKUP_TYPE,
            DatabaseHelper.COLUMN_STOP_TIMES_DROP_OFF_TYPE,
            DatabaseHelper.COLUMN_STOP_TIMES_SHAPE_DIST_TRAVELED
    };

    public StopTimeDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
}
