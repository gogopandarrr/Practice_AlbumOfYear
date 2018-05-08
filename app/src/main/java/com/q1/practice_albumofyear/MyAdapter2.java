package com.q1.practice_albumofyear;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;


import com.bumptech.glide.Glide;
import com.hsalf.smilerating.SmileRating;
import com.luseen.autolinklibrary.AutoLinkMode;
import com.luseen.autolinklibrary.AutoLinkTextView;


import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

/**
 * Created by alfo06-07 on 2018-03-21.
 */

public class MyAdapter2 extends RecyclerView.Adapter {

    MyAdapter myAdapter;
    Context context;
    ArrayList<Lists_Album> listsAlbums;
    Lists_Album listsAlbum;
    Resources res;
    RecyclerView recyclerView;
    private static final int UNSELECTED = -1;
    private int selectedItem = UNSELECTED;
    VH vh;

    public MyAdapter2(Context context, ArrayList<Lists_Album> listsAlbums, RecyclerView recyclerView) {
        this.context = context;
        this.listsAlbums = listsAlbums;
        this.recyclerView = recyclerView;

        res = context.getResources();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView;

            itemView = inflater.inflate(R.layout.info_music,parent,false);
            VH holder = new VH(itemView);

            return holder;



    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        listsAlbum = listsAlbums.get(position);

            vh = (VH) holder;

            vh.artist.setText(listsAlbum.artist);
            vh.title.setText(listsAlbum.album);
            Glide.with(context).load(listsAlbum.cover).into(vh.cover);


    }



    @Override
    public int getItemCount() {
        return listsAlbums.size();
    }



    public void delete(int position){

        listsAlbums.remove(position);
        myAdapter.notifyItemRemoved(position);
        notifyItemRemoved(position);

    }



    class VH extends RecyclerView.ViewHolder {

        CardView info_card;
        TextView artist, title;
        SmileRating smileRating;
        ImageView bt_delete;
        ViewSwitcher switcherT, switcherC;
        AutoLinkTextView comment;
        EditText ev_title, ev_comment;
        ExpandableLayout expandableLayout;
        ImageView btnOpen, cover, rank;



        public VH(View itemView) {
            super(itemView);


            rank= itemView.findViewById(R.id.rank);
            cover= itemView.findViewById(R.id.cover);
            smileRating = itemView.findViewById(R.id.smile_rating);
            artist = itemView.findViewById(R.id.tv_artist);
            title = itemView.findViewById(R.id.tv_albumTitle);
            info_card = itemView.findViewById(R.id.info_card);
            bt_delete = itemView.findViewById(R.id.bt_delete);
            comment = itemView.findViewById(R.id.tv_comment);
            ev_title = itemView.findViewById(R.id.ev_albumTitle);
            ev_comment = itemView.findViewById(R.id.ev_comment);
            switcherT = itemView.findViewById(R.id.switcher_album);
            switcherC = itemView.findViewById(R.id.switcher_comment);
            btnOpen = itemView.findViewById(R.id.btn_open);
            expandableLayout = itemView.findViewById(R.id.expandable_menu);
            expandableLayout.setInterpolator(new OvershootInterpolator());
            btnOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VH holder= (VH) recyclerView.findViewHolderForAdapterPosition(selectedItem);



                    if( holder != null){
                        btnOpen.setSelected(false);
                        expandableLayout.collapse();
                    }
                    int position = getAdapterPosition();

                    if(position == selectedItem){
                        selectedItem = UNSELECTED;}

                        else {
                        btnOpen.setSelected(true);
                        expandableLayout.expand();
                        selectedItem = position;

                    }


                }
            });
            expandableLayout.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
                @Override
                public void onExpansionUpdate(float expansionFraction, int state) {
                    if (state == ExpandableLayout.State.EXPANDING){

                        recyclerView.smoothScrollToPosition(getAdapterPosition());

                    }
                }
            });



            title.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ev_title.setText(title.getText().toString());

                    ev_title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean hasFocus) {

                            if(!hasFocus) {

                                title.setText(ev_title.getText().toString());
                                listsAlbum.setAlbum(title.getText().toString());
                                switcherT.showNext();

                            }
                        }
                    });

                    switcherT.showNext();


                    return true;
                }
            });


            comment.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    comment.addAutoLinkMode(AutoLinkMode.MODE_HASHTAG, AutoLinkMode.MODE_URL, AutoLinkMode.MODE_MENTION);
                    ev_comment.setText(comment.getText().toString());

                    ev_comment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean hasFocus) {


                            if(!hasFocus) {

                                comment.setAutoLinkText(ev_comment.getText().toString());
                                listsAlbum.setOpinion(comment.getText().toString());
                                switcherC.showNext();

                            }
                        }
                    });

                    switcherC.showNext();

                    return true;
                }
            });

            smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
                @Override
                public void onSmileySelected(int smiley, boolean reselected) {

                    int position = getAdapterPosition();
                    VH holder= (VH) recyclerView.findViewHolderForAdapterPosition(position);



                    switch (smiley){

                        case SmileRating.BAD:
                            listsAlbum.setRankImg(R.drawable.emo2_bad);
                            Glide.with(context).load(listsAlbum.getRankImg()).into(holder.rank);
                            notifyDataSetChanged();
                            break;
                        case SmileRating.GOOD:
                            listsAlbum.setRankImg(R.drawable.emo4_good);
                            Glide.with(context).load(listsAlbum.getRankImg()).into(holder.rank);
                            notifyDataSetChanged();
                            break;
                        case SmileRating.GREAT:
                            listsAlbum.setRankImg(R.drawable.emo5_great);
                            Glide.with(context).load(listsAlbum.getRankImg()).into(holder.rank);
                            notifyDataSetChanged();
                            break;
                        case SmileRating.OKAY:
                            listsAlbum.setRankImg(R.drawable.emo3_okay);
                            Glide.with(context).load(listsAlbum.getRankImg()).into(holder.rank);
                            notifyDataSetChanged();
                            break;
                        case SmileRating.TERRIBLE:
                            listsAlbum.setRankImg(R.drawable.emo1_terrible);
                            Glide.with(context).load(listsAlbum.getRankImg()).into(holder.rank);
                            notifyDataSetChanged();
                            break;


                    }

                }
            });




            bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    delete(getAdapterPosition());
                }
            });

        }


    }//vh.class



}
