package com.q1.your_music_collection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alfo06-07 on 2018-05-10.
 */

public class MyAdapter_Community extends RecyclerView.Adapter {

    Activity context;
    ArrayList<Lists_LoadDB> loadDBs;
    ArrayList<ArrayList<String>> listsCovers;
    ArrayList<String> coverlist = new ArrayList<>();
    Lists_LoadDB loadDB;
    ArrayList<Lists_Album> listsAlbums;
    String title, uid, date;

    public MyAdapter_Community(Activity context, ArrayList<Lists_LoadDB> loadDBs, ArrayList<ArrayList<String>> listsCovers, String uid) {
        this.context = context;
        this.loadDBs = loadDBs;
        this.listsCovers = listsCovers;
        this.uid = uid;

    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.list_db_cards, parent, false);

        final VH holder = new VH(itemView);



        holder.bt_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int p = holder.getAdapterPosition();
                loadDB = loadDBs.get(p);
                title = loadDB.getTitle();
                date = loadDB.getDate();


                String serverUrl="http://gogopanda.dothome.co.kr/yourCollections/deleteDB.php";

                SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new AlertDialog.Builder(context).setMessage(response).setPositiveButton("ok", null).create().show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

                multiPartRequest.addStringParam("Date", date);
                multiPartRequest.addStringParam("UID", uid);
                multiPartRequest.addStringParam("Title", title);

                RequestQueue requestQueue = Volley.newRequestQueue(context);

                requestQueue.add(multiPartRequest);


                removeItem(p);

            }
        });



        holder.bt_dbv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int p = (int) holder.getAdapterPosition();

                listsAlbums = loadDBs.get(p).getListsAlbums();
                title = loadDBs.get(p).getTitle();
                Intent intent = new Intent(context, MakeActivity.class);

                int mode = 333;

                intent.putExtra("mode",mode);
                intent.putExtra("nameList",title);
                intent.putParcelableArrayListExtra("listAlbums",listsAlbums);
                context.startActivityForResult(intent, 333);


            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        coverlist = listsCovers.get(position);
        loadDB = loadDBs.get(position);

        VH vh = (VH) holder;

        if(uid.equals(loadDB.getUID())){
            vh.bt_del.setVisibility(View.VISIBLE);
        }else{
            vh.bt_del.setVisibility(View.INVISIBLE);
        }


        vh.tv_total.setText(coverlist.size()+"A");
        vh.tv_title.setText(loadDB.getTitle());
        vh.user.setText(loadDB.getName());
        vh.tv_date.setText(loadDB.getDate());

        vh.adapter_coverView= new MyAdapter_CoverView(context, coverlist);
        vh.recyclerView.setAdapter(vh.adapter_coverView);



    }

    public void removeItem(int p) {

        loadDBs.remove(p);
        listsCovers.remove(p);
        notifyItemRemoved(p);
    }


    @Override
    public int getItemCount() {
        return loadDBs.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView user, tv_title, tv_date, tv_total;
        ImageView more, bt_dbv, bt_del;

        MyAdapter_CoverView adapter_coverView;
        RecyclerView recyclerView;





        public VH(View itemView) {
            super(itemView);

            tv_total = itemView.findViewById(R.id.tv_total);
            bt_del = itemView.findViewById(R.id.bt_del);
            bt_dbv = itemView.findViewById(R.id.bt_dbv);
            tv_date = itemView.findViewById(R.id.date);
            user = itemView.findViewById(R.id.tv_user);
            tv_title = itemView.findViewById(R.id.tv_db_title);


            recyclerView= itemView.findViewById(R.id.coverView);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 3);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new CustomRecyclerDecoration(3, 0, false, 0));


        }
    }

    }



