package com.q1.your_music_collection;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alfo06-07 on 2018-05-10.
 */

public class MyAdapter_Community extends RecyclerView.Adapter {

    Context context;
    ArrayList<Lists_LoadDB> loadDBs;
    ArrayList<ArrayList<String>> listsCovers;
    ArrayList<String> coverlist = new ArrayList<>();
    Lists_LoadDB loadDB;

    public MyAdapter_Community(Context context, ArrayList<Lists_LoadDB> loadDBs, ArrayList<ArrayList<String>> listsCovers) {
        this.context = context;
        this.loadDBs = loadDBs;
        this.listsCovers = listsCovers;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.list_db_cards, parent, false);

        VH holder = new VH(itemView);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        coverlist = listsCovers.get(position);
        loadDB = loadDBs.get(position);

        VH vh = (VH) holder;

        vh.title.setText(loadDB.getTitle());
        vh.user.setText(loadDB.getName());
        vh.date.setText(loadDB.getDate());




        if(coverlist.size()>5) {
            vh.linearLayout.setVisibility(View.VISIBLE);
            vh.more.setVisibility(View.VISIBLE);
        }

        for(int i=0; i < coverlist.size(); i++){

            if(i<10)
            Glide.with(context).load(coverlist.get(i)).into(vh.iv[i]);

        }



    }

    @Override
    public int getItemCount() {
        return loadDBs.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView user, title, date, no;
        CircleImageView userPic;
        ImageView iv[] = new ImageView[10];
        LinearLayout linearLayout;
        ImageView more;


        public VH(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            more = itemView.findViewById(R.id.iv_more);
            linearLayout = itemView.findViewById(R.id.line2);
            user = itemView.findViewById(R.id.tv_user);
            title = itemView.findViewById(R.id.tv_db_title);
            iv[0] = itemView.findViewById(R.id.iv1);
            iv[1] = itemView.findViewById(R.id.iv2);
            iv[2] = itemView.findViewById(R.id.iv3);
            iv[3] = itemView.findViewById(R.id.iv4);
            iv[4] = itemView.findViewById(R.id.iv5);
            iv[5] = itemView.findViewById(R.id.iv6);
            iv[6] = itemView.findViewById(R.id.iv7);
            iv[7] = itemView.findViewById(R.id.iv8);
            iv[8] = itemView.findViewById(R.id.iv9);
            iv[9] = itemView.findViewById(R.id.iv10);


        }
    }

    }



