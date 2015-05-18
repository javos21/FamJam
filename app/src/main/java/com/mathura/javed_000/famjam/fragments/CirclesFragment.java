package com.mathura.javed_000.famjam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mathura.javed_000.famjam.managers.CircleDB;
import com.mathura.javed_000.famjam.CirclePerson;
import com.mathura.javed_000.famjam.RVAdapter;
import com.gc.materialdesign.views.ButtonFloat;

import com.mathura.javed_000.famjam.R;
import java.util.ArrayList;

public class CirclesFragment extends Fragment implements Parcelable{

    public CharSequence[] circlesList;
    private ArrayList<CirclePerson> circlePersons;

    private RecyclerView mRecyclerView;
    private RVAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<CirclePerson> persons;

    public CircleDB db;

    ButtonFloat FAB;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.circles_tab,container,false);

        // Create Cards list
        mRecyclerView = (RecyclerView) v.findViewById(R.id.circleList);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        circlePersons = getArguments().getParcelableArrayList("Circles");

        // Gets the database in write mode
        db = new CircleDB(getActivity());

        // Load user's circles list
        persons = new ArrayList<>();
        persons = db.retrieveAllCardEntries();
        mAdapter  = new RVAdapter(getActivity(),persons);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Floating Add Button
        createCirclesList();

        FAB = (ButtonFloat)v.findViewById(R.id.buttonFloat);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = onCreateCirclesDialog();
                dialog.show();
            }
        });
        return v;
    }

    public Dialog onCreateCirclesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select a Member From Your Google+");
        builder.setItems(circlesList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = circlePersons.get(which).name;
                String photourl = circlePersons.get(which).photoId;
                addToDB(name, photourl);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        return builder.create();
    }

    public void addToDB(String name, String photoUrl){
        db.insertEntry(name, "", photoUrl);
        reloadActivity();
    }

    public void reloadActivity(){
        Intent intent = getActivity().getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        getActivity().overridePendingTransition(0, 0);
        getActivity().finish();

        getActivity().overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void createCirclesList(){
        int count = circlePersons.size();
        circlesList = new CharSequence[count];
        for(int i = 0; i < count; i++){
            circlesList[i] = circlePersons.get(i).name;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
