package com.q1.practice_albumofyear;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alfo06-07 on 2018-03-16.
 */

public class Lists_Album implements Parcelable {

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


    protected Lists_Album(Parcel in) {
        rankImg = in.readInt();
        artist = in.readString();
        album = in.readString();
        cover = in.readString();
        url = in.readString();
        opinion = in.readString();
    }

    public static final Creator<Lists_Album> CREATOR = new Creator<Lists_Album>() {
        @Override
        public Lists_Album createFromParcel(Parcel in) {
            return new Lists_Album(in);
        }

        @Override
        public Lists_Album[] newArray(int size) {
            return new Lists_Album[size];
        }
    };

    public void setRankImg(int rankImg) {
        this.rankImg = rankImg;
    }


    public int getRankImg() { return rankImg;    }


    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(rankImg);
        parcel.writeString(artist);
        parcel.writeString(album);
        parcel.writeString(cover);
        parcel.writeString(url);
        parcel.writeString(opinion);
    }
}
