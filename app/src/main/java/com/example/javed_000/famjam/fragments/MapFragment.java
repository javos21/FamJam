package com.example.javed_000.famjam;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.javed_000.famjam.utils.GPSTracker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by hp1 on 21-01-2015.
 */
public class MapFragment extends Fragment {

    private GoogleMap googleMap;
    GPSTracker gps;
    double latitude;
    double longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.map_tab,container,false);
        createMapView();
        getLocation();
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

    private void getLocation(){
        gps = new GPSTracker(this.getActivity());
        if(gps.CanGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Log.e("MapFragment","lat = " + latitude + " and Long = " + longitude);
            addMarker(latitude,longitude);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 12));
        } else {
            gps.showSettingsAlert();
        }
    }

    private void addMarker(double lat, double lng){
        if(googleMap != null){
            googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .title("Marker")
                            .draggable(true)
            );
        }
    }


}