package com.example.alexis.starkr.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.alexis.starkr.model.Calendar;
import com.example.alexis.starkr.model.StopTime;

import java.util.ArrayList;

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

    public void fillTable(ArrayList<Object> stoptimes){
        this.open();
        String insertCommand = "insert into "+DatabaseHelper.TABLE_STOP_TIMES+" (";
        String debutReq;
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
        debutReq = insertCommand;
        cpt = 1;
        for(Object o : stoptimes){
            StopTime st = StopTime.class.cast(o);
            if(cpt < 1000)
                insertCommand += "('"+st.getTrip_id()+"','"+st.getArrival_time()+"','"+st.getDeparture_time()+"','"+st.getStop_id()+"','"+st.getStop_sequence()+"','"+st.getStop_headsign()+"','"+st.getPickup_type()+"','"+st.getDrop_off_type()+"','"+st.getShape_dist_traveled()+"'),";
            else
                insertCommand += "('"+st.getTrip_id()+"','"+st.getArrival_time()+"','"+st.getDeparture_time()+"','"+st.getStop_id()+"','"+st.getStop_sequence()+"','"+st.getStop_headsign()+"','"+st.getPickup_type()+"','"+st.getDrop_off_type()+"','"+st.getShape_dist_traveled()+"');";
            cpt++;
        }
        database.execSQL(insertCommand);
        this.close();
        return;
    }

}
