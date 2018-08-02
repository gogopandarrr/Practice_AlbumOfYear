package com.q1.your_music_collection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CommunityActivity extends AppCompatActivity {

    RecyclerView dbRecycler;
    MyAdapter_Community adapter_community;
    ArrayList<Lists_LoadDB> loadDBs = new ArrayList<>();
    ArrayList<ArrayList<String>> listsCovers = new ArrayList<>();
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");

        dbRecycler = findViewById(R.id.db_recycler);
        adapter_community = new MyAdapter_Community(this, loadDBs, listsCovers, uid, dbRecycler);
        dbRecycler.setAdapter(adapter_community);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        dbRecycler.setLayoutManager(layoutManager);

        loadDB();





    }//oc

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }



    public void loadDB(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String serverUrl = "http://gogopanda.dothome.co.kr/yourCollections/loadDB.php";

                try {
                    URL url = new URL(serverUrl);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.setUseCaches(false);

                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuilder sb = new StringBuilder();
                    String line = reader.readLine();

                    while(true){

                        sb.append(line);

                        line= reader.readLine();

                        if(line==null) break;

                        sb.append("\n");

                    }

                    String[] rows= sb.toString().split(";;");

                    for(String row : rows){

                        String[] datas = row.split("@#");
                        if(datas.length!=5) continue;
                        String UID = datas[0];
                        String name = datas[1];
                        String title = datas[2];
                        String json = datas[3];
                        String date = datas[4];



                        jsonParser(json, title, date, UID, name);


                    }



                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();

                }


            }
        });

        thread.start();

    }


    public void jsonParser(String jsonString, String title, String date, String UID, String name){


        if(jsonString==null) return;


        try {
            JSONObject jsonObject = new JSONObject(jsonString);




            ArrayList<Lists_Album> listsAlbum = new ArrayList<>();
            JSONArray albumsInfos = jsonObject.getJSONArray("albumLists");
            ArrayList<String> covers  = new ArrayList<>();

            for(int k=0; k< albumsInfos.length(); k++) {


                JSONObject albumInfo = albumsInfos.getJSONObject(k);
                String artist = albumInfo.getString("artist");
                String album = albumInfo.getString("album");
                String cover = albumInfo.getString("cover");
                int rank = albumInfo.getInt("rank");
                String opinion = albumInfo.getString("opinion");
                String info = albumInfo.getString("info");

                listsAlbum.add(new Lists_Album(rank, cover, artist, album, info, opinion));
                covers.add(cover);

            }

            loadDBs.add(new Lists_LoadDB(title, listsAlbum, date, UID, name));
            listsCovers.add(covers);


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter_community.notifyDataSetChanged();
                }
            });


        } catch (JSONException e) {

            Log.e("Exception", e.getMessage());


        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){


            case 333:
                if(resultCode==RESULT_OK) {

                    setResult(RESULT_OK,data);
                    finish();

                }
                break;





        }//switch


    }

}//end
