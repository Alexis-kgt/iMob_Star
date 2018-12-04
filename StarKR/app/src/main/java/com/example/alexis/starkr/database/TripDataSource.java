package com.example.alexis.starkr.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.alexis.starkr.model.StopTime;
import com.example.alexis.starkr.model.Trip;

import java.util.ArrayList;

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


    public void fillTable(ArrayList<Object> trips){
        this.open();
        int cpt = 0;
        ArrayList<String> requetes = new ArrayList<>();
        ArrayList<Object> tmpTrips = new ArrayList<>();
        for(Object o : trips){
            cpt++;
            if(cpt < 1000){
                tmpTrips.add(o);
            }else{
                tmpTrips.add(o);
                requetes.add(this.createRequete(tmpTrips));
                tmpTrips = new ArrayList<>();
                cpt = 0;
            }
        }
        for(String req : requetes){
            database.execSQL(req);
        }
        this.close();
        return;
    }

    private String createRequete(ArrayList<Object> trips){
        String insertCommand = "insert into "+DatabaseHelper.TABLE_TRIPS+" (";
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
        for(Object o : trips){
            Trip t = Trip.class.cast(o);
            if(cpt < trips.size())
                insertCommand += "('"+t.getRoute_id()+"','"+t.getService_id()+"','"+t.getTrip_id()+"','"+t.getTrip_headsign()+"','"+t.getTrip_short_name()+"','"+t.getDirection_id()+"','"+t.getBlock_id()+"','"+t.getWheelchair_accessible()+"','"+t.getBikes_allowed()+"'),";
            else
                insertCommand += "('"+t.getRoute_id()+"','"+t.getService_id()+"','"+t.getTrip_id()+"','"+t.getTrip_headsign()+"','"+t.getTrip_short_name()+"','"+t.getDirection_id()+"','"+t.getBlock_id()+"','"+t.getWheelchair_accessible()+"','"+t.getBikes_allowed()+"');";
            cpt++;
        }
        Log.d("insertcommandtrip",insertCommand);
        return insertCommand;
    }
}
