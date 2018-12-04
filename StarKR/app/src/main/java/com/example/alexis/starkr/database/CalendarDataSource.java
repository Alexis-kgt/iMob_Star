package com.example.alexis.starkr.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.alexis.starkr.model.Calendar;

import java.util.ArrayList;

public class CalendarDataSource {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {
            DatabaseHelper.COLUMN_CALENDAR_SERVICE_ID,
            DatabaseHelper.COLUMN_CALENDAR_MONDAY,
            DatabaseHelper.COLUMN_CALENDAR_TUESDAY,
            DatabaseHelper.COLUMN_CALENDAR_WEDNESDAY,
            DatabaseHelper.COLUMN_CALENDAR_THURSDAY,
            DatabaseHelper.COLUMN_CALENDAR_FRIDAY,
            DatabaseHelper.COLUMN_CALENDAR_SARTURDAY,
            DatabaseHelper.COLUMN_CALENDAR_SUNDAY,
            DatabaseHelper.COLUMN_CALENDAR_START_DATE,
            DatabaseHelper.COLUMN_CALENDAR_END_DATE };

    public CalendarDataSource(Context c){
        dbHelper = new DatabaseHelper(c);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void fillTable(ArrayList<Object> calendars){
        this.open();
        String insertCommand = "insert into "+DatabaseHelper.TABLE_CALENDAR+" (";
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
        for(Object o : calendars){
            Calendar c = Calendar.class.cast(o);
            if(cpt < calendars.size())
                insertCommand += "('"+c.getService_id()+"','"+c.getMonday()+"','"+c.getTuesday()+"','"+c.getWednesday()+"','"+c.getThursday()+"','"+c.getFriday()+"','"+c.getSaturday()+"','"+c.getSunday()+"','"+c.getStart_date()+"','"+c.getEnd_date()+"'),";
            else
                insertCommand += "('"+c.getService_id()+"','"+c.getMonday()+"','"+c.getTuesday()+"','"+c.getWednesday()+"','"+c.getThursday()+"','"+c.getFriday()+"','"+c.getSaturday()+"','"+c.getSunday()+"','"+c.getStart_date()+"','"+c.getEnd_date()+"');";
            cpt++;
        }
        Log.d("insertcommandcal",insertCommand);
        database.execSQL(insertCommand);
        this.close();
        return;
    }
}
