package com.example.alexis.starkr.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class StopDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {
            DatabaseHelper.COLUMN_STOP_ID,
            DatabaseHelper.COLUMN_STOP_CODE,
            DatabaseHelper.COLUMN_STOP_NAME,
            DatabaseHelper.COLUMN_STOP_DESC,
            DatabaseHelper.COLUMN_STOP_LAT,
            DatabaseHelper.COLUMN_STOP_LON,
            DatabaseHelper.COLUMN_STOP_ZONE_ID,
            DatabaseHelper.COLUMN_STOP_URL,
            DatabaseHelper.COLUMN_STOP_LOCATION_TYPE,
            DatabaseHelper.COLUMN_STOP_PARENT_STATION,
            DatabaseHelper.COLUMN_STOP_TIMEZONE,
            DatabaseHelper.COLUMN_STOP_WHEELCHAIR_BOARDING,
    };

    public StopDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
}
