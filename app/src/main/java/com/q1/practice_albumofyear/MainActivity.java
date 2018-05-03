package com.q1.practice_albumofyear;



import android.content.ClipData;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements DiscreteScrollView.OnItemChangedListener, View.OnClickListener{

    ArrayList<Lists_Url> listsUrl = new ArrayList<>();


    ArrayList<Lists_Collection> collections= new ArrayList<>();
    ArrayList<Lists_Album> listsAlbums = new ArrayList<>();
    DiscreteScrollView discreteScrollView;
    InfiniteScrollAdapter infiniteScrollAdapter;
    TextView title, subTitle;
    MyAdapter_Main adapterMain;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);


        title = findViewById(R.id.tv_title_Collection);
        subTitle = findViewById(R.id.tv_subTitle);
        discreteScrollView = findViewById(R.id.myCollections);
        discreteScrollView.setOrientation(DSVOrientation.HORIZONTAL);
        discreteScrollView.addOnItemChangedListener(this);
        adapterMain= new MyAdapter_Main(this, collections, listsUrl);

        discreteScrollView.setAdapter(adapterMain);
        discreteScrollView.setItemTransitionTimeMillis(200);
        discreteScrollView.setItemTransformer(new ScaleTransformer.Builder().setMinScale(0.8f).build());





    }

    public void clickMake(View v){

        Intent intent =  new Intent(this, MakeActivity.class);

        startActivityForResult(intent, 1);

    }

    public void clickDel(View v){

        infiniteScrollAdapter.notifyDataSetChanged();
        int position = infiniteScrollAdapter.getRealCurrentPosition();

        if (position>0){

                collections.remove(position-1);
            infiniteScrollAdapter.notifyItemRemoved(position);
        }


    }





    @Override
    public void onClick(View view) {

    }



    public void onListAdded(Lists_Collection list){

    title.setText(list.getNameList());

    }



    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

//        int position = adapterMain.getRealPosition(adapterPosition);
//
//
//        if(position>0){
//
//            onListAdded(collections.get(position-1));
//
//
//        }
//        else title.setText(R.string.basic_title_make);


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK&&requestCode==1){



            listsAlbums = data.getParcelableArrayListExtra("myList");

            ArrayList<String>urls = new ArrayList<>();
            ArrayList<String>temp = new ArrayList<>();

            for(int i=0;i<listsAlbums.size();i++ ){

                temp.add(listsAlbums.get(i).getCover());

            }
            Log.e("xxx",temp.size()+"");

            urls.addAll(temp);

            listsUrl.add(new Lists_Url(urls));

            temp.clear();



            collections.add(new Lists_Collection(listsAlbums, data.getStringExtra("nameList")));

            adapterMain.notifyItemInserted(collections.size());


        }



    }
}//end
