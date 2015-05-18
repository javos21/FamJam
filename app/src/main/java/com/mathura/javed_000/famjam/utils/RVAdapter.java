package com.mathura.javed_000.famjam;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import com.mathura.javed_000.famjam.CirclePerson;
import com.mathura.javed_000.famjam.LoadProfileImage;
import com.mathura.javed_000.famjam.managers.CircleDB;



public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    List<CirclePerson> persons;
    private Context context;
    public CircleDB db;

    public class PersonViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView personName;
        ImageView personPhoto;

        public PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }

    public RVAdapter(Context context, List<CirclePerson> persons){
        db = new CircleDB(context);
        this.context = context;
        this.persons = persons;
    }

    public void delete(int position){
        persons.remove(position);
        notifyItemRemoved(position);
    }

    public Dialog removeDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Remove " + persons.get(position).name + " ?");
        builder.setPositiveButton("Remove",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteEntry(persons.get(position).name);
                delete(position);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        return builder.create();
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, final int i) {
        personViewHolder.personName.setText(persons.get(i).name);
        new LoadProfileImage(personViewHolder.personPhoto).execute(persons.get(i).photoId);
        personViewHolder.personPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = removeDialog(i);
                dialog.show();
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}