package com.q1.your_music_collection;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.luseen.autolinklibrary.AutoLinkMode;
import com.luseen.autolinklibrary.AutoLinkTextView;
import com.q1.your_music_collection.webView.BrowserActivity;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import static com.q1.your_music_collection.MyAdapter_Make.MODE_SONIC;

/**
 * Created by alfo06-07 on 2018-03-16.
 */

public class MyAdapter_CoverView extends RecyclerView.Adapter {



    VH vh;
    Context context;
    ArrayList<String> coverlist;
    ArrayList<Lists_Album> listsAlbums;
    RecyclerView recyclerView;
    public static final int MODE_SONIC = 1;
    private String url;


    public MyAdapter_CoverView(Context context, ArrayList<String> coverlist, ArrayList<Lists_Album> listsAlbums) {
        this.context = context;
        this.coverlist = coverlist;
        this.listsAlbums = listsAlbums;

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



            cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    url = listsAlbums.get(getAdapterPosition()).getUrl();
                    vh.startBrowserActivity(MODE_SONIC);
                }
            });



        }


        private void startBrowserActivity(int mode) {


            Intent intent = new Intent(context, BrowserActivity.class);
            intent.putExtra(BrowserActivity.PARAM_URL, url);
            intent.putExtra(BrowserActivity.PARAM_MODE, mode);
            context.startActivity(intent);
        }




        }



}//end