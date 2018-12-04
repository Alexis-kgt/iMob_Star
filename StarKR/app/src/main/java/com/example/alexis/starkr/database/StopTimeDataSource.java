package com.example.alexis.starkr.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.alexis.starkr.model.Calendar;
import com.example.alexis.starkr.model.StopTime;

import java.lang.reflect.Array;
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
            /*DatabaseHelper.COLUMN_STOP_TIMES_STOP_HEADSIGN,
            DatabaseHelper.COLUMN_STOP_TIMES_PICKUP_TYPE,
            DatabaseHelper.COLUMN_STOP_TIMES_DROP_OFF_TYPE,
            DatabaseHelper.COLUMN_STOP_TIMES_SHAPE_DIST_TRAVELED*/
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
        int cpt = 0;
        ArrayList<SQLiteStatement> requetes = new ArrayList<>();
        ArrayList<Object> tmpStopTimes = new ArrayList<>();
        for(Object o : stoptimes){
            cpt++;
            if(cpt < 1000){
                tmpStopTimes.add(o);
            }else{
                tmpStopTimes.add(o);
                requetes.add(this.createRequete(tmpStopTimes));
                tmpStopTimes = new ArrayList<>();
                cpt = 0;
            }
        }
        for(SQLiteStatement req : requetes){
            req.executeInsert();
        }
        this.close();
        return;
    }

    private SQLiteStatement createRequete(ArrayList<Object> stoptimes){
        String insertCommand = "insert into "+DatabaseHelper.TABLE_STOP_TIMES+" (";
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
        for(Object o : stoptimes){
            StopTime st = StopTime.class.cast(o);
            if(cpt < stoptimes.size())
                insertCommand += "('"+st.getTrip_id()+"','"+st.getArrival_time()+"','"+st.getDeparture_time()+"','"+st.getStop_id()+"','"+st.getStop_sequence()+/*"','"+st.getStop_headsign()+"','"+st.getPickup_type()+"','"+st.getDrop_off_type()+"','"+st.getShape_dist_traveled()+*/"'),";
            else
                insertCommand += "('"+st.getTrip_id()+"','"+st.getArrival_time()+"','"+st.getDeparture_time()+"','"+st.getStop_id()+"','"+st.getStop_sequence()+/*"','"+st.getStop_headsign()+"','"+st.getPickup_type()+"','"+st.getDrop_off_type()+"','"+st.getShape_dist_traveled()+*/"');";
            cpt++;
        }
        Log.d("insertcommandstoptimes",insertCommand);
        return database.compileStatement(insertCommand);
    }

}
