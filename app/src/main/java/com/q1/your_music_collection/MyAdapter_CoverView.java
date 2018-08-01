package com.q1.your_music_collection;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.luseen.autolinklibrary.AutoLinkMode;
import com.luseen.autolinklibrary.AutoLinkTextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

/**
 * Created by alfo06-07 on 2018-03-16.
 */

public class MyAdapter_CoverView extends RecyclerView.Adapter {



    VH vh;
    Context context;
    ArrayList<String> coverlist;


    public MyAdapter_CoverView(Context context, ArrayList<String> coverlist) {
        this.context = context;
        this.coverlist = coverlist;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);


        View itemView;


            itemView = inflater.inflate(R.layout.list_cv, parent, false);


            VH holder = new VH(itemView);

            return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {




            vh = (VH) holder;


                Glide.with(context).load(coverlist.get(position)).into(vh.cover);




    }

    @Override
    public int getItemCount() {

        return coverlist.size();
    }




    class VH extends RecyclerView.ViewHolder {


        ImageView cover;


        public VH(View itemView) {

            super(itemView);

            cover= itemView.findViewById(R.id.cover_min);




        }

        }//vh class


}//end