package com.q1.your_music_collection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;

import java.util.ArrayList;

/**
 * Created by alfo06-07 on 2018-04-25.
 */

public class MyAdapter_Main extends RecyclerView.Adapter {

    Context context;
    ArrayList<Lists_Collection> collections;
    ArrayList<Lists_Album> listsAlbums;
    ViewSwitcher mViewSwitcher;
    KenBurnsView[] kbView = new KenBurnsView[2];

    private int mTransitionsCount = 0;
    private static final int TRANSITIONS_TO_SWITCH = 1;

    public MyAdapter_Main(Context context, ArrayList<Lists_Collection> collections) {
        this.context = context;
        this.collections = collections;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;

            view= inflater.inflate(R.layout.list_colletion, parent, false);

        VH holder = new VH(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       VH vh = (VH) holder;

        Lists_Collection collection = collections.get(position);
        listsAlbums = collection.getListsAlbums();

        if (listsAlbums.size()==1) {
            for (int i = 0; i < 2; i++) {
                Glide.with(context).load(listsAlbums.get(0).getCover()).into(kbView[i]);
            }

        }else if (listsAlbums.size()>1){
            for (int i = 0; i < 2; i++) {
                Glide.with(context).load(listsAlbums.get(i).getCover()).into(kbView[i]);
            }

        }

    }



    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return collections.size();
    }


    @Override
    public int getItemViewType(int position) {
            return position;



    }



    class VH extends RecyclerView.ViewHolder {





        public VH(View itemView) {
            super(itemView);

                mViewSwitcher = itemView.findViewById(R.id.viewSwitcher);
                kbView[0] = itemView.findViewById(R.id.img1);
                kbView[1] = itemView.findViewById(R.id.img2);



            KenBurnsView.TransitionListener listener = new KenBurnsView.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                    mTransitionsCount++;
                    if (mTransitionsCount == TRANSITIONS_TO_SWITCH) {
                        mViewSwitcher.showNext();
                        mTransitionsCount = 0;
                    }
                }

                @Override
                public void onTransitionEnd(Transition transition) {

                }
            };

            for(int i=0; i<2; i++) {
                kbView[i].setTransitionListener(listener);
            }

        }
    }

}
