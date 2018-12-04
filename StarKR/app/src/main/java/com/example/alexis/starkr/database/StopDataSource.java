package com.example.alexis.starkr.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.alexis.starkr.model.Stop;

import java.util.ArrayList;

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

    public void fillTable(ArrayList<Object> stops){
        this.open();
        String insertCommand = "insert into "+DatabaseHelper.TABLE_STOPS+" (";
        int cpt = 1;
        for(String col : allColumns){
            if(cpt < allColumns.length){
                insertCommand += col+", ";
            }else{
                insertCommand += col+")";
            }
            cpt++;
        }
        insertCommand += " values ";
        cpt = 1;
        for(Object o : stops){
            Stop s = Stop.class.cast(o);
            if(cpt < stops.size())
                insertCommand += "('"+s.getStop_id()+"','"+s.getStop_code()+"','"+s.getStop_name()+"','"+s.getStop_desc()+"','"+s.getStop_lat()+"','"+s.getStop_lon()+"','"+s.getStop_id()+"','"+s.getStop_url()+"','"+s.getLocation_type()+"','"+s.getParent_station()+"','"+s.getStop_timezone()+"','"+s.getWheelchair_boarding()+"'),";
            else
                insertCommand += "('"+s.getStop_id()+"','"+s.getStop_code()+"','"+s.getStop_name()+"','"+s.getStop_desc()+"','"+s.getStop_lat()+"','"+s.getStop_lon()+"','"+s.getStop_id()+"','"+s.getStop_url()+"','"+s.getLocation_type()+"','"+s.getParent_station()+"','"+s.getStop_timezone()+"','"+s.getWheelchair_boarding()+"');";        }
        Log.d("insertcommand",insertCommand);
        database.execSQL(insertCommand);
        this.close();
        return;
    }
}
