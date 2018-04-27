package com.q1.practice_albumofyear;

/**
 * Created by alfo06-07 on 2018-03-16.
 */

public class Lists_Album {

    int rankImg;
    String artist, album, cover, url, opinion;

    public Lists_Album(int rankImg, String cover, String artist, String album, String url, String opinion) {
        this.rankImg = rankImg;
        this.cover = cover;
        this.artist = artist;
        this.album = album;
        this.url = url;
        this.opinion = opinion;

    }


    public void setRankImg(int rankImg) {
        this.rankImg = rankImg;
    }




}
