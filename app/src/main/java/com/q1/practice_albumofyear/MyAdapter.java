package com.q1.practice_albumofyear;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by alfo06-07 on 2018-03-16.
 */

public class MyAdapter extends RecyclerView.Adapter {


    private static  int TYPE_ITEM = 1;
    private static  int TYPE_HEADER = 0;
    private static  int TYPE_FOOTER = 2;
    private boolean headerFlag = false;

    VH vh;
    Context context;
    ArrayList<Lists_Album> listsAlbums;

    public MyAdapter(Context context, ArrayList<Lists_Album> listsAlbums) {
        this.context = context;
        this.listsAlbums = listsAlbums;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);


        View itemView;


            headerFlag = false;
            itemView = inflater.inflate(R.layout.list_item, parent, false);


            VH holder = new VH(itemView);

            return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            vh = (VH) holder;


                Lists_Album listsAlbum = listsAlbums.get(position);

                Glide.with(context).load(listsAlbum.rankImg).into(vh.rating);
                Glide.with(context).load(listsAlbum.cover).into(vh.cover);



    }

    @Override
    public int getItemCount() {

        return listsAlbums.size();
    }




    class VH extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView cover, rating;


        public VH(View itemView) {
            super(itemView);


                cardView = itemView.findViewById(R.id.viewCard);
                cover = itemView.findViewById(R.id.iv_cover);
                rating = itemView.findViewById(R.id.iv_rating);



        }
    }


}//end