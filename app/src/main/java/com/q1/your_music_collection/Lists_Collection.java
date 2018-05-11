package com.q1.your_music_collection;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by alfo06-07 on 2018-04-30.
 */

public class Lists_Collection implements Parcelable {
    ArrayList<Lists_Album> listsAlbums;
    String nameList;

    public Lists_Collection(ArrayList<Lists_Album> listsAlbums, String nameList) {

        this.listsAlbums = listsAlbums;
        this.nameList = nameList;
    }


    protected Lists_Collection(Parcel in) {
        listsAlbums = in.createTypedArrayList(Lists_Album.CREATOR);
        nameList = in.readString();
    }

    public static final Creator<Lists_Collection> CREATOR = new Creator<Lists_Collection>() {
        @Override
        public Lists_Collection createFromParcel(Parcel in) {
            return new Lists_Collection(in);
        }

        @Override
        public Lists_Collection[] newArray(int size) {
            return new Lists_Collection[size];
        }
    };

    public ArrayList<Lists_Album> getListsAlbums() {
        return listsAlbums;
    }

    public void setListsAlbums(ArrayList<Lists_Album> listsAlbums) {
        this.listsAlbums = listsAlbums;
    }

    public String getNameList() {
        return nameList;
    }

    public void setNameList(String nameList) {
        this.nameList = nameList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeTypedList(listsAlbums);
        parcel.writeString(nameList);
    }
}
