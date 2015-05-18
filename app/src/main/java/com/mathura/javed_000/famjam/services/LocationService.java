package com.mathura.javed_000.famjam.services;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.util.Pair;
import android.util.Log;

import com.mathura.javed_000.famjam.GPSTracker;
import com.mathura.javed_000.famjam.MainActivity;
import com.mathura.javed_000.famjam.managers.CircleDB;
import com.mathura.javed_000.famjam.managers.EndpointsAsyncTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by javed_000 on 16/05/2015.
 */
public class LocationService extends Service {

    private static final String TAG = "LocationService";
    private boolean isRunning = false;
    private Handler handler;
    public CircleDB db;

    @Override
    public void onCreate(){
        Log.i(TAG, "Service onCreate");
        handler = new Handler();
        db = new CircleDB(getApplicationContext());
        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service onStartCommand");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (isRunning) {
                    getLocation();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 60000, 60000);
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        Log.i(TAG, "Service onDestroy");
    }

    private void getLocation(){
        handler.post(new Runnable(){
            public void run() {
                GPSTracker gps = new GPSTracker(getApplicationContext());
                if (gps.CanGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    new EndpointsAsyncTask().execute(new Pair<Context, String>
                            (getApplicationContext(), ";" + db.getName() + ";" +
                                    db.getEmail() + ";" + latitude + ";" + longitude ));
                } else {
                    gps.showSettingsAlert();
                }
            }
        });
    }
}
