package com.q1.practice_albumofyear;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by alfo06-07 on 2018-04-25.
 */

public class MyAdapter_Main extends RecyclerView.Adapter {

    Context context;
    ArrayList<Lists_Collection> collections;
    private boolean headerFlag = false;
    private static  int TYPE_HEADER = 0;

    public MyAdapter_Main(Context context, ArrayList<Lists_Collection> collections) {
        this.context = context;
        this.collections = collections;

        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;

        if(viewType==TYPE_HEADER){

            headerFlag =true;
            view= inflater.inflate(R.layout.list_colletion_header, parent, false);



        }else{

            headerFlag= false;
            view= inflater.inflate(R.layout.list_colletion, parent, false);
        }


        VH holder = new VH(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        VH vh = (VH) holder;
        Log.e("a2","aaaaaaaaaaaaaaa");

    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return collections.size()+1;
    }


    @Override
    public int getItemViewType(int position) {
//        if(position==TYPE_HEADER) return TYPE_HEADER;
//        else
            return position;



    }


    public void delete(int position){


        collections.remove(position);
        notifyItemRemoved(position);

    }


    class VH extends RecyclerView.ViewHolder{

        boolean isHeader = headerFlag;
        ImageView iv_collection, btn_add, del;


        public VH(View itemView) {
            super(itemView);



            if(!isHeader){
                iv_collection= itemView.findViewById(R.id.iv_collection);

            }else{
                btn_add= itemView.findViewById(R.id.iv_btnAdd);

            }


        }
    }

}
