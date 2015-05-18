package com.mathura.javed_000.famjam;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mathura.javed_000.famjam.GPSTracker;
import com.mathura.javed_000.famjam.LoadProfileImage;

import com.mathura.javed_000.famjam.R;
import com.mathura.javed_000.famjam.managers.CircleDB;
import com.mathura.javed_000.famjam.utils.PersonLocation;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by hp1 on 21-01-2015.
 */
public class MapFragment extends Fragment {

    private GoogleMap googleMap;
    private ArrayList<PersonLocation> locations;

    // Gets the database in write mode
    public CircleDB db;

    GPSTracker gps;
    double latitude;
    double longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.map_tab,container,false);
        db = new CircleDB(getActivity());
        createMapView();
        getCircleLocations();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(10.6667,-61.5167), 10));
        return v;
    }

    private void createMapView(){
        try {
            if(googleMap == null){
                googleMap= ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView)).getMap();
                if(googleMap == null) {
                    Toast.makeText(getActivity().getBaseContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }

    private void getCircleLocations(){
        ArrayList<PersonLocation> locations = new ArrayList<PersonLocation>();
        locations = db.retrieveAllLocations();
        for (PersonLocation location : locations){
            addMarker(location.name,"",location.latitude,location.longitude);
        }
    }

    private void addMarker(String name, String photoUrl, double lat, double lng){
        if(googleMap != null){
            //View marker =  getActivity().getLayoutInflater().inflate(R.layout.infowindow, null);
            //Bitmap output = createDrawableFromView(getActivity(),marker);
            googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .title(name)
                    //.icon(BitmapDescriptorFactory.fromBitmap(output))
            );

        }
    }

    public static Bitmap createDrawableFromView(Context context, View view){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }


}