package com.q1.your_music_collection;




import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements DiscreteScrollView.OnItemChangedListener, View.OnClickListener, DiscreteScrollView.ScrollListener{


    ArrayList<Lists_Collection> collections= new ArrayList<>();
    ArrayList<Lists_Album> listsAlbums= new ArrayList<>();
    DiscreteScrollView discreteScrollView;
    TextView title, subTitle, userId, userName;
    MyAdapter_Main adapterMain;
    JSONObject obj;
    TinyDB tinyDB;
    ArrayList<Object> stp;
    String email, password, uid = "none", name;
    FirebaseAuth mAuth;
    FirebaseUser user;
    boolean islogin = false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);


        tinyDB = new TinyDB(this);

        userName = findViewById(R.id.userName);
        userId = findViewById(R.id.userId);
        title = findViewById(R.id.tv_title_Collection);
        subTitle = findViewById(R.id.tv_subTitle);
        discreteScrollView = findViewById(R.id.myCollections);
        discreteScrollView.setOrientation(DSVOrientation.HORIZONTAL);
        discreteScrollView.addOnItemChangedListener(this);
        adapterMain= new MyAdapter_Main(this, collections);
        discreteScrollView.setAdapter(adapterMain);
        discreteScrollView.setItemTransitionTimeMillis(200);
        discreteScrollView.setItemTransformer(new ScaleTransformer.Builder().setMinScale(0.8f).build());
        discreteScrollView.setOverScrollEnabled(true);
        discreteScrollView.setSlideOnFling(true);
        discreteScrollView.removeItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                collections.remove(adapterPosition);



            }
        });



    }

    public void login(View v){

        if(islogin){

            mAuth.signOut();
            islogin =false;
            userId.setText("sign in");
            userName.setVisibility(View.GONE);
            uid = "";

        }else{

            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 0);
        }



    }

    public void clickMake(View v){

        Intent intent =  new Intent(this, MakeActivity.class);

        int mode  = 111;
        intent.putExtra("mode",mode);
        startActivityForResult(intent, 11);

    }

    public void clickCommunity(View v){

//        getAD();

        Intent intent = new Intent(this, CommunityActivity.class);

            intent.putExtra("uid", uid);


        startActivityForResult(intent, 33);


    }

    public void clickEdit(View v){
        int position = discreteScrollView.getCurrentItem();
        int mode = 222;

        Intent intent = new Intent(this, MakeActivity.class);
        intent.putParcelableArrayListExtra("listAlbums",collections.get(position).getListsAlbums());
        intent.putExtra("nameList",collections.get(position).getNameList());
        intent.putExtra("mode", mode);
        intent.putExtra("position",position);

        startActivityForResult(intent, 20);
    }



    public void clickDel(View v){

        int position = discreteScrollView.getCurrentItem();

        collections.remove(position);

        refresh();

        if(position>0)  discreteScrollView.smoothScrollToPosition(position-1);

        saveToPhone();
    }

    public void refresh(){

        discreteScrollView.setAdapter(null);
        adapterMain= new MyAdapter_Main(this, collections);
        discreteScrollView.setAdapter(adapterMain);


    }


    public void listToJSON(int position){

        obj = new JSONObject();

        try {

        JSONArray jarray = new JSONArray();

        for (int i = 0; i < collections.get(position).getListsAlbums().size(); i++){

            JSONObject innerObj = new JSONObject();


                innerObj.put("artist", collections.get(position).getListsAlbums().get(i).getArtist());
                innerObj.put("album", collections.get(position).getListsAlbums().get(i).getAlbum());
                innerObj.put("cover", collections.get(position).getListsAlbums().get(i).getCover());
                innerObj.put("rank", collections.get(position).getListsAlbums().get(i).getRankImg());
                innerObj.put("info", collections.get(position).getListsAlbums().get(i).getUrl());

                if(collections.get(position).getListsAlbums().get(i).getOpinion()!=null)
                innerObj.put("opinion",collections.get(position).getListsAlbums().get(i).getOpinion());
                else innerObj.put("opinion","");


                jarray.put(innerObj);


        }//for

            obj.put("albumLists",jarray);



        }catch (JSONException e) {
            e.printStackTrace();
        }



    }

    public void clickName(View v){

        showTextInputDialog();

    }

    public void clickUpload(View v){



       if(islogin){

           int position = discreteScrollView.getCurrentItem();

           Log.e("po",position+"");
           listToJSON(position);



           String serverUrl="http://gogopanda.dothome.co.kr/yourCollections/insertDB.php";

           SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {
                   new AlertDialog.Builder(MainActivity.this).setMessage(response).setPositiveButton("ok", null).create().show();
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

               }
           });
           String title = collections.get(position).getNameList();
           multiPartRequest.addStringParam("MyJson",obj.toString());
           multiPartRequest.addStringParam("Title", title);
           multiPartRequest.addStringParam("Name", name);
           multiPartRequest.addStringParam("UID", uid);

           RequestQueue requestQueue = Volley.newRequestQueue(this);


           requestQueue.add(multiPartRequest);


       }else Toast.makeText(this, "need to sign in..", Toast.LENGTH_SHORT).show();




    }






    public void saveToPhone(){

        stp = new ArrayList<>();
        for(Lists_Collection a : collections){

            stp.add(a);

        }

        tinyDB.putListObject("collection",stp);

        tinyDB.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

            }
        });

    }
    public void  loadToPhone(){


    stp = tinyDB.getListObject("collection",Lists_Collection.class);

    collections.clear();

    for (Object objs : stp){

        collections.add((Lists_Collection)objs);

    }


        adapterMain.notifyDataSetChanged();


    }

    @Override
    protected void onResume() {
        int position = discreteScrollView.getCurrentItem();
        refresh();
        discreteScrollView.smoothScrollToPosition(position);
        super.onResume();
    }

    @Override
    public void onClick(View view) {


    }


    @Override
    public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerView.ViewHolder currentHolder, @Nullable RecyclerView.ViewHolder newCurrent) {


    }



    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

        if(adapterPosition>=0){

            title.setText(collections.get(adapterPosition).getNameList());
            subTitle.setText("Total "+collections.get(adapterPosition).getListsAlbums().size()+" albums");
        }else{
            title.setText("make your Collection");
            subTitle.setText("▼");
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        loadToPhone();
    }

    private void showTextInputDialog() {
        new LovelyTextInputDialog(this, R.style.EditTextTintTheme)
                .setTopColorRes(R.color.darkDeepOrange)
                .setTitle(R.string.text_input_nick)
                .setIcon(R.drawable.ic_like)
                .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String text) {
                        name = text;
                        tinyDB.putString("nickName", name);
                        userUpdate();

                    }})
                .setNegativeButton(android.R.string.no, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(name==null) {
                            int index = email.indexOf("@");
                            name = email.substring(0, index);
                            tinyDB.putString("nickName", name);
                            userUpdate();
                        }else return;
                    }
                }).show();
    }

    private void userUpdate(){

        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(name).build();
        user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "User profile updated.", Toast.LENGTH_SHORT).show();
                    userName.setText(" Welcome, "+name+" ");
                }

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode==RESULT_OK&&requestCode==11){

            listsAlbums = data.getParcelableArrayListExtra("myList");


            collections.add(new Lists_Collection(listsAlbums, data.getStringExtra("nameList")));

            adapterMain.notifyItemInserted(collections.size());



            if(collections.size()>1) discreteScrollView.scrollToPosition(collections.size()-1);




        }else if(resultCode==RESULT_OK&&requestCode==33){

            listsAlbums = data.getParcelableArrayListExtra("myList");

            collections.add(new Lists_Collection(listsAlbums, data.getStringExtra("nameList")));

            adapterMain.notifyItemInserted(collections.size());


            if(collections.size()>1) discreteScrollView.scrollToPosition(collections.size()-1);



        }else if(resultCode==RESULT_OK&&requestCode==20){

            int position = data.getIntExtra("position",-1);


            listsAlbums = data.getParcelableArrayListExtra( "myList");

            if(position!=-1) {
                collections.set(position, new Lists_Collection(listsAlbums, data.getStringExtra("nameList")));
            }else{
                collections.add(new Lists_Collection(listsAlbums, data.getStringExtra("nameList")));
            }
            refresh();

            discreteScrollView.scrollToPosition(position);


        }else if(resultCode==RESULT_OK&&requestCode==0){

            email = data.getStringExtra("email");
            password = data.getStringExtra("password");
            uid = data.getStringExtra("uid");


            tinyDB.putString("email",email);
            tinyDB.putString("password",password);

            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();

            name = user.getDisplayName();


            if(name == null){

                showTextInputDialog();

            }


            if(user != null){

                userId.setText("Logout");
                userName.setVisibility(View.VISIBLE);
                userName.setText(" Welcome, "+name+" ");
                islogin = true;
            }





        }

        saveToPhone();


    }





    }


