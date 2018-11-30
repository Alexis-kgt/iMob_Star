package com.example.alexis.starkr;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MainActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    boolean zipDownloaded;

    public void setZipDownloaded(boolean zipDownloaded) {
        this.zipDownloaded = zipDownloaded;
    }

    public boolean getZipDowloaded(){
        return zipDownloaded;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zipDownloaded = false;

// instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage("A message");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        HashMap<String,String> csvFileOld = this.readCsvFile();
        Log.d("oldCsvFile",csvFileOld.get("ID"));
// execute this when the downloader must be fired
        final DownloadTask downloadTaskCsv = new DownloadTask(MainActivity.this,"csv");
        downloadTaskCsv.execute("https://data.explore.star.fr/explore/dataset/tco-busmetro-horaires-gtfs-versions-td/download/?format=csv&timezone=Europe/Berlin&use_labels_for_header=true");

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTaskCsv.cancel(true);
            }
        });

        HashMap<String,String> csvFileNew = this.readCsvFile();
        Log.d("newCsvFile",csvFileNew.get("ID"));

        final DownloadTask downloadTaskZip = new DownloadTask(MainActivity.this, "zip");
        downloadTaskZip.execute(csvFileNew.get("URL"));

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTaskZip.cancel(true);
            }
        });
    }
    public void walkdir(File dir) {
        File listFile[] = dir.listFiles();

        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {// if its a directory need to get the files under that directory
                    walkdir(listFile[i]);
                } else {// add path of  files to your arraylist for later use

                    //Do what ever u want
                    Log.d("filePath",listFile[i].getAbsolutePath());
                }
            }
        }
    }
    @Override
    protected void onStart(){
        super.onStart();

        String[] permissions = new String[] {
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permissions,1);

    }

    public boolean permissionOk(){
        return checkSelfPermission(Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public HashMap<String,String> readCsvFile(){
        CSVReader reader = null;
        HashMap<String,String> keyToValue = new HashMap<String,String>();
        try {
            reader = new CSVReader(new FileReader(Environment.getExternalStorageDirectory()+"/json.csv"));
            String [] line = reader.readNext();
            String [] keys = line[0].split(";");
            line = reader.readNext();
            String [] values = line[0].split(";");
            for(int i = 0; i < 8; i++){
                keyToValue.put(keys[i],values[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keyToValue;
    }

    public boolean unzip(){
        InputStream is;
        ZipInputStream zis;
        try
        {
            String filename;
            is = new FileInputStream(Environment.getExternalStorageDirectory()+"/bdd.zip");
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            while ((ze = zis.getNextEntry()) != null)
            {
                filename = ze.getName();

                // Need to create directories if not exists, or
                // it will generate an Exception...
                if (ze.isDirectory()) {
                    File fmd = new File(Environment.getExternalStorageDirectory() + "/" + filename);
                    fmd.mkdirs();
                    continue;
                }

                FileOutputStream fout = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + filename);

                while ((count = zis.read(buffer)) != -1)
                {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
            }

            zis.close();
        }
        catch(IOException e)
        {
            Log.d("zippp",""+e.toString());
            return false;
        }

        return true;
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
                if(permissionOk()){
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
                    if(fileType == "csv")
                        output = new FileOutputStream(Environment.getExternalStorageDirectory()+"/json.csv");
                    else if(fileType == "zip")
                        output = new FileOutputStream(Environment.getExternalStorageDirectory()+"/bdd.zip");

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
                        if(fileType == "zip"){
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
            if(fileType == "zip")
                mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            if(fileType == "zip"){
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setMax(100);
                mProgressDialog.setProgress(progress[0]);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            if(fileType == "zip")
                mProgressDialog.dismiss();
            if (result != null){
                Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
            }

            else{
                if(fileType == "zip"){
                    Toast.makeText(context,"File downloaded", Toast.LENGTH_LONG).show();
                    boolean unziped;
                    unziped = unzip();
                    Log.d("zippp",unziped+"");


                    File dir= android.os.Environment.getExternalStorageDirectory();

                    walkdir(dir);


                }

            }
        }
    }
}
