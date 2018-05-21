package com.q1.your_music_collection;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.luseen.autolinklibrary.AutoLinkMode;
import com.luseen.autolinklibrary.AutoLinkTextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

/**
 * Created by alfo06-07 on 2018-03-16.
 */

public class MyAdapter_DBView extends RecyclerView.Adapter {



    VH vh;
    Context context;
    ArrayList<Lists_Album> listsAlbums;
    RecyclerView recyclerView;
    private static final int UNSELECTED = -1;
    private int selectedItem = UNSELECTED;

    public MyAdapter_DBView(Context context, ArrayList<Lists_Album> listsAlbums, RecyclerView recyclerView) {
        this.context = context;
        this.listsAlbums = listsAlbums;
        this.recyclerView = recyclerView;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);


        View itemView;


            itemView = inflater.inflate(R.layout.list_item, parent, false);


            VH holder = new VH(itemView);

            return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {




            vh = (VH) holder;



                Lists_Album listsAlbum = listsAlbums.get(position);


                Glide.with(context).load(listsAlbum.cover).into(vh.cover);

                vh.comment.setText(listsAlbum.getOpinion());



    }

    @Override
    public int getItemCount() {

        return listsAlbums.size();
    }




    class VH extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView cover, rating, open;
        ExpandableLayout expandableLayout;
        AutoLinkTextView comment;


        public VH(View itemView) {
            super(itemView);


                comment = itemView.findViewById(R.id.tv_comment);
                comment.addAutoLinkMode(AutoLinkMode.MODE_HASHTAG, AutoLinkMode.MODE_URL, AutoLinkMode.MODE_MENTION);
                open = itemView.findViewById(R.id.btn_open);
                cardView = itemView.findViewById(R.id.viewCard);
                cover = itemView.findViewById(R.id.iv_cover);
                rating = itemView.findViewById(R.id.iv_rating);
                expandableLayout = itemView.findViewById(R.id.expandable_menu);


                open.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        VH holder= (VH) recyclerView.findViewHolderForAdapterPosition(selectedItem);


                        if( holder != null){
                            open.setSelected(false);
                            expandableLayout.collapse();
                        }
                        int position = getAdapterPosition();

                        if(position == selectedItem){
                            selectedItem = UNSELECTED;}

                        else {
                            open.setSelected(true);
                            expandableLayout.expand();
                            selectedItem = position;
                    }
                }});



        }

        }//vh class


}//end