package com.example.alexis.starkr.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.alexis.starkr.model.Calendar;
import com.example.alexis.starkr.model.Route;

import java.util.ArrayList;

public class RouteDataSource {

    private Context context;
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

    public void fillTable(ArrayList<Object> routes) {
        this.open();
        String insertCommand = "insert into " + DatabaseHelper.TABLE_ROUTES + " (";
        int cpt = 1;
        for (String col : allColumns) {
            if (cpt < allColumns.length) {
                insertCommand += col + ", ";
            } else {
                insertCommand += col + ")";
            }
            cpt++;
        }
        insertCommand += " values ";
        cpt = 1;
        for (Object o : routes) {
            Route r = Route.class.cast(o);
            if (cpt < routes.size())
                insertCommand += "('" + r.getRoute_id() + "','" + r.getAgency_id() + "','" + r.getRoute_short_name() + "','" + r.getRoute_long_name() + "','" + r.getRoute_desc() + "','" + r.getRoute_type() + "','" + r.getRoute_url() + "','" + r.getRoute_color() + "','" + r.getRoute_text_color() + "','" + r.getRoute_sort_order() + "'),";
            else
                insertCommand += "('" + r.getRoute_id() + "','" + r.getAgency_id() + "','" + r.getRoute_short_name() + "','" + r.getRoute_long_name() + "','" + r.getRoute_desc() + "','" + r.getRoute_type() + "','" + r.getRoute_url() + "','" + r.getRoute_color() + "','" + r.getRoute_text_color() + "','" + r.getRoute_sort_order() + "');";
            cpt++;
        }
        Log.d("insertcommandrou", insertCommand);
        database.execSQL(insertCommand);
        this.close();
        return;
    }
}
