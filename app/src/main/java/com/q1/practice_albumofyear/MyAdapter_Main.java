package com.q1.practice_albumofyear;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import com.goka.kenburnsview.KenBurnsView;

import java.util.ArrayList;

/**
 * Created by alfo06-07 on 2018-04-25.
 */

public class MyAdapter_Main extends RecyclerView.Adapter {

    Context context;
    ArrayList<Lists_Collection> collections;
    ArrayList<Lists_Url> listsUrl;
    ArrayList<Integer> listBasicCover = new ArrayList<>();
    private boolean headerFlag = false;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public MyAdapter_Main(Context context, ArrayList<Lists_Collection> collections,ArrayList<Lists_Url> listsUrl) {
        this.context = context;
        this.collections = collections;
        this.listsUrl = listsUrl;
        setHasStableIds(true);

        listBasicCover.add(R.drawable.cover5);
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

        if(!headerFlag) {


            Log.e("a2", position + "");
            Log.e("a3",listsUrl.size()+"");
            Log.e("a4",listsUrl.get(position-1).getUrls().size()+"");

            if(listsUrl.get(position-1).getUrls().size()!=0) vh.kenBurnsView.initStrings(listsUrl.get(position - 1).getUrls());
            else vh.kenBurnsView.initResourceIDs(listBasicCover);

        }





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
        if(position==TYPE_HEADER) return TYPE_HEADER;
        else
            return position;



    }


    public void delete(int position){


        collections.remove(position);
        notifyItemRemoved(position);

    }


    class VH extends RecyclerView.ViewHolder{

        boolean isHeader = headerFlag;
        ImageView btn_add;
        KenBurnsView kenBurnsView;


        public VH(View itemView) {
            super(itemView);



            if(!isHeader){
                kenBurnsView= itemView.findViewById(R.id.kenburns);

            }else{
                btn_add= itemView.findViewById(R.id.iv_btnAdd);

            }


        }
    }

}
