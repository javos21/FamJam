package com.mathura.javed_000.famjam.managers;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Nicholas on 5/11/2015.
 */
public class GcmIntentService extends IntentService {

    public CircleDB db;
    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (extras != null && !extras.isEmpty()) {  // has effect of unparcelling Bundle
            // Since we're not using two way messaging, this is all we really to check for
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Logger.getLogger("GCM_RECEIVED").log(Level.INFO, extras.toString());

                updateDB(extras.getString("message"));
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    protected void updateDB(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.e("GCMIntentService",message);

                db = new CircleDB(getApplicationContext());
                StringTokenizer st = new StringTokenizer(message,";");
                String name = st.nextToken();
                String email = st.nextToken();
                String lat = st.nextToken();
                String lng = st.nextToken();
                try{
                    if(db.ifPersonExists(name)){
                        db.updateEntry(name,email,Double.parseDouble(lat),Double.parseDouble(lng));
                        Log.i("DB Updated", name + " " + email + " " + lat + " " + lng);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                Log.i("EndpointsAsyncTask", name + " " + email + " " + lat + " " + lng);
                //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            }

            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

        });
    }
}