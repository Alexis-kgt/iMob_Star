package com.example.alexis.starkr.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // TABLE CALENDAR
    public static final String TABLE_CALENDAR = "CALENDAR";
    public static final String COLUMN_CALENDAR_SERVICE_ID = "CALENDAR";
    public static final String COLUMN_CALENDAR_MONDAY = "CALENDAR";
    public static final String COLUMN_CALENDAR_TUESDAY = "CALENDAR";
    public static final String COLUMN_CALENDAR_WEDNESDAY = "CALENDAR";
    public static final String COLUMN_CALENDAR_THURSDAY = "CALENDAR";
    public static final String COLUMN_CALENDAR_FRIDAY = "CALENDAR";
    public static final String COLUMN_CALENDAR_SARTURDAY = "CALENDAR";
    public static final String COLUMN_CALENDAR_SUNDAY = "CALENDAR";
    public static final String COLUMN_CALENDAR_START_DATE = "CALENDAR";
    public static final String COLUMN_CALENDAR_END_DATE = "CALENDAR";

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

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE = "create table " + TABLE_COMMENTS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_COMMENT
            + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }
}
