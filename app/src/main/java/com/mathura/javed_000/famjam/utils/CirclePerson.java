package com.mathura.javed_000.famjam;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javed_000 on 14/05/2015.
 */
public class CirclePerson implements Parcelable{
    public String name;
    public String photoId;

    public CirclePerson(String name, String photoId) {
        this.name = name;
        this.photoId = photoId;
    }

    public CirclePerson(Parcel in){
        this.name = in.readString();
        this.photoId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.photoId);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CirclePerson createFromParcel(Parcel in) {
            return new CirclePerson(in);
        }

        public CirclePerson[] newArray(int size) {
            return new CirclePerson[size];
        }
    };
}

