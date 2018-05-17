package com.q1.your_music_collection;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialOverlayLayout;
import com.leinardi.android.speeddial.SpeedDialView;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;


import java.util.ArrayList;

public class MakeActivity extends AppCompatActivity {

    ArrayList<Lists_Album> listsAlbums = new ArrayList<>();

    RecyclerView infoRecycle,coverRecycle;
    MyAdapter2 adapter2;
    SpeedDialView speedDialView;
    SpeedDialOverlayLayout overlayLayout;
    TextView tv_name;


    boolean modify;
    int modiPosition;
    String modiTitle;



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
                        clickSave();
                        speedDialView.close();
                        return true;



                }

                return false;

            }
        });



        RecyclerViewDragDropManager dragDropManager = new RecyclerViewDragDropManager();
        dragDropManager.setInitiateOnMove(false);
        dragDropManager.setInitiateOnLongPress(true);

        coverRecycle = findViewById(R.id.cover_recycler);
        infoRecycle = findViewById(R.id.info_recycler);
        infoRecycle.setHasFixedSize(false);


        adapter2 = new MyAdapter2(this, listsAlbums, infoRecycle);
        infoRecycle.setAdapter(dragDropManager.createWrappedAdapter(adapter2));

        dragDropManager.attachRecyclerView(infoRecycle);

        tv_name = findViewById(R.id.tv_nameCollection);



        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showTextInputDialog();


            }
        });




        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);

        coverRecycle.setLayoutManager(layoutManager);
        infoRecycle.setLayoutManager(layoutManager2);



        Intent data = getIntent();


        modify = data.getBooleanExtra("modify", false);

        if(modify){
            ArrayList<Lists_Album> temp = data.getParcelableArrayListExtra("listAlbums");

            for(int i=0; i<temp.size();i++) {
                listsAlbums.add(temp.get(i));
            }
            modiTitle = data.getStringExtra("nameList");
            tv_name.setText(modiTitle);


            modiPosition = data.getIntExtra("position",0);

            adapter2.notifyDataSetChanged();

        }else showTextInputDialog();



    }//oc

    private void showTextInputDialog() {
        new LovelyTextInputDialog(this, R.style.EditTextTintTheme)
                .setTopColorRes(R.color.darkDeepOrange)
                .setTitle(R.string.text_input_title)
                .setIcon(R.drawable.ic_listen)
                .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String text) {
                        tv_name.setText(text);
                    }})
                .setNegativeButton(android.R.string.no, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).show();
    }



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




    public void clickSave(){


        Intent intent = getIntent();
        if(modify){
            intent.putExtra("position",modiPosition);

        }
        intent.putParcelableArrayListExtra("myList",listsAlbums);
        intent.putExtra("nameList",tv_name.getText().toString());
        setResult(RESULT_OK,intent);

        finish();


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


        adapter2.notifyDataSetChanged();


        super.onActivityResult(requestCode, resultCode, data);
    }

}
