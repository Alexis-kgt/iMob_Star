package com.example.alexis.starkr.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // TABLE CALENDAR
    public static final String TABLE_CALENDAR = "CALENDAR";
    public static final String COLUMN_CALENDAR_SERVICE_ID = "SERVICE_ID";
    public static final String COLUMN_CALENDAR_MONDAY = "MONDAY";
    public static final String COLUMN_CALENDAR_TUESDAY = "TUESDAY";
    public static final String COLUMN_CALENDAR_WEDNESDAY = "WEDNESDAY";
    public static final String COLUMN_CALENDAR_THURSDAY = "THURSDAY";
    public static final String COLUMN_CALENDAR_FRIDAY = "FRIDAY";
    public static final String COLUMN_CALENDAR_SARTURDAY = "SARTURDAY";
    public static final String COLUMN_CALENDAR_SUNDAY = "SUNDAY";
    public static final String COLUMN_CALENDAR_START_DATE = "START_DATE";
    public static final String COLUMN_CALENDAR_END_DATE = "END_DATE";

    // TABLE ROUTES
    public static final String TABLE_ROUTES = "ROUTES";
    public static final String COLUMN_ROUTE_ID = "ROUTE_ID";
    public static final String COLUMN_ROUTE_AGENCY_ID = "AGENCY_ID";
    public static final String COLUMN_ROUTE_SHORT_NAME = "ROUTE_SHORT_NAME";
    public static final String COLUMN_ROUTE_LONG_NAME = "ROUTE_LONG_NAME";
    public static final String COLUMN_ROUTE_DESC = "ROUTE_DESC";
    public static final String COLUMN_ROUTE_TYPE = "ROUTE_TYPE";
    public static final String COLUMN_ROUTE_URL = "ROUTE_URL";
    public static final String COLUMN_ROUTE_COLOR = "ROUTE_COLOR";
    public static final String COLUMN_ROUTE_TEXT_COLOR = "ROUTE_TEXT_COLOR";
    public static final String COLUMN_ROUTE_SORT_ORDER = "ROUTE_SORT_ORDER";

    // TABLE STOP_TIMES
    public static final String TABLE_STOP_TIMES = "STOP_TIMES";
    public static final String COLUMN_STOP_TIMES_TRIP_ID = "TRIP_ID";
    public static final String COLUMN_STOP_TIMES_ARRIVAL_TIME = "ARRIVAL_TIME";
    public static final String COLUMN_STOP_TIMES_DEPARTURE_TIME = "DEPARTURE_TIME";
    public static final String COLUMN_STOP_TIMES_STOP_ID = "STOP_ID";
    public static final String COLUMN_STOP_TIMES_STOP_SEQUENCE = "STOP_SEQUENCE";
    public static final String COLUMN_STOP_TIMES_STOP_HEADSIGN = "STOP_HEADSIGN";
    public static final String COLUMN_STOP_TIMES_PICKUP_TYPE = "PICKUP_TYPE";
    public static final String COLUMN_STOP_TIMES_DROP_OFF_TYPE = "DROP_OFF_TYPE";
    public static final String COLUMN_STOP_TIMES_SHAPE_DIST_TRAVELED = "SHAPE_DIST_TRAVELED";

    // TABLE STOPS
    public static final String TABLE_STOPS = "STOPS";
    public static final String COLUMN_STOP_ID = "STOP_ID";
    public static final String COLUMN_STOP_CODE = "STOP_CODE";
    public static final String COLUMN_STOP_NAME = "STOP_NAME";
    public static final String COLUMN_STOP_DESC = "STOP_DESC";
    public static final String COLUMN_STOP_LAT = "STOP_LAT";
    public static final String COLUMN_STOP_LON = "STOP_LON";
    public static final String COLUMN_STOP_ZONE_ID = "ZONE_ID";
    public static final String COLUMN_STOP_URL = "STOP_URL";
    public static final String COLUMN_STOP_LOCATION_TYPE = "LOCATION_TYPE";
    public static final String COLUMN_STOP_PARENT_STATION = "PARENT_STATION";
    public static final String COLUMN_STOP_TIMEZONE = "STOP_TIMEZONE";
    public static final String COLUMN_STOP_WHEELCHAIR_BOARDING = "WHEELCHAIR_BOARDING";

    // TABLE TRIPS
    public static final String TABLE_TRIPS = "TRIPS";
    public static final String COLUMN_TRIP_ROUTE_ID = "ROUTE_ID";
    public static final String COLUMN_TRIP_SERVICE_ID = "SERVICE_ID";
    public static final String COLUMN_TRIP_ID = "TRIP_ID";
    public static final String COLUMN_TRIP_HEADSIGN = "TRIP_HEADSIGN";
    public static final String COLUMN_TRIP_SHORT_NAME = "TRIP_SHORT_NAME";
    public static final String COLUMN_TRIP_DIRECTION_ID = "DIRECTION_ID";
    public static final String COLUMN_TRIP_BLOCK_ID = "BLOCK_ID";
    public static final String COLUMN_TRIP_SHAPE_ID = "SHAPE_ID";
    public static final String COLUMN_TRIP_WHEELCHAIR_ACCESSIBLE = "WHEELCHAIR_ACCESSIBLE";
    public static final String COLUMN_TRIP_BIKES_ALLOWED = "BIKES_ALLOWED";

    private static final String DATABASE_NAME = "starKR.db";
    private static final int DATABASE_VERSION = 1;

    // Commandes sql pour la création de la base de données
    private static final String DATABASE_CREATE_CALENDAR = "create table " + TABLE_CALENDAR + "("
            + COLUMN_CALENDAR_SERVICE_ID + " integer primary key autoincrement, "
            + COLUMN_CALENDAR_MONDAY + " text not null, "
            + COLUMN_CALENDAR_TUESDAY+ " text not null, "
            + COLUMN_CALENDAR_WEDNESDAY+ " text not null, "
            + COLUMN_CALENDAR_THURSDAY+ " text not null, "
            + COLUMN_CALENDAR_FRIDAY+ " text not null, "
            + COLUMN_CALENDAR_SARTURDAY+ " text not null, "
            + COLUMN_CALENDAR_SUNDAY+ " text not null, "
            + COLUMN_CALENDAR_START_DATE+ " text not null, "
            + COLUMN_CALENDAR_END_DATE+ " text not null);";

    private static final String DATABASE_CREATE_ROUTES = "create table " + TABLE_ROUTES + "("
            + COLUMN_ROUTE_ID + " integer primary key autoincrement, "
            + COLUMN_ROUTE_AGENCY_ID + " text not null, "
            + COLUMN_ROUTE_SHORT_NAME+ " text not null, "
            + COLUMN_ROUTE_LONG_NAME+ " text not null, "
            + COLUMN_ROUTE_DESC+ " text not null, "
            + COLUMN_ROUTE_TYPE+ " text not null, "
            + COLUMN_ROUTE_URL+ " text not null, "
            + COLUMN_ROUTE_COLOR+ " text not null, "
            + COLUMN_ROUTE_TEXT_COLOR+ " text not null, "
            + COLUMN_ROUTE_SORT_ORDER+ " text not null);";

    private static final String DATABASE_CREATE_STOP_TIMES = "create table " + TABLE_STOP_TIMES + "("
            + COLUMN_STOP_TIMES_TRIP_ID + " text not null, "
            + COLUMN_STOP_TIMES_ARRIVAL_TIME+ " text not null, "
            + COLUMN_STOP_TIMES_DEPARTURE_TIME+ " text not null, "
            + COLUMN_STOP_TIMES_STOP_ID+ " text not null, "
            + COLUMN_STOP_TIMES_STOP_SEQUENCE+ " text not null, "
            + COLUMN_STOP_TIMES_STOP_HEADSIGN+ " text not null, "
            + COLUMN_STOP_TIMES_PICKUP_TYPE+ " text not null, "
            + COLUMN_STOP_TIMES_DROP_OFF_TYPE+ " text not null, "
            + COLUMN_STOP_TIMES_SHAPE_DIST_TRAVELED+ " text not null, "
            + "foreign key ("+COLUMN_STOP_TIMES_TRIP_ID+") references "+TABLE_TRIPS+"("+COLUMN_TRIP_ID+"), "
            + "foreign key ("+COLUMN_STOP_TIMES_STOP_ID+") references "+TABLE_STOPS+"("+COLUMN_STOP_ID +"));";

    private static final String DATABASE_CREATE_STOPS = "create table " + TABLE_STOPS + "("
            + COLUMN_STOP_ID + " integer primary key autoincrement, "
            + COLUMN_STOP_CODE + " text not null, "
            + COLUMN_STOP_NAME+ " text not null, "
            + COLUMN_STOP_DESC+ " text not null, "
            + COLUMN_STOP_LAT+ " text not null, "
            + COLUMN_STOP_LON+ " text not null, "
            + COLUMN_STOP_ZONE_ID+ " text not null, "
            + COLUMN_STOP_URL+ " text not null, "
            + COLUMN_STOP_LOCATION_TYPE+ " text not null, "
            + COLUMN_STOP_PARENT_STATION+ " text not null, "
            + COLUMN_STOP_TIMEZONE+ " text not null, "
            + COLUMN_STOP_WHEELCHAIR_BOARDING+ " text not null);";

    private static final String DATABASE_CREATE_TRIPS = "create table " + TABLE_TRIPS + "("
            + COLUMN_TRIP_ROUTE_ID + " text not null, "
            + COLUMN_TRIP_SERVICE_ID+ " text not null, "
            + COLUMN_TRIP_ID + " integer primary key autoincrement, "
            + COLUMN_TRIP_HEADSIGN+ " text not null, "
            + COLUMN_TRIP_SHORT_NAME+ " text not null, "
            + COLUMN_TRIP_DIRECTION_ID+ " text not null, "
            + COLUMN_TRIP_BLOCK_ID+ " text not null, "
            + COLUMN_TRIP_SHAPE_ID+ " text not null, "
            + COLUMN_TRIP_WHEELCHAIR_ACCESSIBLE+ " text not null, "
            + COLUMN_TRIP_BIKES_ALLOWED+ " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_CALENDAR);
        database.execSQL(DATABASE_CREATE_ROUTES);
        database.execSQL(DATABASE_CREATE_STOP_TIMES);
        database.execSQL(DATABASE_CREATE_STOPS);
        database.execSQL(DATABASE_CREATE_TRIPS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALENDAR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOP_TIMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);
        onCreate(db);
    }

    public void recreateDatabase(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALENDAR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOP_TIMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);
        onCreate(db);
    }
}
