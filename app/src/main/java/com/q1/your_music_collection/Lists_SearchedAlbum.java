package com.q1.your_music_collection;

/**
 * Created by alfo06-07 on 2018-04-11.
 */

public class Lists_SearchedAlbum {

    String title, artist, coverL, coverXL, url;
    int rank;

    public Lists_SearchedAlbum(String name, String artist, String coverL, String coverXL, String url) {
        this.title = name;
        this.artist = artist;
        this.coverL = coverL;
        this.coverXL = coverXL;
        this.url = url;
    }


    public String getCoverXL() {
        return coverXL;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getCoverL() {
        return coverL;
    }

    public String getUrl() {
        return url;
    }


}
