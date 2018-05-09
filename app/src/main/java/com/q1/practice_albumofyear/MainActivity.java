package com.q1.practice_albumofyear;



import android.content.ClipData;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

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



public class MainActivity extends AppCompatActivity implements DiscreteScrollView.OnItemChangedListener, View.OnClickListener, DiscreteScrollView.ScrollListener{

    ArrayList<Lists_Collection> collections= new ArrayList<>();
    ArrayList<Lists_Album> listsAlbums = new ArrayList<>();
    DiscreteScrollView discreteScrollView;
    TextView title, subTitle;
    MyAdapter_Main adapterMain;
    JSONObject obj;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);


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

    public void clickMake(View v){

        Intent intent =  new Intent(this, MakeActivity.class);

        startActivityForResult(intent, 1);

    }

    public void clickEdit(View v){
        int position = discreteScrollView.getCurrentItem();
        boolean modify = true;

        Intent intent = new Intent(this, MakeActivity.class);
        intent.putParcelableArrayListExtra("listAlbums",collections.get(position).getListsAlbums());
        intent.putExtra("nameList",collections.get(position).getNameList());
        intent.putExtra("modify", modify);
        intent.putExtra("position",position);

        startActivityForResult(intent, 2);
    }



    public void clickDel(View v){

        int position = discreteScrollView.getCurrentItem();

        discreteScrollView.setAdapter(null);
        collections.remove(position);

        adapterMain= new MyAdapter_Main(this, collections);
        discreteScrollView.setAdapter(adapterMain);
        if(position>0)  discreteScrollView.smoothScrollToPosition(position-1);
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
                innerObj.put("rank",collections.get(position).getListsAlbums().get(i).getRankImg());
                if(collections.get(position).getListsAlbums().get(i).getOpinion()!=null)
                innerObj.put("opinion",collections.get(position).getListsAlbums().get(i).getOpinion());
                else
                    innerObj.put("opinion","None");
                innerObj.put("info", collections.get(position).getListsAlbums().get(i).getUrl());

                jarray.put(innerObj);

        }//for

            obj.put("title",collections.get(position).getNameList());
            obj.put("albumLists",jarray);

            System.out.println(obj.toString());

        }catch (JSONException e) {
            e.printStackTrace();
        }



    }


    public void clickUpload(View v){

        int position = discreteScrollView.getCurrentItem();

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

        multiPartRequest.addStringParam("MyJson",obj.toString());

        RequestQueue requestQueue = Volley.newRequestQueue(this);


        requestQueue.add(multiPartRequest);



    }


    public void loadDB(View v){

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


                    while(true){
                        String line = reader.readLine();

                        if(line==null) break;

                        sb.append(line);


                    }

                    reader.close();
                    connection.disconnect();


                    String result = sb.toString();
                    jsonParser(result);


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();

                }


            }
        });

        thread.start();

    }


    public void jsonParser(String jsonString){


        if(jsonString==null) return;


        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            System.out.println(jsonObject.toString());
            collections.clear();


                String title = jsonObject.getString("title");


                ArrayList<Lists_Album> listsAlbum = new ArrayList<>();
                JSONArray albumsInfos = jsonObject.getJSONArray("albumLists");


                    for(int k=0; k< albumsInfos.length(); k++) {

                        JSONObject albumInfo = albumsInfos.getJSONObject(k);

                        String artist = albumInfo.getString("artist");
                        String album = albumInfo.getString("album");
                        String cover = albumInfo.getString("cover");
                        int rank = albumInfo.getInt("rank");
                        String opinion = albumInfo.getString("opinion");
                        String info = albumInfo.getString("info");

                        listsAlbum.add(new Lists_Album(rank, cover, artist, album, info, opinion));

                    }

                    collections.add(new Lists_Collection(listsAlbum, title));


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterMain.notifyDataSetChanged();
                    }
                });


        } catch (JSONException e) {

            Log.e("Exception", e.getMessage());


        }




    }





    @Override
    public void onClick(View view) {


    }


    @Override
    public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerView.ViewHolder currentHolder, @Nullable RecyclerView.ViewHolder newCurrent) {


    }



    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

        if(adapterPosition>=0)  title.setText(collections.get(adapterPosition).getNameList());


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK&&requestCode==1){

            listsAlbums = data.getParcelableArrayListExtra("myList");

            collections.add(new Lists_Collection(listsAlbums, data.getStringExtra("nameList")));

            adapterMain.notifyItemInserted(collections.size());

        }else if(resultCode==RESULT_OK&&requestCode==2){

            int position = data.getIntExtra("position",0);

            listsAlbums = data.getParcelableArrayListExtra( "myList");

            collections.set(position, new Lists_Collection(listsAlbums, data.getStringExtra("nameList")));

            adapterMain.notifyItemChanged(position);
        }



    }


}
