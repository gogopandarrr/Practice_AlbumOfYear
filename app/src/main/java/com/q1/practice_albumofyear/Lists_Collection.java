package com.q1.practice_albumofyear;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by alfo06-07 on 2018-04-30.
 */

public class Lists_Collection  {
    ArrayList<Lists_Album> listsAlbums;
    String nameList;

    public Lists_Collection(ArrayList<Lists_Album> listsAlbums, String nameList) {

        this.listsAlbums = listsAlbums;
        this.nameList = nameList;
    }


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
}
