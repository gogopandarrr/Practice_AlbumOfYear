package com.q1.your_music_collection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class DBViewActivity extends AppCompatActivity {

    MyAdapter_CoverView adapter_dbView;
    RecyclerView recyclerView;
    ArrayList<Lists_Album> listsAlbums;
    String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbview);

        Intent data = getIntent();

        title = data.getStringExtra("title");
        listsAlbums = data.getParcelableArrayListExtra("listsAlbums");


        recyclerView = findViewById(R.id.recycler_dbv);
        adapter_dbView = new MyAdapter_CoverView(this, listsAlbums, recyclerView);

        recyclerView.setAdapter(adapter_dbView);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
             recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new CustomRecyclerDecoration(3, 0, false, 0));




    }
}
