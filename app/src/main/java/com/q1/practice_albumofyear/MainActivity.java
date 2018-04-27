package com.q1.practice_albumofyear;



import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.Orientation;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements DiscreteScrollView.OnItemChangedListener, View.OnClickListener{



    ArrayList<Lists_Album> listsAlbums = new ArrayList<>();

    DiscreteScrollView discreteScrollView;
    InfiniteScrollAdapter infiniteScrollAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);


        discreteScrollView = findViewById(R.id.myCollections);
        discreteScrollView.setOrientation(Orientation.HORIZONTAL);
        discreteScrollView.addOnItemChangedListener(this);
        infiniteScrollAdapter = InfiniteScrollAdapter.wrap(new MyAdapter_Main(this,listsAlbums));
        discreteScrollView.setAdapter(infiniteScrollAdapter);
        discreteScrollView.setItemTransitionTimeMillis(240);
        discreteScrollView.setItemTransformer(new ScaleTransformer.Builder().setMinScale(0.8f).build());




    }

    public void clickMake(View v){

        Intent intent =  new Intent(this, MakeActivity.class);

        startActivityForResult(intent, 1);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

    }
}//end
