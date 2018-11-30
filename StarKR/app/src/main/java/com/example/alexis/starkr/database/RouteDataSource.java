package com.example.alexis.starkr.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RouteDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {
            DatabaseHelper.COLUMN_ROUTE_ID,
            DatabaseHelper.COLUMN_ROUTE_AGENCY_ID,
            DatabaseHelper.COLUMN_ROUTE_SHORT_NAME,
            DatabaseHelper.COLUMN_ROUTE_LONG_NAME,
            DatabaseHelper.COLUMN_ROUTE_DESC,
            DatabaseHelper.COLUMN_ROUTE_TYPE,
            DatabaseHelper.COLUMN_ROUTE_URL,
            DatabaseHelper.COLUMN_ROUTE_COLOR,
            DatabaseHelper.COLUMN_ROUTE_TEXT_COLOR,
            DatabaseHelper.COLUMN_ROUTE_SORT_ORDER
    };

    public RouteDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
}
