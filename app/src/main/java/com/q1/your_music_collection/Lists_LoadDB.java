package com.q1.your_music_collection;

import java.util.ArrayList;

/**
 * Created by alfo06-07 on 2018-05-09.
 */

public class Lists_LoadDB {

    String title;
    ArrayList<Lists_Album> listsAlbums;
    String date;

    public Lists_LoadDB(String title, ArrayList<Lists_Album> listsAlbums, String date) {
        this.title = title;
        this.listsAlbums = listsAlbums;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Lists_Album> getListsAlbums() {
        return listsAlbums;
    }

    public void setListsAlbums(ArrayList<Lists_Album> listsAlbums) {
        this.listsAlbums = listsAlbums;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
