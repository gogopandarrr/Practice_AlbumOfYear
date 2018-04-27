package com.q1.practice_albumofyear;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialOverlayLayout;
import com.leinardi.android.speeddial.SpeedDialView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

public class MakeActivity extends AppCompatActivity {


    ArrayList<Lists_Album> listsAlbums = new ArrayList<>();
    RecyclerView infoRecycle,coverRecycle;
    MyAdapter2 adapter2;
    MyAdapter adapter;
    SpeedDialView speedDialView;
    SpeedDialOverlayLayout overlayLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_make);


        overlayLayout = findViewById(R.id.overlay);
        speedDialView = findViewById(R.id.speedDial);
        speedDialView.setOverlayLayout(overlayLayout);

        speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_add, R.mipmap.ic_add)
                .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.inbox_primary, getTheme()))
                .setLabel(getString(R.string.label_add_album))
                .setLabelColor(Color.WHITE)
                .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.material_blue_900, getTheme()))
                .setLabelClickable(false)
                .create());

        speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_save, R.drawable.ic_save)
                .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.material_lime_500, getTheme()))
                .setLabel(getString(R.string.label_save_list))
                .setLabelColor(Color.WHITE)
                .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.material_lime_900, getTheme()))
                .setLabelClickable(false)
                .create());



        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {

                switch (actionItem.getId()){

                    case R.id.fab_add:
                        clickAdd();
                        speedDialView.close();
                        return true;


                    case R.id.fab_save:
                        speedDialView.close();
                        return true;



                }

                return false;

            }
        });



        coverRecycle = findViewById(R.id.cover_recycler);
        infoRecycle = findViewById(R.id.info_recycler);
        adapter = new MyAdapter(this, listsAlbums);
        adapter2 = new MyAdapter2(this, listsAlbums, adapter, infoRecycle);
        infoRecycle.setAdapter(adapter2);
        coverRecycle.setAdapter(adapter);





        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);

        coverRecycle.setLayoutManager(layoutManager);
        infoRecycle.setLayoutManager(layoutManager2);



    }//oc
    @Override
    public void onBackPressed() {
        if(speedDialView.isOpen())speedDialView.close();
        else super.onBackPressed();
    }




    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    public void clickAdd(){

        Intent intent = new Intent(this, SearchActivity.class);
        startActivityForResult(intent, 10);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode){


            case 10:
                if(resultCode==RESULT_OK) {
                    listsAlbums.add(new Lists_Album(R.drawable.emo0_basic,
                            data.getStringExtra("cover"), data.getStringExtra("artist"),
                            data.getStringExtra("title"), data.getStringExtra("url"), null));

                }

                break;

        }//switch

        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();

        super.onActivityResult(requestCode, resultCode, data);
    }

}
