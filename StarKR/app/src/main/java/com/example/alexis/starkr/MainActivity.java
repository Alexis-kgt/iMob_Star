package com.example.alexis.starkr;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexis.starkr.database.*;
import com.example.alexis.starkr.model.*;
import com.opencsv.CSVReader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MainActivity extends AppCompatActivity implements DateFragment.OnDirectionSelectedListener, StopFragment.OnStopListFragmentInteractionListener, StopTimeFragment.OnStopTimeListFragmentInteractionListener, TripFragment.OnTripListFragmentInteractionListener {
    private static final String TAG = "zzzzzz";
    ProgressDialog mProgressDialog;
    String CHANNEL_ID = "com.example.alexis.starkr.model";

    DateFragment dateFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();

        setContentView(R.layout.activity_main);

        // instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage("Téléchargement de la base de données");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        int annee = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        int mois = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1;
        int jour = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
        int heure = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY);
        int minutes = java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE);

        String moisS, jourS, heureS, minutesS;
        if(mois < 10)
            moisS = "0"+mois;
        else
            moisS = mois+"";
        if(jour < 10)
            jourS = "0"+jour;
        else
            jourS = jour+"";
        if(heure < 10)
            heureS = "0"+heure;
        else
            heureS = heure+"";
        if(minutes < 10)
            minutesS = "0"+minutes;
        else
            minutesS = minutes+"";
        ((TextView)findViewById(R.id.timeView)).setText(heureS + ":" + minutesS);
        ((TextView)findViewById(R.id.dateView)).setText(jourS + "/" + moisS + "/" + annee);
        dateFragment = new DateFragment();
        fragmentTransaction.add(R.id.dateFragment, dateFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();


        HashMap<String, String> csvFileOld = this.readCsvFile();


//
//        //replace lines to trigger the notification
//        String dateString = "2018-12-01";
//        //String dateString = csvFileOld.get("Fin de validité");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date convertedDate = new Date();
//        try {
//            convertedDate = dateFormat.parse(dateString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        if (csvFileOld.keySet().size() > 0) {
//            if (convertedDate.before(new Date())) {
//                sendNotification();
//            }
//        }


        // execute this when the downloader must be fired
        final DownloadTask downloadTaskCsv = new DownloadTask(MainActivity.this, "csv");
        downloadTaskCsv.execute("https://data.explore.star.fr/explore/dataset/tco-busmetro-horaires-gtfs-versions-td/download/?format=csv&timezone=Europe/Berlin&use_labels_for_header=true");

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTaskCsv.cancel(true);
            }
        });

//        Spinner lignesSpinner = findViewById(R.id.lignesSpinner);
//        lignesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                populateDirectionsSpinner();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//
//            }
//
//        });

//        Spinner directionsSpinner = findViewById(R.id.directionsSpinner);
//        directionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                //TODO récupérer les horaires du bus;
//                Log.d(TAG, "onItemSelected: pos " + position);
//                Log.d(TAG, "onItemSelected: id " + id);
//
//                LoadTaskStopTimes ltst = new LoadTaskStopTimes(MainActivity.this);
//                mProgressDialog.setMessage("Insertion des données en base");
//                ltst.execute();
//                Toast.makeText(MainActivity.this,"Base de données remplie", Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//
//            }
//
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        String[] permissions = new String[]{
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permissions, 1);

    }

    public void readCsv() {
        HashMap<String, String> csvFileNew = this.readCsvFile();
        Log.d("newCsvFile", csvFileNew.get("ID"));

        final DownloadTask downloadTaskZip = new DownloadTask(MainActivity.this, "zip");
        downloadTaskZip.execute(csvFileNew.get("URL"));

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTaskZip.cancel(true);
            }
        });
    }

    public void refreshDateFragmentDate(String date){
        ((TextView)findViewById(R.id.dateView)).setText(date);
    }
    public void refreshDateFragmentTime(String time){
        ((TextView)findViewById(R.id.timeView)).setText(time);
    }

    public boolean permissionOk() {
        return checkSelfPermission(Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public HashMap<String, String> readCsvFile() {
        CSVReader reader;
        HashMap<String, String> keyToValue = new HashMap<String, String>();
        try {
            reader = new CSVReader(new FileReader(Environment.getExternalStorageDirectory() + "/json.csv"));
            String[] line = reader.readNext();
            String[] keys = line[0].split(";");
            line = reader.readNext();
            if (line != null) {
                String[] values = line[0].split(";");
                for (int i = 0; i < 8; i++) {
                    keyToValue.put(keys[i], values[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keyToValue;
    }

    public ArrayList<Object> createObjectsForDb(String object) {
        String fileName = object + ".txt";
        ArrayList<Object> objects = new ArrayList<>();
        try {
            FileInputStream objFile = new FileInputStream(Environment.getExternalStorageDirectory() + "/" + fileName);
            InputStreamReader objReader = new InputStreamReader(objFile);
            BufferedReader objBufferReader = new BufferedReader(objReader);
            String strLine;
            int cpt = 0;
            while ((strLine = objBufferReader.readLine()) != null) {
                if (strLine.contains("'")) {
                    strLine = strLine.replace("'", " ");
                }
                if (cpt != 0) {
                    switch (object) {
                        case "calendar":
                            objects.add(Calendar.createObject(strLine));
                            break;
                        case "routes":
                            objects.add(Route.createObject(strLine));
                            break;
                        case "stops":
                            objects.add(Stop.createObject(strLine));
                            break;
                        case "stop_times":
                            objects.add(StopTime.createObject(strLine));
                            break;
                        case "trips":
                            objects.add(Trip.createObject(strLine));
                            break;
                    }
                }
                cpt++;
            }
            if (object == "stop_times") {
                Log.d("insertcommandcpt", cpt + "");
            }
            objFile.close();
        } catch (FileNotFoundException objError) {
            Toast.makeText(this, "Fichier non trouvé\n" + objError.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException objError) {
            Toast.makeText(this, "Erreur\n" + objError.toString(), Toast.LENGTH_LONG).show();
        }
        Log.d("fileContent", objects.size() + "");
        return objects;
    }


    private class LoadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public LoadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
            dbHelper.recreateDatabase(dbHelper.getWritableDatabase());
            ArrayList<Object> calendars = createObjectsForDb("calendar");
            CalendarDataSource cds = new CalendarDataSource(MainActivity.this);
            cds.fillTable(calendars);
            publishProgress(33);
            ArrayList<Object> routes = createObjectsForDb("routes");
            RouteDataSource rds = new RouteDataSource(MainActivity.this);
            rds.fillTable(routes);
            publishProgress(66);
            ArrayList<Object> stops = createObjectsForDb("stops");
            StopDataSource sds = new StopDataSource(MainActivity.this);
            sds.fillTable(stops);
            publishProgress(100);

            //TODO charger ca avec un filtre quand la direction est sélectionnée
            Log.d(TAG, "doInBackground: "+params.toString());
            /*ArrayList<Object> stopTimes = createObjectsForDb("stop_times");
            StopTimeDataSource stds = new StopTimeDataSource(this);
            stds.fillTable(stopTimes);
            ArrayList<Object> trips = createObjectsForDb("trips");
            TripDataSource tds = new TripDataSource(this);
            tds.fillTable(trips);*/

            /*SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM ROUTES WHERE ROUTE_ID  LIKE '%242%'",null);
            if(c.moveToFirst()){
                Log.d("resultSQL",c.getString(3));
            }
            */
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null) {
                Toast.makeText(context, "Problème d'insertion des données", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Base de données remplie", Toast.LENGTH_LONG).show();
                dateFragment.setSpinnerContent();
            }
        }
    }

//    private class LoadTaskStopTimes extends AsyncTask<String, Integer, String> {
//
//        private Context context;
//        private PowerManager.WakeLock mWakeLock;
//
//        public LoadTaskStopTimes(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            ArrayList<Object> stopTimes = createObjectsForDb("stop_times");
//            StopTimeDataSource stds = new StopTimeDataSource(MainActivity.this);
//            stds.fillTable(stopTimes);
//            publishProgress(50);
//            ArrayList<Object> trips = createObjectsForDb("trips");
//            TripDataSource tds = new TripDataSource(MainActivity.this);
//            tds.fillTable(trips);
//            publishProgress(100);
//
//            /*SQLiteDatabase db = dbHelper.getReadableDatabase();
//            Cursor c = db.rawQuery("SELECT * FROM ROUTES WHERE ROUTE_ID  LIKE '%242%'",null);
//            if(c.moveToFirst()){
//                Log.d("resultSQL",c.getString(3));
//            }
//            */
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            // take CPU lock to prevent CPU from going off if the user
//            // presses the power button during download
//            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
//            mWakeLock.acquire();
//            mProgressDialog.show();
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... progress) {
//            super.onProgressUpdate(progress);
//            // if we get here, length is known, now set indeterminate to false
//            mProgressDialog.setIndeterminate(false);
//            mProgressDialog.setMax(100);
//            mProgressDialog.setProgress(progress[0]);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            mWakeLock.release();
//            mProgressDialog.dismiss();
//            if (result != null){
//                Toast.makeText(context,"Problème d'insertion des données", Toast.LENGTH_LONG).show();
//            }else{
//                Toast.makeText(MainActivity.this,"Base de données remplie", Toast.LENGTH_LONG).show();
//                Log.d(TAG, "onPostExecute: "+ result.toString());
//                DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
//                SQLiteDatabase db = dbHelper.getReadableDatabase();
//                Cursor c = db.rawQuery("SELECT * FROM ROUTES",null);
//                ArrayList<String> lignes = new ArrayList();
//                if(c.moveToFirst()){
//                    do {
//                        lignes.add(c.getString(2) + " : "+ c.getString(3)+"_____"+c.getString(7)+"_____"+c.getString(8));
//                    } while(c.moveToNext());
//                }
//                final String[] lignesArray = new String[lignes.size()];
//                int cpt = 0;
//                for(String l : lignes){
//                    lignesArray[cpt] = l;
//                    cpt++;
//                }
//
//                ScrollView scrollView = findViewById(R.id.scrollView);
//
//                scrollView.setAdapter(new ArrayAdapter<String>(MainActivity.this,R.layout.fragment_stoptime_list,lignesArray){
//
//                    public View getView(int position, View convertView, ViewGroup parent) {
//                        // Cast the spinner collapsed item (non-popup item) as a text view
//                        TextView tv = (TextView) super.getView(position, convertView, parent);
//
//                        // Set the text color of spinner item
//                        tv.setText(lignesArray[position].split("_____")[0]);
//                        tv.setBackgroundColor(Color.parseColor("#"+lignesArray[position].split("_____")[1]));
//                        tv.setTextColor(Color.parseColor("#"+lignesArray[position].split("_____")[2]));
//
//                        // Return the view
//                        return tv;
//                    }
//
//                    @Override
//                    public View getDropDownView(int position, View convertView, ViewGroup parent){
//                        View v = convertView;
//                        if (v == null) {
//                            Context mContext = this.getContext();
//                            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                            v = vi.inflate(R.layout.ligne_item_spinner, null);
//                        }
//
//                        TextView tv = (TextView) v.findViewById(R.id.ligneItem);
//                        tv.setText(lignesArray[position].split("_____")[0]);
//                        tv.setBackgroundColor(Color.parseColor("#"+lignesArray[position].split("_____")[1]));
//                        tv.setTextColor(Color.parseColor("#"+lignesArray[position].split("_____")[2]));
//                        return v;
//                    }
//                });
//
//            }
//        }
//    }

    private class UnzipTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public UnzipTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            InputStream is;
            ZipInputStream zis;
            try {
                String filename;
                is = new FileInputStream(Environment.getExternalStorageDirectory() + "/bdd.zip");
                zis = new ZipInputStream(new BufferedInputStream(is));
                ZipEntry ze;
                byte[] buffer = new byte[1024];
                int count, cpt = 1;

                while ((ze = zis.getNextEntry()) != null) {
                    filename = ze.getName();

                    // Need to create directories if not exists, or
                    // it will generate an Exception...
                    if (ze.isDirectory()) {
                        File fmd = new File(Environment.getExternalStorageDirectory() + "/" + filename);
                        fmd.mkdirs();
                        continue;
                    }

                    FileOutputStream fout = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + filename);

                    while ((count = zis.read(buffer)) != -1) {
                        fout.write(buffer, 0, count);
                    }

                    fout.close();
                    zis.closeEntry();
                    publishProgress(cpt * (100 / 11));
                    cpt++;
                }
                zis.close();
            } catch (IOException e) {
                Log.d("zippp", "" + e.toString());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null) {
                Toast.makeText(context, "Problème d'insertion des données", Toast.LENGTH_LONG).show();
            } else {
                LoadTask lt = new LoadTask(MainActivity.this);
                mProgressDialog.setMessage("Insertion des données en base");
                lt.execute();
                Toast.makeText(MainActivity.this, "Base de données remplie", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;
        private String fileType;

        public DownloadTask(Context context, String fileType) {
            this.fileType = fileType;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {

            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                if (permissionOk()) {
                    URL url = new URL(sUrl[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    // expect HTTP 200 OK, so we don't mistakenly save error report
                    // instead of the file
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        return "Server returned HTTP " + connection.getResponseCode()
                                + " " + connection.getResponseMessage();
                    }

                    // this will be useful to display download percentage
                    // might be -1: server did not report the length
                    int fileLength = connection.getContentLength();

                    // download the file
                    input = connection.getInputStream();
                    if (fileType == "csv")
                        output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/json.csv");
                    else if (fileType == "zip")
                        output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/bdd.zip");

                    byte data[] = new byte[4096];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        // allow canceling with back button
                        if (isCancelled()) {
                            input.close();
                            return null;
                        }
                        total += count;
                        // publishing the progress....
                        if (fileType == "zip") {
                            if (fileLength > 0) // only if total length is known
                                publishProgress((int) (total * 100 / fileLength));
                        }
                        output.write(data, 0, count);
                    }
                }

            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
            mWakeLock.acquire();
            if (fileType == "zip")
                mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            if (fileType == "zip") {
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setMax(100);
                mProgressDialog.setProgress(progress[0]);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            if (fileType == "zip")
                mProgressDialog.dismiss();
            if (result != null) {
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            } else {
                if (fileType == "csv") {
                    readCsv();
                } else if (fileType == "zip") {
                    UnzipTask ut = new UnzipTask(MainActivity.this);
                    mProgressDialog.setMessage("Décompression des données");
                    ut.execute();
                    Toast.makeText(MainActivity.this, "Fichier décompressé", Toast.LENGTH_LONG).show();
                }
            }
        }


    }

    public void sendNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("New CSV available")
                .setContentText("click to download")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        int notificationId = 0;
        notificationManager.notify(notificationId++, mBuilder.build());

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel name";
            String description = "channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof DateFragment) {
            DateFragment dateFragment = (DateFragment) fragment;
            dateFragment.setOnDirectionSelectedListener(this);
        }
        if (fragment instanceof StopFragment) {
            StopFragment stopFragment = (StopFragment) fragment;
            stopFragment.setOnStopSelectedListener(this);
        }

    }

    public void onDirectionSelected(int position) {
        StopFragment stopFragment = StopFragment.newInstance(1);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.stopFragment, stopFragment);
        fragmentTransaction.commit();
    }

    public void onStopListFragmentInteraction(Stop stop) {
        StopTimeFragment stopTimeFragment = new StopTimeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.stopTimeFragment, stopTimeFragment);
        fragmentTransaction.commit();
    }

    public void onStopTimeListFragmentInteraction(StopTime stopTime) {
        StopTimeFragment stopTimeFragment = new StopTimeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.stopTimeFragment, stopTimeFragment);
        fragmentTransaction.commit();
    }

    public void onTripListFragmentInteraction(Trip trip) {
    }

}
