package com.q1.your_music_collection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by alfo06-07 on 2018-04-04.
 */

public class MyAdapter_Search extends RecyclerView.Adapter {

    Context context;
    ArrayList<Lists_SearchedAlbum> searchedAlbums;




    public MyAdapter_Search(Context context, ArrayList<Lists_SearchedAlbum> searchedAlbums) {
        this.context = context;
        this.searchedAlbums = searchedAlbums;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView;
        itemView = inflater.inflate(R.layout.list_search_item,parent,false);

        VH holder = new VH(itemView);



        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Lists_SearchedAlbum searchedAlbum = searchedAlbums.get(position);

        VH vh = (VH) holder;


        Glide.with(context).load(searchedAlbum.coverL).into(vh.iv_searchedCover);
        vh.tv_searchedInfo.setText(searchedAlbum.url);
        vh.tv_searchedArtist.setText(searchedAlbum.artist);
        vh.tv_searchedAlbum.setText(searchedAlbum.title);



    }

    @Override
    public int getItemCount() {
        return searchedAlbums.size();
    }






    public class VH extends RecyclerView.ViewHolder {


        ImageView iv_searchedCover;
        TextView tv_searchedAlbum, tv_searchedArtist, tv_searchedInfo;

        public VH(View itemView) {

           super(itemView);


            tv_searchedInfo = itemView.findViewById(R.id.tv_search_info);
            iv_searchedCover = itemView.findViewById(R.id.iv_search_cover);
            tv_searchedAlbum = itemView.findViewById(R.id.tv_search_album);
            tv_searchedArtist = itemView.findViewById(R.id.tv_search_artist);

        }


    }



}
